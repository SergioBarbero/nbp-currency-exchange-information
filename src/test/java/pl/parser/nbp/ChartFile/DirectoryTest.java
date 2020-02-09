package pl.parser.nbp.ChartFile;

import org.junit.jupiter.api.Test;

import java.util.NavigableSet;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class DirectoryTest {

    private FilesDirectory directory = new FilesDirectory();

    @Test
    void shouldRetrieveDataFromRemoteServer() {
        NavigableSet<ChartFile> chartFiles = directory.findChartFiles(2019);

        assertThat(chartFiles).isNotNull();
        assertThat(chartFiles.size()).isGreaterThan(0);
    }

    @Test
    void shouldThrowErrorFileNotAvailable() {
        assertThatExceptionOfType(FileNotLoadedException.class)
                .isThrownBy(() -> directory.findChartFiles(2000));
    }
}
