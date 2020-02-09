package pl.parser.nbp.ChartFile;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NavigableSet;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class ChartFileRemoteServiceTest {

    DateFormat format = new SimpleDateFormat("yyMMdd");

    @Autowired
    private ChartFileService chartFileService;

    @MockBean
    private ChartFileRemoteService.Directory directory;

    private static NavigableSet<ChartFile> chartFiles  = new TreeSet<>();

    @BeforeAll
    static void init() {
        chartFiles.add(new ChartFile("a001z190102"));
        chartFiles.add(new ChartFile("c002z190103"));
        chartFiles.add(new ChartFile("c003z190104"));
        chartFiles.add(new ChartFile("a004z190105"));
        chartFiles.add(new ChartFile("a005z190106"));
        chartFiles.add(new ChartFile("c006z190107"));
        chartFiles.add(new ChartFile("c007z190108"));
        chartFiles.add(new ChartFile("c008z190109"));
        chartFiles.add(new ChartFile("c009z190113"));
        chartFiles.add(new ChartFile("c0010z190115"));
    }


    @Test
    void shouldThrowIllegalArgumentException_OnFindFilesBy_WhenPassedIncorrectDates() throws ParseException {
        // given
        Date to = format.parse("010110");
        Date from = format.parse("010102");

        // when
        assertThatIllegalArgumentException()
                .isThrownBy(() -> chartFileService.findFilesBy(from, to, ChartType.c))

                // then
                .withMessage("Dates must be in or after 2002");
    }

    @Test
    void shouldThrowIllegalArgumentException_OnFindFilesBy_WhenPassedSwappedDates() throws ParseException {
        // given
        Date from = format.parse("010110");
        Date to = format.parse("010102");

        // when
        assertThatIllegalArgumentException()
                .isThrownBy(() -> chartFileService.findFilesBy(from, to, ChartType.c))

                // then
                .withMessage("First date introduced must be before or equals the second");
    }

    @Test
    void shouldThrowIllegalArgumentException_OnFindFileBy_WhenPassedIncorrectDate() throws ParseException {
        // given
        Date date = format.parse("010110");

        // when
        assertThatIllegalArgumentException()
                .isThrownBy(() -> chartFileService.findFileBy(date, ChartType.c))

                // then
                .withMessage("Date must be in or after 2002");
    }

    @Test
    void shouldFindFilesByDatesAndChartType() throws ParseException {
        // given
        when(directory.findChartFiles(2019)).thenReturn(chartFiles);
        Date from = format.parse("190103");
        Date to = format.parse("190110");

        // when
        NavigableSet<ChartFile> files = chartFileService.findFilesBy(from, to, ChartType.c);

        // then
        assertThat(files, notNullValue());
    }

    @Test
    void shouldFindFile() throws ParseException {
        // given
        when(directory.findChartFiles(2019)).thenReturn(chartFiles);
        Date date = format.parse("190103");

        // when
        ChartFile file = chartFileService.findFileBy(date, ChartType.c);

        // then
        assertThat(file.getPublicationDate(), is(date));
    }

}
