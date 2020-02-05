package pl.parser.nbp.Util;

import java.io.IOException;
import java.net.URL;

public final class FileUtil {

    private FileUtil() {
        throw new AssertionError();
    }

    public static String readContentFromUrl(String url) throws IOException {
        return new String(new URL(url).openStream().readAllBytes());
    }
}
