package pl.parser.nbp.ChartFile;
import org.springframework.util.Assert;
import pl.parser.nbp.Util.FileUtil;

import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public final class ChartFileService {

    private final static int LIMIT_YEAR = 2002;
    private final static String RATE_CHART_LIST_BASE_URL = "http://www.nbp.pl/kursy/xml/";

    private final int fromYear;
    private final int toYear;

    public ChartFileService(int year1, int year2) {
        Assert.state(year1 <= LIMIT_YEAR || year2 <= LIMIT_YEAR, "Year must be higer than " + LIMIT_YEAR);
        this.fromYear = Math.min(year1, year2);
        this.toYear = Math.max(year1, year2);
    }

    /**
     * Gets the closer fileName given the date parameter, this means, if exact file was not provided in specified date, the previous to it
     * @param date specified date
     * @param type letter of file
     * @return name of the file
     */
    public ChartFile findFileBy(Date date, ChartType type) {
        DateFormat df = new SimpleDateFormat("yyMMdd");
        NavigableSet<ChartFile> allFiles = this.getAllFiles();
        TreeSet<Date> dates = allFiles.stream()
                .filter(e -> e.getType().equals(type))
                .map(ChartFile::getPublicationDate)
                .collect(Collectors.toCollection(TreeSet::new));
        String closest = df.format(dates.floor(date));
        String regex = String.format("^%s.*%s$", type, closest);
        return allFiles.stream().filter(e -> e.getFileName().matches(regex)).findFirst().get();
    }

    public NavigableSet<ChartFile> getAllFiles() {
        TreeSet<ChartFile> chartFiles = new TreeSet<>();
        for (int i = fromYear; i <= toYear; i++) {
            try {
                NavigableSet<String> filesList = retrieveList(i);
                for (String fileName : filesList) chartFiles.add(new ChartFile(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return chartFiles;
    }

    public NavigableSet<ChartFile> filterList(ChartType type, Date dateStart, Date dateEnd) {
        ChartFile fileStart = this.findFileBy(dateStart, type);
        ChartFile fileEnd = this.findFileBy(dateEnd, type);
        return this.getAllFiles().stream().filter(file -> file.getType().equals(type)).collect(Collectors.toCollection(TreeSet::new)).subSet(fileStart, true, fileEnd, true);
    }

    private static NavigableSet<String> retrieveList(int year) throws IOException {
        String url = buildUrl(year);
        String[] content = FileUtil.readContentFromUrl(url).split("\r\n");
        return new TreeSet<>(Arrays.asList(content));
    }

    private static String buildUrl(int year) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return (year == currentYear) ? RATE_CHART_LIST_BASE_URL + "dir.txt" : RATE_CHART_LIST_BASE_URL + "dir" + year + ".txt";
    }
}
