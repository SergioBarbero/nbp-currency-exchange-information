package pl.parser.nbp.chartfile;

import java.util.NavigableSet;

public interface DirectoryService {
    NavigableSet<ChartFile> findChartFiles(int year);
}
