package pl.parser.nbp.chartfile;

import java.util.Set;

public interface DirectoryService {
    Set<ChartFile> findChartFiles(int year);
}
