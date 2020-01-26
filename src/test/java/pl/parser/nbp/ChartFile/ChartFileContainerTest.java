package pl.parser.nbp.ChartFile;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChartFileContainerTest {

    @Test
    void shouldInstantiateChartFileDirectory() {
        // given
        ChartFileService chartFileService = new ChartFileService(2002, 2007);

        // then
        assertThat(chartFileService.getAllFiles(), notNullValue());
    }


    @Test
    void shouldFindFile() {
        // given
        ChartFileService chartFileService = new ChartFileService(2002, 2007);
        Date date = new Date(1106750000000L); // 26-01-2005

        // when
        ChartFile file = chartFileService.findFileBy(date, ChartType.c);

        // then
        assertThat(file.getPublicationDate(), is(date));
    }

}
