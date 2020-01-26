package pl.parser.nbp.ChartFile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.NavigableSet;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class ChartFileServiceTest {

    @Autowired
    private ChartFileService chartFileService;

    @Test
    void shouldThrowIllegalArgumentException_OnFindFilesBy_WhenPassedIncorrectDates() {
        Date to = new Date(979081200000L);
        Date from = new Date(947458800000L);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> chartFileService.findFilesBy(ChartType.c, from, to))
                .withMessage("Dates must be equal or after of 2002");
    }

    @Test
    void shouldThrowIllegalArgumentException_OnFindFilesBy_WhenPassedSwappedDates() {
        Date to = new Date(1010617200000L);
        Date from = new Date(1042153200000L);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> chartFileService.findFilesBy(ChartType.c, from, to))
                .withMessage("First date introduced must be before the second");
    }

    @Test
    void shouldThrowIllegalArgumentException_OnFindFileBy_WhenPassedIncorrectDate() {
        Date from = new Date(979081200000L);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> chartFileService.findFileBy(from, ChartType.c))
                .withMessage("Dates must be equal or after of 2002");
    }

    @Test
    void shouldInstantiateChartFileDirectory() {
        assertThat(chartFileService.getAllFiles(2002, 2007), notNullValue());
    }

    @Test
    void shouldFindFilesByDatesAndChartType() {
        // given
        Date from = new Date(1105311600000L);
        Date to = new Date(1136847600000L);

        // when
        NavigableSet<ChartFile> files = chartFileService.findFilesBy(ChartType.c, from, to);

        // then
        assertThat(files, notNullValue());
    }

    @Test
    void shouldFindFile() {
        // given
        Date date = new Date(1105311600000L); // 26-01-2005

        // when
        ChartFile file = chartFileService.findFileBy(date, ChartType.c);

        // then
        assertThat(file.getPublicationDate(), is(date));
    }

}
