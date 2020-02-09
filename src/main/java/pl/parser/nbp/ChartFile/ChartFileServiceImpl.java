package pl.parser.nbp.ChartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.parser.nbp.Util.DateUtil.getYearFromDate;

@Service
public final class ChartFileServiceImpl implements ChartFileService {

    private final static DateFormat PUBLICATION_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final static Date LIMIT_DATE = new GregorianCalendar(2001, Calendar.DECEMBER, 31).getTime();

    private final FilesDirectory directory;

    public ChartFileServiceImpl(FilesDirectory directory) {
        this.directory = directory;
    }

    /**
     * Gets the closest fileName given the date parameter, this means, if exact file was not provided in specified date, the previous to it
     * @param date specified date
     * @param type letter of file
     * @return name of the file
     */
    @Override
    public ChartFile findFileBy(Date date, ChartType type) {
        Assert.isTrue(date.after(LIMIT_DATE), "Date must be in or after " + (getYearFromDate(LIMIT_DATE) + 1));
        int year = getYearFromDate(date);

        return directory.findChartFiles(year).stream()
                .filter(e -> e.getType().equals(type) && e.getPublicationDate().equals(date))
                .findAny()
                .orElseThrow(() -> new FileNotFoundException("Chart from " + PUBLICATION_DATE_FORMAT.format(date) + " was not found"));
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
        Assert.isTrue(from.after(LIMIT_DATE), "Dates must be in or after " + (getYearFromDate(LIMIT_DATE) + 1));

        int fromYear = getYearFromDate(from);
        int toYear = getYearFromDate(to);

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
        Assert.isTrue(from.after(LIMIT_DATE), "Dates must be in or after " + (getYearFromDate(LIMIT_DATE) + 1));

        int fromYear = getYearFromDate(from);
        int toYear = getYearFromDate(to);

        return IntStream.rangeClosed(fromYear, toYear)
                .mapToObj(directory::findChartFiles)
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(TreeSet::new));
    }

}
