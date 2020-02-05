package pl.parser.nbp.ChartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.parser.nbp.Util.DateUtil;
import pl.parser.nbp.Util.FileUtil;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.parser.nbp.Util.DateUtil.getYearFromDate;

@Service
public final class ChartFileRemoteService implements ChartFileService {

    private final static int LIMIT_YEAR = 2002;

    private final static Date LIMIT_DATE = new GregorianCalendar(2001, Calendar.DECEMBER, 31).getTime();

    /**
     * Gets the closest fileName given the date parameter, this means, if exact file was not provided in specified date, the previous to it
     * @param date specified date
     * @param type letter of file
     * @return name of the file
     */
    @Override
    public ChartFile findFileBy(Date date, ChartType type) {
        Assert.isTrue(date.after(LIMIT_DATE), "Dates must be in or after " + getYearFromDate(LIMIT_DATE) + 1);
        int year = getYearFromDate(date);

        TreeSet<Date> dates = new ChartFileDirectory().findChartFiles(year).stream()
                .filter(e -> e.getType().equals(type))
                .map(ChartFile::getPublicationDate)
                .collect(Collectors.toCollection(TreeSet::new));
        String closest = new SimpleDateFormat("yyMMdd").format(dates.floor(date));
        String regex = String.format("^%s.*%s$", type, closest);
        return new ChartFileDirectory().findChartFiles(year).stream().filter(e -> e.getFileName().matches(regex)).findFirst().get();
    }

    /**
     * Retrieves a NavigableSet of ChartFile given a type and two boundary dates
     * @param from
     * @param to
     * @param type
     * @return All ChartFiles found
     */
    @Override
    public NavigableSet<ChartFile> findFilesBy(Date from, Date to, ChartType type) {
        Assert.isTrue(from.before(to) || from.equals(to), "First date introduced must be before or equals the second");
        Assert.isTrue(from.after(LIMIT_DATE), "Dates must be in or after " + getYearFromDate(LIMIT_DATE) + 1);

        int fromYear = getYearFromDate(from);
        int toYear = getYearFromDate(to);

        ChartFileDirectory directory = new ChartFileDirectory();

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
        Assert.isTrue(from.before(to) || from.equals(to), "First date introduced must be before or equals the second");
        Assert.isTrue(from.after(LIMIT_DATE), "Dates must be in or after " + getYearFromDate(LIMIT_DATE) + 1);

        int fromYear = getYearFromDate(from);
        int toYear = getYearFromDate(to);

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
