package pl.parser.nbp.ChartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.parser.nbp.Util.FileUtil;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public final class ChartFileRemoteService implements ChartFileService {

    private final static int LIMIT_YEAR = 2002;

    /**
     * Gets the closest fileName given the date parameter, this means, if exact file was not provided in specified date, the previous to it
     * @param date specified date
     * @param type letter of file
     * @return name of the file
     */
    @Override
    public ChartFile findFileBy(Date date, ChartType type) {
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        int year = gregorianCalendar.get(Calendar.YEAR);
        Assert.isTrue(year >= LIMIT_YEAR, "Dates must be equal or after of " + LIMIT_YEAR);

        DateFormat df = new SimpleDateFormat("yyMMdd");
        NavigableSet<ChartFile> allFiles = this.findFilesBy(date, date);
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
    @Override
    public NavigableSet<ChartFile> findFilesBy(ChartType type, Date from, Date to) {
        ChartFileDirectory directory = new ChartFileDirectory();

        Calendar gregorianCalendarStart = new GregorianCalendar();
        gregorianCalendarStart.setTime(from);
        int fromYear = gregorianCalendarStart.get(Calendar.YEAR);

        Calendar gregorianCalendarEnd = new GregorianCalendar();
        gregorianCalendarEnd.setTime(to);
        int toYear = gregorianCalendarEnd.get(Calendar.YEAR);

        Assert.isTrue(fromYear >= LIMIT_YEAR && toYear >= LIMIT_YEAR, "Dates must be equal or after of " + LIMIT_YEAR);
        Assert.isTrue(from.before(to) || from.equals(to), "First date introduced must be before or equals the second");

        return IntStream.rangeClosed(fromYear, toYear)
                .mapToObj(directory::findChartFiles)
                .flatMap(Collection::stream)
                .filter(file -> file.getType().equals(type))
                .filter(file -> file.getPublicationDate().before(to) && file.getPublicationDate().after(from))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Retrieves all files into NavigableSet
     * @return All ChartFiles found
     */
    @Override
    public NavigableSet<ChartFile> findFilesBy(Date from, Date to) {
        Calendar gregorianCalendarStart = new GregorianCalendar();
        gregorianCalendarStart.setTime(from);
        int fromYear = gregorianCalendarStart.get(Calendar.YEAR);

        Calendar gregorianCalendarEnd = new GregorianCalendar();
        gregorianCalendarEnd.setTime(to);
        int toYear = gregorianCalendarEnd.get(Calendar.YEAR);

        Assert.isTrue(fromYear >= LIMIT_YEAR && toYear >= LIMIT_YEAR, "Dates must be equal or after of " + LIMIT_YEAR);
        Assert.isTrue(from.before(to) || from.equals(to), "First date introduced must be before or equals the second");

        return IntStream.rangeClosed(fromYear, toYear)
                .mapToObj(year -> new ChartFileDirectory().findChartFiles(year))
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private static class ChartFileDirectory {

        private final static String DIRECTORY = "http://www.nbp.pl/kursy/xml/";

        private NavigableSet<ChartFile> findChartFiles(int year) {
            try {
                return Arrays.stream(FileUtil.readContentFromUrl(getUrl(year)).split("\r\n"))
                        .map(ChartFile::new).collect(Collectors.toCollection(TreeSet::new));
            } catch (IOException e) {
                throw new FileNotLoadedException("File couldn't be retrieved", e);
            }
        }

        private static String getUrl(int year) {
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            return (year == currentYear) ? DIRECTORY + "dir.txt" : DIRECTORY + "dir" + year + ".txt";
        }
    }
}
