package pl.parser.nbp.currencyStatistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.parser.nbp.chartfile.ChartFile;
import pl.parser.nbp.chartfile.ChartType;
import pl.parser.nbp.chartfile.ChartFileService;
import pl.parser.nbp.rate.PurchasesRate;
import pl.parser.nbp.ratechart.CurrencyRateChartC;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

@RestController
public class CurrencyStatisticsController {

    @Autowired
    private ChartFileService ChartFileService;

    @GetMapping("/statistics/{start-date}/{end-date}/{currency}")
    public CurrencyStatistics getStatisticsForRangeAndType(
            @PathVariable("start-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @PathVariable("end-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @PathVariable("currency") String currencyCode) {

        SortedSet<ChartFile> filteredList = ChartFileService.findFilesBy(startDate, endDate, ChartType.c);
        List<PurchasesRate> rates = filteredList.stream()
                .map(ChartFile::retrieveCurrencyRateChart)
                .map(CurrencyRateChartC.class::cast)
                .map(chart -> chart.getRateByCurrency(currencyCode))
                .collect(Collectors.toList());
        double[] buyingRates = rates.stream().mapToDouble(PurchasesRate::getBuyingRate).toArray();
        double[] sellingRates = rates.stream().mapToDouble(PurchasesRate::getSellingRate).toArray();
        return new CurrencyStatistics(new RateStatistics(buyingRates), new RateStatistics(sellingRates));
    }

}
