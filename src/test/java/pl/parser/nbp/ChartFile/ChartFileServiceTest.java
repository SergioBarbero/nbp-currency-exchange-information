package pl.parser.nbp.ChartFile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class ChartFileServiceTest {

    @Autowired
    private ChartFileService chartFileService;

    @Test
    void shouldThrowIllegalStateException_WhenPassedIncorrectYear() {}

    @Test
    void shouldInstantiateChartFileDirectory() {
        assertThat(chartFileService.getAllFiles(2002, 2007), notNullValue());
    }


    @Test
    void shouldFindFile() {
        // given
        Date date = new Date(1106750000000L); // 26-01-2005

        // when
        ChartFile file = chartFileService.findFileBy(date, ChartType.c);

        // then
        assertThat(file.getPublicationDate(), is(date));
    }

}
