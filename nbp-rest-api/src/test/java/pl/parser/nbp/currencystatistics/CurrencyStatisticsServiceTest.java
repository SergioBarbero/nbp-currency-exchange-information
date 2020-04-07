package pl.parser.nbp.currencystatistics;

import org.junit.jupiter.api.Test;
import pl.parser.nbp.chartfile.ChartType;
import pl.parser.nbp.rate.CurrencyCode;
import pl.parser.nbp.rate.PurchasesRate;
import pl.parser.nbp.ratechart.CurrencyRateChartC;
import pl.parser.nbp.ratechart.CurrencyRateChartService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CurrencyStatisticsServiceTest {

    private static final DateFormat FORMAT = new SimpleDateFormat("yyMMdd");

    private final List<PurchasesRate> purchasesRates = List.of(
            new PurchasesRate("euro", 1, CurrencyCode.EUR, 1L, 2L),
            new PurchasesRate("pound", 1, CurrencyCode.GBP, 1L, 3L),
            new PurchasesRate("dollar", 1, CurrencyCode.USD, 1L, 1L)
    );

    private final List<CurrencyRateChartC> currencyRateCharts = List.of(
            new CurrencyRateChartC('c', "a001z190102", FORMAT.parse("190103"), purchasesRates),
            new CurrencyRateChartC('c', "a001z190102", FORMAT.parse("190104"), purchasesRates),
            new CurrencyRateChartC('c', "a001z190102", FORMAT.parse("190105"), purchasesRates)
    );

    public CurrencyStatisticsServiceTest() throws ParseException {
    }

    @Test
    public void shouldReturnStatistics() throws ParseException {
        // given
        CurrencyRateChartService chartFileService = mock(CurrencyRateChartService.class);
        CurrencyStatisticsService currencyStatisticsService = new CurrencyStatisticsService(chartFileService);
        Date from = FORMAT.parse("190103");
        Date to = FORMAT.parse("190115");
        given(chartFileService.getCurrencyRateCharts(from, to, ChartType.c)).willReturn(currencyRateCharts);

        // when
        CurrencyStatistics statistics = currencyStatisticsService.getStatisticsInRangeAndType(from, to, CurrencyCode.EUR);

        // then
        assertThat(statistics.getBuyingRate().getMean()).isEqualTo(2.0);
        assertThat(statistics.getSellingRate().getMean()).isEqualTo(1.0);
    }

}
