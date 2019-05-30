package pl.parser.nbp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Returns the sorted sublist from older to newer by type of file
     * @param letter type of file
     * @return empty array if the type was not identified, array with names in contrary case
     */
    public String[] getSortedSublist(char letter) {
        List<String> sublist = Arrays.asList(this.getNameList()).stream().filter(fileName -> fileName.startsWith(String.valueOf(letter))).collect(Collectors.toList());
        Collections.sort(sublist);
        String[] sublistArray = new String[sublist.size()];
        return sublist.toArray(sublistArray);
    }
}
