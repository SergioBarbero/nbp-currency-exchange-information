package pl.parser.nbp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class RateChartList {

    private static RateChartList list = new RateChartList();

    private final static String rateChartListUrl = "http://www.nbp.pl/kursy/xml/dir.txt";

    private String[] filesNames;

    private RateChartList(){
        try {
            URL listUrl = new URL(rateChartListUrl);
            InputStream list = listUrl.openStream();
            this.filesNames = new String(list.readAllBytes()).split("\r\n");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RateChartList getChartList() {
        if(list == null) {
            list = new RateChartList();
        }
        return list;
    }

    public String[] getNameList() {
        return this.filesNames;
    }
}
