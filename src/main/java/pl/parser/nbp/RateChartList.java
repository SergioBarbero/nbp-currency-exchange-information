package pl.parser.nbp;

import java.io.IOException;

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
        NavigableSet<String> dateList = this.filesNames.stream().map(name -> name.substring(name.length() - 6 )).collect(Collectors.toCollection(TreeSet::new));
        String closestFile = dateList.floor(expeditionDate);
        String regex = "^" + type + ".*" + closestFile + "$";
        return this.filesNames.stream().filter(name -> name.matches(regex)).findFirst().get();
    }

    public static String buildUrl(int year) {
        return (year < 2015) ? rateChartListBaseUrl + "dir" + year + ".txt" : rateChartListBaseUrl + "dir.txt";
    }

    private static SortedSet<String> retrieveList(int year) throws IOException {
        String url = buildUrl(year);
        String[] content = Utils.readFromUrl(url).split("\r\n");
        return new TreeSet<>(Arrays.asList(content));
    }

    public SortedSet<String> getFilesNames() {
        return filesNames;
    }

    public RateChartList(int year1, int year2) {
        if (year1 < limitYear || year2 < limitYear) {
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
