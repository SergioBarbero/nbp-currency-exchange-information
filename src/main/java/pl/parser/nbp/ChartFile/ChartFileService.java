package pl.parser.nbp.ChartFile;

import java.util.Date;
import java.util.NavigableSet;

public interface ChartFileService {
    /**
     * Finds file by Date and type
     * @param date To find file
     * @param type ChartType to find
     * @return ChartFile found, null if was not found
     */
    ChartFile findFileBy(Date date, ChartType type);

    /**
     * Find files from date to date
     * @param from Date low threshold
     * @param to Date high threshold
     * @return NavigableSet<ChartFile> returned
     */
    NavigableSet<ChartFile> findFilesBy(Date from, Date to);

    /**
     * Find files from date to date and a type
     * @param from Date low threshold
     * @param to Date high threshold
     * @param type ChartType given
     * @return NavigableSet<ChartFile> returned
     */
    NavigableSet<ChartFile> findFilesBy(Date from, Date to, ChartType type);
}
