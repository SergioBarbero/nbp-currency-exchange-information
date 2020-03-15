package pl.parser.nbp.ratechart;

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
import static org.mockito.Mockito.when;

public class CurrencyRateChartServiceTest {

    private static final DateFormat FORMAT = new SimpleDateFormat("yyMMdd");

    private final ChartFileService chartFileService = mock(ChartFileService.class);
    private final CurrencyRateChartService currencyRateChartService = new CurrencyRateChartService(chartFileService);

    private static NavigableSet<ChartFile> chartFiles = new TreeSet<>();
    private static List<PurchasesRate> purchasesRates = new ArrayList<>();

    @BeforeAll
    static void init() {
        chartFiles.add(new ChartFile("a001z190102"));
        chartFiles.add(new ChartFile("c002z190103"));
        chartFiles.add(new ChartFile("c003z190104"));
        chartFiles.add(new ChartFile("h004z190105"));
        chartFiles.add(new ChartFile("a005z190106"));
        chartFiles.add(new ChartFile("c006z190107"));
        chartFiles.add(new ChartFile("c007z190108"));
        chartFiles.add(new ChartFile("c008z190109"));
        chartFiles.add(new ChartFile("b009z190113"));
        chartFiles.add(new ChartFile("c010z190115"));
        purchasesRates.add(new PurchasesRate("euro", 1, CurrencyCode.EUR, 1L, 2L));
        purchasesRates.add(new PurchasesRate("pound", 1, CurrencyCode.GBP, 1L, 3L));
        purchasesRates.add(new PurchasesRate("dollar", 1, CurrencyCode.USD, 1L, 1L));
    }

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
    }

    void shouldFail_WhenNotFindingCurrencyChart() {}

    void shouldFail_WhenFindingNoCurrencyCharts() {}
}
