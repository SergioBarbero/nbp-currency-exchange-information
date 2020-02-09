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
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ChartFileServiceImplTest {

    DateFormat format = new SimpleDateFormat("yyMMdd");

    @Autowired
    private ChartFileService chartFileService;

    @MockBean
    private FilesDirectory directory;

    private static NavigableSet<ChartFile> chartFiles  = new TreeSet<>();

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
        chartFiles.add(new ChartFile("c0010z190115"));
    }


    @Test
    void shouldThrowIllegalArgumentException_OnFindFilesBy_WhenPassedIncorrectDates() throws ParseException {
        // given
        Date from = format.parse("010102");
        Date to = format.parse("010110");

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
    void shouldFindFilesByDatesAndChartType() throws ParseException {
        // given
        when(directory.findChartFiles(2019)).thenReturn(chartFiles);
        Date from = format.parse("190103");
        Date to = format.parse("190110");

        // when
        NavigableSet<ChartFile> files = chartFileService.findFilesBy(from, to, ChartType.c);

        // then
        assertThat(files).isNotNull();
        assertThat(files.size()).isEqualTo(4);
        assertThat(files.stream().map(ChartFile::getType).toArray())
                .isEqualTo(IntStream.range(0, 4).mapToObj(x -> ChartType.c).toArray());
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
    void shouldThrowFileNotFoundException_OnFindFileBy_WhenFileNotFound() throws ParseException {
        // given
        when(directory.findChartFiles(2019)).thenReturn(chartFiles);
        Date date = format.parse("190110");

        // when
        assertThatExceptionOfType(FileNotFoundException.class)
                .isThrownBy(() -> chartFileService.findFileBy(date, ChartType.c))
                .withMessage("Chart from 2019-01-10 was not found");

    }

    @Test
    void shouldFindFile() throws ParseException {
        // given
        when(directory.findChartFiles(2019)).thenReturn(chartFiles);
        Date date = format.parse("190103");

        // when
        ChartFile file = chartFileService.findFileBy(date, ChartType.c);

        // then
        assertThat(file).isEqualByComparingTo(new ChartFile("c002z190103"));
    }

}
