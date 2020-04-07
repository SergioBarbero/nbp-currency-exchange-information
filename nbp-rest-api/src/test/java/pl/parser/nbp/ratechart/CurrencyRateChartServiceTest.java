package pl.parser.nbp.ratechart;

import org.junit.jupiter.api.Test;
import pl.parser.nbp.chartfile.ChartFile;
import pl.parser.nbp.chartfile.ChartFileService;
import pl.parser.nbp.chartfile.ChartType;
import pl.parser.nbp.chartfile.FileNotFoundException;
import pl.parser.nbp.rate.CurrencyCode;
import pl.parser.nbp.rate.PurchasesRate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CurrencyRateChartServiceTest {

    private static final DateFormat FORMAT = new SimpleDateFormat("yyMMdd");

    private final ChartFileService chartFileService = mock(ChartFileService.class);
    private final CurrencyRateChartService currencyRateChartService = new CurrencyRateChartService(chartFileService);

    private final List<PurchasesRate> purchasesRates = List.of(
            new PurchasesRate("euro", 1, CurrencyCode.EUR, 1L, 2L),
            new PurchasesRate("pound", 1, CurrencyCode.GBP, 1L, 3L),
            new PurchasesRate("dollar", 1, CurrencyCode.USD, 1L, 1L)
    );

    private final List<CurrencyRateChart> currencyRateCharts = List.of(
            new CurrencyRateChartC('c', "a001z190102", FORMAT.parse("190103"), purchasesRates),
            new CurrencyRateChartC('c', "a001z190102", FORMAT.parse("190104"), purchasesRates),
            new CurrencyRateChartC('c', "a001z190102", FORMAT.parse("190105"), purchasesRates)
    );

    public CurrencyRateChartServiceTest() throws ParseException {}

    @Test
    void shouldGetListOfCurrencyCharts() throws ParseException {
        // given
        Date from = FORMAT.parse("190103");
        Date to = FORMAT.parse("190107");

        ChartFile chartFile1 = mock(ChartFile.class);
        ChartFile chartFile2 = mock(ChartFile.class);
        ChartFile chartFile3 = mock(ChartFile.class);
        given(chartFileService.findFilesBy(from, to, ChartType.c)).willReturn(Set.of(chartFile1, chartFile2, chartFile3));

        given(chartFile1.retrieveCurrencyRateChart()).willReturn(currencyRateCharts.get(0));
        given(chartFile2.retrieveCurrencyRateChart()).willReturn(currencyRateCharts.get(1));
        given(chartFile3.retrieveCurrencyRateChart()).willReturn(currencyRateCharts.get(2));

        // when
        List<CurrencyRateChartC> currencyRateCharts = currencyRateChartService.getCurrencyRateCharts(from, to, ChartType.c);

        // then
        assertThat(currencyRateCharts.size()).isEqualTo(3);
        assertThat(currencyRateCharts.get(0)).isInstanceOf(CurrencyRateChartC.class);
        assertThat(currencyRateCharts.get(1)).isInstanceOf(CurrencyRateChartC.class);
        assertThat(currencyRateCharts.get(2)).isInstanceOf(CurrencyRateChartC.class);
    }

    @Test
    void shouldGetCurrencyChart() throws ParseException {
        // given
        Date date = FORMAT.parse("190103");
        CurrencyRateChart currencyRateChart = new CurrencyRateChartC('c',"c003z190104", date, purchasesRates);
        given(chartFileService.findFileBy(date, ChartType.c)).willReturn(Optional.of(mock(ChartFile.class)));
        given(chartFileService.findFileBy(date, ChartType.c).get().retrieveCurrencyRateChart()).willReturn(currencyRateChart);

        // when
        CurrencyRateChartC chart = (CurrencyRateChartC) currencyRateChartService.getCurrencyRateChart(date, ChartType.c);

        // then
        assertThat(chart.getChartNumber()).isEqualTo(null);
        assertThat(chart.getPublicationDate()).isEqualTo(null);
        assertThat(chart.getCurrencies()).isEqualTo(purchasesRates);
        assertThat(chart.getType()).isEqualTo('c');
        assertThat(chart.getUid()).isEqualTo("c003z190104");
    }

    @Test
    void shouldFail_WhenNotFindingCurrencyChart() throws ParseException {
        Date date = FORMAT.parse("190103");
        given(chartFileService.findFileBy(date, ChartType.c)).willReturn(Optional.empty());

        assertThatExceptionOfType(FileNotFoundException.class)
                .isThrownBy(() -> currencyRateChartService.getCurrencyRateChart(date, ChartType.c))
                .withMessage("Chart from 2019-01-03 was not found");
    }

    @Test
    void shouldFail_WhenFindingNoCurrencyCharts() throws ParseException {
        Date from = FORMAT.parse("190103");
        Date to = FORMAT.parse("190107");

        given(chartFileService.findFilesBy(from, to, ChartType.c)).willReturn(new HashSet<>());

        assertThatExceptionOfType(FileNotFoundException.class)
                .isThrownBy(() -> currencyRateChartService.getCurrencyRateCharts(from, to, ChartType.c))
                .withMessage("There was no charts from 2019-01-03 to 2019-01-07");

    }
}
