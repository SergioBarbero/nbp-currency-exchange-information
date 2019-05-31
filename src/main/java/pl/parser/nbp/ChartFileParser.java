package pl.parser.nbp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.net.MalformedURLException;

public class ChartFileParser {

    private final static String baseUrl = "http://www.nbp.pl/kursy/xml/";
    private static ObjectMapper xmlMapper = new XmlMapper();

    public static RateChart readXmlChartFile(String filename) throws MalformedURLException {
        String url = baseUrl + filename + ".xml";
        RateChart rateChart;
        try {
            String content = Utils.readFromUrl(url);
            rateChart = xmlMapper.readValue(content, RateChart.class);
            return rateChart;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
