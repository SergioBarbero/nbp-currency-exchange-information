package pl.parser.nbp.ChartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.parser.nbp.Util.FileUtil;

import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public final class ChartFileService {

    private final static int LIMIT_YEAR = 2002;
    private final static String RATE_CHART_LIST_BASE_URL = "http://www.nbp.pl/kursy/xml/";

    public ChartFileService() {}

    /**
     * Gets the closer fileName given the date parameter, this means, if exact file was not provided in specified date, the previous to it
     * @param date specified date
     * @param type letter of file
     * @return name of the file
     */
    public ChartFile findFileBy(Date date, ChartType type) {
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        int year = gregorianCalendar.get(Calendar.YEAR);
        Assert.isTrue(year >= LIMIT_YEAR, "Dates must be equal or after of " + LIMIT_YEAR);

        DateFormat df = new SimpleDateFormat("yyMMdd");
        NavigableSet<ChartFile> allFiles = this.getAllFiles(date, date);
        TreeSet<Date> dates = allFiles.stream()
                .filter(e -> e.getType().equals(type))
                .map(ChartFile::getPublicationDate)
                .collect(Collectors.toCollection(TreeSet::new));
        String closest = df.format(dates.floor(date));
        String regex = String.format("^%s.*%s$", type, closest);
        return allFiles.stream().filter(e -> e.getFileName().matches(regex)).findFirst().get();
    }

    /**
     * Retrieves a NavigableSet of ChartFile given a type and two boundary dates
     * @param type
     * @param from
     * @param to
     * @return All ChartFiles found
     */
    public NavigableSet<ChartFile> findFilesBy(ChartType type, Date from, Date to) {
        Calendar gregorianCalendarStart = new GregorianCalendar();
        gregorianCalendarStart.setTime(from);
        int fromYear = gregorianCalendarStart.get(Calendar.YEAR);

        Calendar gregorianCalendarEnd = new GregorianCalendar();
        gregorianCalendarEnd.setTime(to);
        int toYear = gregorianCalendarEnd.get(Calendar.YEAR);

        Assert.isTrue(fromYear >= LIMIT_YEAR && toYear >= LIMIT_YEAR, "Dates must be equal or after of " + LIMIT_YEAR);
        Assert.isTrue(from.before(to) || from.equals(to), "First date introduced must be before or equals the second");

        // TODO This implementation is highly inefficient because of fetching all files 3 times
        ChartFile fileStart = this.findFileBy(from, type);
        ChartFile fileEnd = this.findFileBy(to, type);
        return this.getAllFiles(from, to).stream()
                .filter(file -> file.getType().equals(type))
                .collect(Collectors.toCollection(TreeSet::new))
                .subSet(fileStart, true, fileEnd, true);
    }

    /**
     * Retrieves all files into NavigableSet
     * @return All ChartFiles found
     */
    public NavigableSet<ChartFile> getAllFiles(Date from, Date to) {
        Calendar gregorianCalendarStart = new GregorianCalendar();
        gregorianCalendarStart.setTime(from);
        int fromYear = gregorianCalendarStart.get(Calendar.YEAR);

        Calendar gregorianCalendarEnd = new GregorianCalendar();
        gregorianCalendarEnd.setTime(to);
        int toYear = gregorianCalendarEnd.get(Calendar.YEAR);

        Assert.isTrue(fromYear >= LIMIT_YEAR && toYear >= LIMIT_YEAR, "Dates must be equal or after of " + LIMIT_YEAR);
        Assert.isTrue(from.before(to) || from.equals(to), "First date introduced must be before or equals the second");

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

    private static NavigableSet<String> retrieveList(int year) throws IOException {
        String url = getUrl(year);
        String[] content = FileUtil.readContentFromUrl(url).split("\r\n");
        return new TreeSet<>(Arrays.asList(content));
    }

    private static String getUrl(int year) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return (year == currentYear) ? RATE_CHART_LIST_BASE_URL + "dir.txt" : RATE_CHART_LIST_BASE_URL + "dir" + year + ".txt";
    }
}
