package pl.parser.nbp.chartfile;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChartFileServiceImplTest {

    private static final DateFormat FORMAT = new SimpleDateFormat("yyMMdd");

    protected DirectoryService directoryService = mock(DirectoryService.class);
    protected ChartFileService chartFileService = new ChartFileServiceImpl(directoryService);

    protected static NavigableSet<ChartFile> chartFiles  = new TreeSet<>();

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
    }


    @Test
    public void shouldThrowIllegalArgumentException_OnFindFilesBy_WhenPassedIncorrectDates() throws ParseException {
        // given
        Date from = FORMAT.parse("010102");
        Date to = FORMAT.parse("010110");

        // when
        assertThatIllegalArgumentException()
                .isThrownBy(() -> chartFileService.findFilesBy(from, to, ChartType.c))

                // then
                .withMessage("Dates must be in or after 2002");
    }

    @Test
    public void shouldThrowIllegalArgumentException_OnFindFilesBy_WhenPassedSwappedDates() throws ParseException {
        // given
        Date from = FORMAT.parse("010110");
        Date to = FORMAT.parse("010102");

        // when
        assertThatIllegalArgumentException()
                .isThrownBy(() -> chartFileService.findFilesBy(from, to, ChartType.c))

                // then
                .withMessage("First date introduced must be before or equals the second");
    }

    @Test
    public void shouldFindFilesByDatesAndChartType() throws ParseException {
        // given
        when(directoryService.findChartFiles(2019)).thenReturn(chartFiles);
        Date from = FORMAT.parse("190103");
        Date to = FORMAT.parse("190110");

        // when
        NavigableSet<ChartFile> files = chartFileService.findFilesBy(from, to, ChartType.c);

        // then
        assertThat(files).isNotNull();
        assertThat(files.size()).isEqualTo(4);
        assertThat(files.stream().map(ChartFile::getType).toArray())
                .isEqualTo(IntStream.range(0, 4).mapToObj(x -> ChartType.c).toArray());
    }

    @Test
    void shouldFindFilesByDates() throws ParseException {
        // given
        when(directoryService.findChartFiles(2019)).thenReturn(chartFiles);
        Date from = FORMAT.parse("190103");
        Date to = FORMAT.parse("190110");

        // when
        NavigableSet<ChartFile> files = chartFileService.findFilesBy(from, to);

        // then
        assertThat(files).isNotNull();
        assertThat(files.size()).isEqualTo(6);
    }

    @Test
    void shouldThrowIllegalArgumentException_OnFindFileBy_WhenPassedIncorrectDate() throws ParseException {
        // given
        Date date = FORMAT.parse("010110");

        // when
        assertThatIllegalArgumentException()
                .isThrownBy(() -> chartFileService.findFileBy(date, ChartType.c))

                // then
                .withMessage("Date must be in or after 2002");
    }

    @Test
    void shouldReturnEmptyOptional_OnFindFileBy_WhenFileNotFound() throws ParseException {
        // given
        when(directoryService.findChartFiles(2019)).thenReturn(chartFiles);
        Date date = FORMAT.parse("190110");

        Optional<ChartFile> fileBy = chartFileService.findFileBy(date, ChartType.c);

        // then
        assertThat(fileBy).isEmpty();
    }

    @Test
    void shouldFindFile() throws ParseException {
        // given
        when(directoryService.findChartFiles(2019)).thenReturn(chartFiles);
        Date date = FORMAT.parse("190103");

        // when
        ChartFile file = chartFileService.findFileBy(date, ChartType.c).get();

        // then
        assertThat(file).isEqualByComparingTo(new ChartFile("c002z190103"));
    }

}
