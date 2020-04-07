package pl.parser.nbp.currencystatistics;

import org.springframework.stereotype.Service;
import pl.parser.nbp.chartfile.ChartType;
import pl.parser.nbp.rate.CurrencyCode;
import pl.parser.nbp.rate.PurchasesRate;
import pl.parser.nbp.ratechart.CurrencyRateChartService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyStatisticsService {

    private final CurrencyRateChartService currencyRateChartService;

    public CurrencyStatisticsService(CurrencyRateChartService currencyRateChartService) {
        this.currencyRateChartService = currencyRateChartService;
    }

    public CurrencyStatistics getStatisticsInRangeAndType(Date startDate, Date endDate, CurrencyCode currencyCode) {
        List<PurchasesRate> rates = getPurchasesRates(startDate, endDate, currencyCode);

        double[] buyingRates = rates.stream().mapToDouble(PurchasesRate::getBuyingRate).toArray();
        double[] sellingRates = rates.stream().mapToDouble(PurchasesRate::getSellingRate).toArray();
        return new CurrencyStatistics(new RateStatistics(buyingRates), new RateStatistics(sellingRates));
    }

    private List<PurchasesRate> getPurchasesRates(Date startDate, Date endDate, CurrencyCode currencyCode) {
        return currencyRateChartService.getCurrencyRateCharts(startDate, endDate, ChartType.c)
                    .stream()
                    .map(chart -> chart.getRateByCurrency(currencyCode))
                    .collect(Collectors.toList());
    }
}
