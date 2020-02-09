package pl.parser.nbp.ChartFile;

import org.springframework.stereotype.Service;
import pl.parser.nbp.Util.FileUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class FilesDirectory {

    private final static String URL = "http://www.nbp.pl/kursy/xml/";

    public NavigableSet<ChartFile> findChartFiles(int year) {
        try {
            return Arrays.stream(FileUtil.readContentFromUrl(getUrl(year))
                    .split("\r\n"))
                    .map(ChartFile::new).collect(Collectors.toCollection(TreeSet::new));
        } catch (IOException e) {
            throw new FileNotLoadedException("File couldn't be retrieved", e);
        }
    }

    private static String getUrl(int year) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return (year == currentYear) ? URL + "dir.txt" : URL + "dir" + year + ".txt";
    }
}
