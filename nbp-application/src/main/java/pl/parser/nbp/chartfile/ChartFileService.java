package pl.parser.nbp.chartfile;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface ChartFileService {
    /**
     * Finds file by Date and type
     * @param date To find file
     * @param type ChartType to find
     * @return ChartFile if found, empty optional if not
     */
    Optional<ChartFile> findFileBy(Date date, ChartType type);

    /**
     * Find files from date to date and a type
     * @param from Date low threshold
     * @param to Date high threshold
     * @param type ChartType given
     * @return Set<ChartFile> returned
     */
    Set<ChartFile> findFilesBy(Date from, Date to, ChartType type);
}
