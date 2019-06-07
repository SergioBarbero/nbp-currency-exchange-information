package pl.parser.nbp.CurrencyStatistics;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.parser.nbp.ChartFile.ChartFile;
import pl.parser.nbp.ChartFile.ChartFileBucket;
import pl.parser.nbp.Currency.Currency;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

@RestController
public class CurrencyStatisticsController {

    private static DateFormat publicationDateFormat = new SimpleDateFormat("yyyy");

    @GetMapping("/statistics/{start-date}/{end-date}/{currency}/{type}")
    public CurrencyStatistics getStatisticsForRangeAndType(
            @PathVariable("start-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @PathVariable("end-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @PathVariable("currency") String currencyCode,
            @PathVariable("type") char type) {
        int startYear = Integer.parseInt(publicationDateFormat.format(startDate));
        int endYear = Integer.parseInt(publicationDateFormat.format(endDate));
        ChartFileBucket bucket = new ChartFileBucket(startYear, endYear);
        SortedSet<ChartFile> filteredList = bucket.filterList(type, startDate, endDate);
        filteredList.forEach(ChartFile::load);
        List<Currency> rates = filteredList.stream().map(file -> file.getChart().getRateByCurrency(currencyCode)).collect(Collectors.toList());
        double[] buyingRates = rates.stream().mapToDouble(Currency::getBuyingRate).toArray();
        double[] sellingRates = rates.stream().mapToDouble(Currency::getSellingRate).toArray();
        return new CurrencyStatistics(new RateStatistics(buyingRates), new RateStatistics(sellingRates));
    }


}
