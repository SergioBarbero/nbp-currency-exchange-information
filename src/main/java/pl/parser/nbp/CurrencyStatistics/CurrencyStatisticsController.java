package pl.parser.nbp.CurrencyStatistics;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.parser.nbp.ChartFile.ChartFile;
import pl.parser.nbp.ChartFile.ChartFileContainer;
import pl.parser.nbp.Rate.PurchasesRate;
import pl.parser.nbp.RateChart.CurrencyRateChartC;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

@RestController
public class CurrencyStatisticsController {

    private static DateFormat publicationDateFormat = new SimpleDateFormat("yyyy");

    @GetMapping("/statistics/{start-date}/{end-date}/{currency}")
    public CurrencyStatistics getStatisticsForRangeAndType(
            @PathVariable("start-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @PathVariable("end-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @PathVariable("currency") String currencyCode) {

        int startYear = Integer.parseInt(publicationDateFormat.format(startDate));
        int endYear = Integer.parseInt(publicationDateFormat.format(endDate));
        ChartFileContainer bucket = new ChartFileContainer(startYear, endYear);
        SortedSet<ChartFile> filteredList = bucket.filterList('c', startDate, endDate);
        for (ChartFile chartFile : filteredList) {
            try {
                chartFile.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<PurchasesRate> rates = filteredList.stream().map(ChartFile::getChart).map(CurrencyRateChartC.class::cast).map(chart -> chart.getRateByCurrency(currencyCode)).collect(Collectors.toList());
        double[] buyingRates = rates.stream().mapToDouble(PurchasesRate::getBuyingRate).toArray();
        double[] sellingRates = rates.stream().mapToDouble(PurchasesRate::getSellingRate).toArray();
        return new CurrencyStatistics(new RateStatistics(buyingRates), new RateStatistics(sellingRates));
    }

}
