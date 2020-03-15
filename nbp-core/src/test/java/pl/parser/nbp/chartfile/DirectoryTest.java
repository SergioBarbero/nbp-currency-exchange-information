package pl.parser.nbp.chartfile;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThat;

public class DirectoryTest {

    private final DirectoryService directoryService = new NbpDirectoryClient();

    @Test
    void shouldRetrieveDataFromRemoteServer() {
        Set<ChartFile> chartFiles = directoryService.findChartFiles(2019);

        assertThat(chartFiles).isNotNull();
        assertThat(chartFiles.size()).isGreaterThan(0);
    }

    @Test
    void shouldThrowErrorFileNotAvailable() {
        assertThatExceptionOfType(FileNotLoadedException.class)
                .isThrownBy(() -> directoryService.findChartFiles(2000));
    }
}
