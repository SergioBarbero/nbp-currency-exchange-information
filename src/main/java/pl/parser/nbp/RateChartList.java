package pl.parser.nbp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class RateChartList {

    private final static int limitYear = 2002;
    private final static String rateChartListBaseUrl = "http://www.nbp.pl/kursy/xml/";

    private SortedSet<String> filesNames;

    /**
     * Gets the closer fileName given the date parameter, this means, if exact file was not provided in specified date, the previous to it
     * @param date specified date
     * @param type letter of file
     * @return name of the file
     */
    public String getFileName(Date date, char type) {
        DateFormat df = new SimpleDateFormat("yyMMdd");
        String expeditionDate = df.format(date);
        String dateCode = "000000";
        String closerFileName = "";
        Iterator<String> it = this.filesNames.iterator();
        while (dateCode.compareTo(expeditionDate) < 0) {
            String fileName = it.next();
            dateCode = fileName.substring(fileName.length() - 6);
            closerFileName = fileName;
        }
        return type + closerFileName.substring(1);
    }

    private static String buildUrl(int year) {
        if (year < 2015) {
            return rateChartListBaseUrl + "dir" + year + ".txt";
        }
        return rateChartListBaseUrl + "dir.txt";
    }

    private static SortedSet<String> retrieveList(int year) throws IOException {
        String url = buildUrl(year);
        String[] content = Utils.readFromUrl(url).split("\r\n");
        return new TreeSet<>(Arrays.asList(content));
    }

    public RateChartList(int year1, int year2) {
        if (year1 > limitYear || year2 > limitYear) {
            throw new IllegalArgumentException();
        }
        int fromYear = Math.min(year1, year2);
        int toYear = Math.max(year1, year2);
        this.filesNames = new TreeSet<>();
        for (int i = fromYear; i <= toYear; i++) {
            try {
                SortedSet<String> filesList = retrieveList(i);
                this.filesNames.addAll(filesList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
