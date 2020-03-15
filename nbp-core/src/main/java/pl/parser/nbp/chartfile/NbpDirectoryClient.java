package pl.parser.nbp.chartfile;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NbpDirectoryClient implements DirectoryService {

    private final static String URL = "http://www.nbp.pl/kursy/xml/";

    @Override
    public Set<ChartFile> findChartFiles(int year) {
        try {
            String filesText = new RestTemplate().getForObject(new URI(getUrl(year)), String.class);
            return Arrays.stream(filesText.substring(3).split("\r\n"))
                    .map(ChartFile::new).collect(Collectors.toCollection(TreeSet::new));
        } catch (URISyntaxException | HttpClientErrorException e) {
            throw new FileNotLoadedException("File couldn't be retrieved", e);
        }
    }

    private static String getUrl(int year) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return year == currentYear ? URL + "dir.txt" : URL + "dir" + year + ".txt";
    }
}
