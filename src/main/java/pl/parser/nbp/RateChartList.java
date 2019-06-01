package pl.parser.nbp;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class RateChartList {

    private final static int limitYear = 2002;
    private final static String rateChartListBaseUrl = "http://www.nbp.pl/kursy/xml/";

    private NavigableSet<ChartFile> files;

    /**
     * Gets the closer fileName given the date parameter, this means, if exact file was not provided in specified date, the previous to it
     * @param date specified date
     * @param type letter of file
     * @return name of the file
     */
    public ChartFile findFile(Date date, char type) {
        DateFormat df = new SimpleDateFormat("yyMMdd");
        String expeditionDate = df.format(date);
        String regex = "^" + type + ".*" + expeditionDate + "$";
        return this.files.stream().filter(e -> e.getFileName().matches(regex)).findFirst().get();
    }

    public static String buildUrl(int year) {
        return (year < 2015) ? rateChartListBaseUrl + "dir" + year + ".txt" : rateChartListBaseUrl + "dir.txt";
    }

    private static SortedSet<String> retrieveList(int year) throws IOException {
        String url = buildUrl(year);
        String[] content = Utils.readFromUrl(url).split("\r\n");
        return new TreeSet<>(Arrays.asList(content));
    }

    public NavigableSet<ChartFile> getFilesNames() {
        return files;
    }

    public RateChartList(int year1, int year2) {
        if (year1 < limitYear || year2 < limitYear) {
            throw new IllegalArgumentException();
        }
        int fromYear = Math.min(year1, year2);
        int toYear = Math.max(year1, year2);
        this.files = new TreeSet<>();
        for (int i = fromYear; i <= toYear; i++) {
            try {
                SortedSet<String> filesList = retrieveList(i);
                for(String fileName: filesList) {
                    this.files.add(new ChartFile(fileName));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
