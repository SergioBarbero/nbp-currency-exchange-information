package pl.parser.nbp.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class Utils {

    private Utils() {
        throw new AssertionError();
    }

    public static String readFromUrl(String url) throws IOException {
        URL fileUrl = new URL(url);
        InputStream in = fileUrl.openStream();
        return new String(in.readAllBytes());
    }
}
