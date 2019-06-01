package pl.parser.nbp.ChartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import pl.parser.nbp.RateChart.RateChart;
import pl.parser.nbp.Util.Utils;

import java.io.IOException;

public class ChartFile implements Comparable<ChartFile> {

    private final static String baseUrl = "http://www.nbp.pl/kursy/xml/";
    private static ObjectMapper xmlMapper = new XmlMapper();

    private String fileName;

    private RateChart chart;

    public ChartFile(String fileName) {
        this.fileName = fileName;
    }

    public void load() {
        try {
            String content = Utils.readFromUrl(this.getUrl());
            this.chart = xmlMapper.readValue(content, RateChart.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return baseUrl + this.fileName + ".xml";
    }

    public RateChart getChart() {
        return chart;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public int compareTo(ChartFile o) {
        return fileName.compareTo(o.fileName);
    }
}