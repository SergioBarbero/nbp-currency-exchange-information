package pl.parser.nbp.Util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class FileUtilTest {

    @Test
    void shouldReadContentFromUrl() throws IOException {
        String s = FileUtil.readContentFromUrl("https://www.google.com");
        assertThat(s).isNotBlank();
    }
}
