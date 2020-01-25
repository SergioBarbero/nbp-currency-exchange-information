package pl.parser.nbp.ChartFile;
import pl.parser.nbp.Util.FileUtil;

import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ChartFileContainer {

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
        TreeSet<Date> dates = this.files.stream().filter(e -> e.getType() == type).map(ChartFile::getPublicationDate).collect(Collectors.toCollection(TreeSet::new));
        String closest = df.format(dates.floor(date));
        String regex = String.format("^%s.*%s$", type, closest);
        return this.files.stream().filter(e -> e.getFileName().matches(regex)).findFirst().get();
    }

    public static String buildUrl(int year) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return (year == currentYear) ? rateChartListBaseUrl + "dir.txt" : rateChartListBaseUrl + "dir" + year + ".txt";
    }

    private static NavigableSet<String> retrieveList(int year) throws IOException {
        String url = buildUrl(year);
        String[] content = FileUtil.readContentFromUrl(url).split("\r\n");
        return new TreeSet<>(Arrays.asList(content));
    }

    public NavigableSet<ChartFile> getFiles() {
        return files;
    }

    public NavigableSet<ChartFile> filterList(char type, Date dateStart, Date dateEnd) {
        ChartFile fileStart = this.findFile(dateStart, type);
        ChartFile fileEnd = this.findFile(dateEnd, type);
        return this.getFiles().stream().filter(file -> file.getType() == type).collect(Collectors.toCollection(TreeSet::new)).subSet(fileStart, true, fileEnd, true);
    }

    public ChartFileContainer(int year1, int year2) {
        if (year1 < limitYear || year2 < limitYear) {
            throw new IllegalArgumentException();
        }
        int fromYear = Math.min(year1, year2);
        int toYear = Math.max(year1, year2);
        this.files = new TreeSet<>();
        for (int i = fromYear; i <= toYear; i++) {
            try {
                NavigableSet<String> filesList = retrieveList(i);
                for (String fileName : filesList) this.files.add(new ChartFile(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
