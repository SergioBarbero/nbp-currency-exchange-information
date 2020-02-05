package pl.parser.nbp.ChartFile;

import java.util.Date;
import java.util.NavigableSet;

public interface ChartFileService {
    ChartFile findFileBy(Date date, ChartType type);

    NavigableSet<ChartFile> findFilesBy(Date from, Date to, ChartType type);

    NavigableSet<ChartFile> findFilesBy(Date from, Date to);
}
