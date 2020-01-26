package pl.parser.nbp.ChartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.util.Assert;
import pl.parser.nbp.RateChart.CurrencyRateChart;
import pl.parser.nbp.RateChart.CurrencyRateChartC;
import pl.parser.nbp.Util.FileUtil;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ChartFile implements Comparable<ChartFile> {

    private final static String BASE_URL = "http://www.nbp.pl/kursy/xml/";
    private final static ObjectMapper XML_MAPPER = new XmlMapper();
    private final static DateFormat PUBLICATION_DATE_FORMAT = new SimpleDateFormat("yyMMdd");
    private final static char COMMON_LETTER = 'z';

    private final String tableReference;
    private final Date publicationDate;
    private final ChartType type;

    public ChartFile(String fileName) throws ParseException {
        String formattedDate = fileName.substring(fileName.length() - 6);
        this.publicationDate = PUBLICATION_DATE_FORMAT.parse(formattedDate);
        this.type = ChartType.valueOf(String.valueOf(fileName.charAt(0)));
        this.tableReference = fileName.substring(1, 4);
    }

    public String getFileName() {
        return this.type + this.tableReference + COMMON_LETTER + PUBLICATION_DATE_FORMAT.format(this.publicationDate);
    }

    public ChartType getType() {
        return this.type;
    }

    public Date getPublicationDate() {
        return this.publicationDate;
    }

    public CurrencyRateChart retrieveCurrencyRateChart() {
        Assert.state(this.type.equals(ChartType.c), "This kind of chart is not allowed");
        String url = this.getUrl();
        CurrencyRateChartC currencyRateChartC;
        try {
            String urlContent = FileUtil.readContentFromUrl(url);
            currencyRateChartC = XML_MAPPER.readValue(urlContent, CurrencyRateChartC.class);
        } catch (IOException e) {
            throw new ChartNotLoadedException("Chart file " + url + " couldn't be loaded");
        }
        return currencyRateChartC;
    }

    public String getUrl() {
        return BASE_URL + this.getFileName() + ".xml";
    }

    @Override
    public int compareTo(ChartFile o) {
        int dateComparison = this.getPublicationDate().compareTo(o.getPublicationDate());
        return dateComparison == 0 ? String.valueOf(type).compareTo(String.valueOf(o.type)) : dateComparison;
    }
}