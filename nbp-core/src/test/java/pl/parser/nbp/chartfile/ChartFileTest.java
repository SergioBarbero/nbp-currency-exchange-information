package pl.parser.nbp.chartfile;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.parser.nbp.ratechart.CurrencyRateChart;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;

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

    static Stream<Arguments> files() {
        return Stream.of(
                Arguments.of("First file is more recent", new ChartFile("c003z200107"), new ChartFile("c002z190103"), 1),
                Arguments.of("Second file is more recent", new ChartFile("c002z190103"), new ChartFile("c003z200107"), -1),
                Arguments.of("First file is more recent", new ChartFile("a003z200107"), new ChartFile("c003z200107"), 1),
                Arguments.of("Second file is more recent", new ChartFile("c003z200107"), new ChartFile("a003z200107"), -1),
                Arguments.of("Files are the same", new ChartFile("c003z200107"), new ChartFile("c003z200107"), 0)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("files")
    public void shouldApropiatedCompareTo(String name, ChartFile firstFile, ChartFile secondFile, int expectedCompareTo) {
        assertThat(firstFile.compareTo(secondFile)).isEqualTo(expectedCompareTo);

    }
}
