package pl.parser.nbp.chartfile;

import org.junit.jupiter.api.Test;
import pl.parser.nbp.ratechart.CurrencyRateChart;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class ChartFileTest {

    private static final DateFormat FORMAT = new SimpleDateFormat("yyMMdd");

    @Test
    public void shouldThrowIllegalArgumentException_WhenTypeIsUnknown() {
        assertThatIllegalArgumentException().isThrownBy(() -> new ChartFile("d003z200107"));
    }

    @Test
    public void shouldThrowIllegalArgumentException_WhenTypeIsNotC() {
        // given
        ChartFile chartFile = new ChartFile("a003z200107");

        // when then
        assertThatIllegalArgumentException().isThrownBy(chartFile::retrieveCurrencyRateChart);
    }

    @Test
    public void shouldRetrieveCurrencyRateChart() {
        // given
        ChartFile chartFile = new ChartFile("c002z190103");

        // when
        CurrencyRateChart currencyRateChart = chartFile.retrieveCurrencyRateChart();

        // then
        assertThat(currencyRateChart).isNotNull();
    }

    @Test
    public void shouldGetUrl() {
        // given
        ChartFile chartFile = new ChartFile("c003z200107");

        // when then
        assertThat(chartFile.getUrl()).isEqualTo("http://www.nbp.pl/kursy/xml/c003z200107.xml");
    }

    @Test
    public void shouldInstantiateChartFileWithCorrectFields() throws ParseException {
        // given
        ChartFile chartFile = new ChartFile("c003z200107");

        // when then
        assertThat(chartFile.getPublicationDate()).isEqualTo(FORMAT.parse("200107"));
        assertThat(chartFile.getType()).isEqualTo(ChartType.c);
    }
}
