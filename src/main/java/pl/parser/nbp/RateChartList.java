package pl.parser.nbp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class RateChartList {

    private final static int limitYear = 2002;
    private int year;
    private final static String rateChartListBaseUrl = "http://www.nbp.pl/kursy/xml/";

    private String[] filesNames;

    public String getFileName(Date date, char type) {
        DateFormat df = new SimpleDateFormat("yyMMdd");
        String expeditionDate = df.format(date);
        String letter = String.valueOf(type);
        String regex = "^" + letter + ".*" + expeditionDate + "$";
        return Arrays.stream(this.getFilesNames()).filter(fileName -> fileName.matches(regex)).collect(Collectors.toList()).get(0);
    }

    private static String buildUrl(int year) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String url;
        if (year < limitYear || year > currentYear) {
            throw new IllegalArgumentException("Please, introduce a valid year between 2002 and " + currentYear);
        } else if (year < 2015) {
            url = rateChartListBaseUrl + "dir" + year + ".txt";
        } else {
            url = rateChartListBaseUrl + "dir.txt";
        }
        return url;
    }

    public RateChartList(int year) throws IOException {
        URL url = new URL(buildUrl(year));
        this.year = year;
        InputStream list = url.openStream();
        this.filesNames = new String(list.readAllBytes()).split("\r\n");
    }

    public int getYear() {
        return this.year;
    }

    public String[] getFilesNames() {
        return this.filesNames;
    }

    public RateChartList sublist(String fromName, String toName) {
        List filesNames = Arrays.asList(this.filesNames);
        int bigger = fromName.compareTo(toName);
        if (bigger > 0) {
            throw new IllegalArgumentException("Final name must be bigger than starting name");
        }
        int index1 = filesNames.indexOf(fromName);
        int index2 = filesNames.indexOf(toName);
        if (index1 == -1 || index2 == -1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Arrays.sort(this.filesNames);
        filesNames = filesNames.subList(index1, index2);
        return new RateChartList(filesNames, this.year);
    }

    private RateChartList(List<String> filesNames, int year) {
        String[] fileNamesArray = new String[filesNames.size()];
        filesNames.toArray(fileNamesArray);
        this.filesNames = fileNamesArray;
        this.year = year;
    }

    /**
     * Crops the list to the specified type
     * @param type type of file
     * @return empty array if the type was not identified, array with names in contrary case
     */
    public RateChartList byType(char type) {
        List<String> sublist = Arrays.stream(this.getFilesNames()).filter(fileName -> fileName.startsWith(String.valueOf(type))).collect(Collectors.toList());
        return new RateChartList(sublist, this.year);
    }

    private void setFilesNames(String[] filesNames) {
        this.filesNames = filesNames;
    }
}
