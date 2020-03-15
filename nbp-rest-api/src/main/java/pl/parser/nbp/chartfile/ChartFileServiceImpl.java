package pl.parser.nbp.chartfile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.parser.nbp.util.DateUtil.*;

@Service
public final class ChartFileServiceImpl implements ChartFileService {

    private final static DateFormat PUBLICATION_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final static Date LIMIT_DATE = new GregorianCalendar(2001, Calendar.DECEMBER, 31).getTime();

    private final DirectoryService directoryService;

    public ChartFileServiceImpl(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @Override
    public Optional<ChartFile> findFileBy(Date date, ChartType type) {
        Assert.isTrue(date.after(LIMIT_DATE), "Date must be in or after " + (getYearFromDate(LIMIT_DATE) + 1));
        int year = getYearFromDate(date);

        return directoryService.findChartFiles(year).stream()
                .filter(e -> e.getType().equals(type) && e.getPublicationDate().equals(date))
                .findAny();
    }

    @Override
    public Set<ChartFile> findFilesBy(Date from, Date to, ChartType type) {
        Assert.isTrue(from.before(to) || from.equals(to), "First date introduced must be before or equals the second");
        Assert.isTrue(from.after(LIMIT_DATE), "Dates must be in or after " + (getYearFromDate(LIMIT_DATE) + 1));

        int fromYear = getYearFromDate(from);
        int toYear = getYearFromDate(to);

        return IntStream.rangeClosed(fromYear, toYear)
                .mapToObj(directoryService::findChartFiles)
                .flatMap(Collection::stream)
                .filter(file -> file.getType().equals(type))
                .filter(file -> file.getPublicationDate().after(from) && file.getPublicationDate().before(to))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public Set<ChartFile> findFilesBy(Date from, Date to) {
        Assert.isTrue(from.before(to) || from.equals(to), "First date introduced must be before or equals the second");
        Assert.isTrue(from.after(LIMIT_DATE), "Dates must be in or after " + (getYearFromDate(LIMIT_DATE) + 1));

        int fromYear = getYearFromDate(from);
        int toYear = getYearFromDate(to);

        return IntStream.rangeClosed(fromYear, toYear)
                .mapToObj(directoryService::findChartFiles)
                .flatMap(Collection::stream)
                .filter(file -> file.getPublicationDate().after(from) && file.getPublicationDate().before(to))
                .collect(Collectors.toCollection(TreeSet::new));
    }

}
