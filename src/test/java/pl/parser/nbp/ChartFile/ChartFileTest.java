package pl.parser.nbp.ChartFile;

import org.junit.jupiter.api.Test;
import pl.parser.nbp.RateChart.CurrencyRateChart;

import java.text.ParseException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChartFileTest {

    @Test
    public void shouldThrowIllegalArgumentException_WhenTypeIsUnknown() throws ParseException {
        // given
        ChartFile chartFile = new ChartFile("d003z200107");

        // when then
        assertThatIllegalArgumentException().isThrownBy(chartFile::retrieveCurrencyRateChart);
    }

    @Test
    public void shouldThrowIllegalStateException_WhenTypeIsNotC() throws ParseException {
        // given
        ChartFile chartFile = new ChartFile("a003z200107");

        // when then
        assertThatIllegalStateException().isThrownBy(chartFile::retrieveCurrencyRateChart);
    }

    @Test
    public void shouldRetrieveCurrencyRateChart() throws ParseException {
        // given
        ChartFile chartFile = new ChartFile("c003z200107");

        // when
        CurrencyRateChart currencyRateChart = chartFile.retrieveCurrencyRateChart();

        // then
        assertThat(currencyRateChart, notNullValue());
    }

    @Test
    public void shouldGetUrl() throws ParseException {
        // given
        ChartFile chartFile = new ChartFile("c003z200107");

        // when then
        assertThat(chartFile.getUrl(), is("http://www.nbp.pl/kursy/xml/c003z200107.xml"));
    }

    @Test
    public void shouldInstantiateChartFileWithCorrectFields() throws ParseException {
        // given
        ChartFile chartFile = new ChartFile("c003z200107");

        // when then
        assertThat(chartFile.getPublicationDate(), is(equalTo(new Date())));
        assertThat(chartFile.getType(), is(equalTo('c')));
    }
}
