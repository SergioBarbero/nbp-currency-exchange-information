package pl.parser.nbp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RateChartList {

    private final static int limitYear = 2002;
    private final static String rateChartListBaseUrl = "http://www.nbp.pl/kursy/xml/";

    private String[] filesNames;

    public RateChartList(int year) throws IOException {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        URL listUrl;
        if (year < limitYear || year > currentYear) {
            throw new IllegalArgumentException("Please, introduce a valid year between 2002 and " + currentYear);
        } else if (year < 2015) {
            listUrl = new URL(rateChartListBaseUrl + "dir" + year + ".txt");
        } else {
            listUrl = new URL(rateChartListBaseUrl + "dir.txt");
        }
        InputStream list = listUrl.openStream();
        this.filesNames = new String(list.readAllBytes()).split("\r\n");
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
