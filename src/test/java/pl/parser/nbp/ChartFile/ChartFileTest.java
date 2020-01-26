package pl.parser.nbp.ChartFile;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.text.ParseException;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChartFileTest {

    @Test
    public void shouldRetrieveCurrencyRateChart() throws ParseException {
        ChartFile chartFile = new ChartFile("c003z200107");

    }

    @Test
    public void shouldInstantiateChartFileWithCorrectFields() throws ParseException {
        // given
        ChartFile chartFile = new ChartFile("c003z200107");

        // then
        assertThat(chartFile.getPublicationDate(), is(equalTo(new Date())));
        assertThat(chartFile.getType(), is(equalTo('c')));
    }



}
