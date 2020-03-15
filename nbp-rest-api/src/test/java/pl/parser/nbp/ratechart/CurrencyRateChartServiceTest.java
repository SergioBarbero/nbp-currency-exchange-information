package pl.parser.nbp.ratechart;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.parser.nbp.chartfile.ChartFile;
import pl.parser.nbp.chartfile.ChartFileService;
import pl.parser.nbp.chartfile.ChartType;
import pl.parser.nbp.rate.CurrencyCode;
import pl.parser.nbp.rate.PurchasesRate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CurrencyRateChartServiceTest {

    private static final DateFormat FORMAT = new SimpleDateFormat("yyMMdd");

    private final ChartFileService chartFileService = mock(ChartFileService.class);
    private final CurrencyRateChartService currencyRateChartService = new CurrencyRateChartService(chartFileService);

    private final Set<ChartFile> chartFiles = ImmutableSet.of(
            new ChartFile("a001z190102"),
            new ChartFile("c002z190103"),
            new ChartFile("c003z190104")
    );

    private final List<PurchasesRate> purchasesRates = ImmutableList.of(
            new PurchasesRate("euro", 1, CurrencyCode.EUR, 1L, 2L),
            new PurchasesRate("pound", 1, CurrencyCode.GBP, 1L, 3L),
            new PurchasesRate("dollar", 1, CurrencyCode.USD, 1L, 1L)
    );

    void shouldGetListOfCurrencyCharts() throws ParseException {}

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

    void shouldFail_WhenNotFindingCurrencyChart() throws ParseException {
        Date date = FORMAT.parse("190103");
    }

    void shouldFail_WhenFindingNoCurrencyCharts() {}
}
