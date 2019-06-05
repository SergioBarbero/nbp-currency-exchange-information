package pl.parser.nbp.ChartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import pl.parser.nbp.RateChart.CurrencyRateChart;
import pl.parser.nbp.Util.Utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChartFile implements Comparable<ChartFile> {

    private final static String baseUrl = "http://www.nbp.pl/kursy/xml/";
    private static ObjectMapper xmlMapper = new XmlMapper();
    private static DateFormat publicationDateFormat = new SimpleDateFormat("yyMMdd");

    private static char commonLetter = 'z';
    private String tableReference;
    private Date publicationDate;
    private char type;

    private CurrencyRateChart chart;

    public ChartFile(String fileName) throws ParseException {
        String formattedDate = fileName.substring(fileName.length() - 6);
        this.publicationDate = publicationDateFormat.parse(formattedDate);
        this.type = fileName.charAt(0);
        this.tableReference = fileName.substring(1, 4);
    }

    public String getFileName() {
        return this.type + this.tableReference + commonLetter + publicationDateFormat.format(this.publicationDate);
    }

    public char getType() {
        return this.type;
    }

    public Date getPublicationDate() {
        return this.publicationDate;
    }

    public void load() {
        try {
            String content = Utils.readFromUrl(this.getUrl());
            this.chart = xmlMapper.readValue(content, CurrencyRateChart.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return baseUrl + this.getFileName() + ".xml";
    }

    public CurrencyRateChart getChart() {
        return chart;
    }

    @Override
    public int compareTo(ChartFile o) {
        int dateComparison = this.getPublicationDate().compareTo(o.getPublicationDate());
        return dateComparison == 0 ? String.valueOf(type).compareTo(String.valueOf(o.type)) : dateComparison;
    }
}