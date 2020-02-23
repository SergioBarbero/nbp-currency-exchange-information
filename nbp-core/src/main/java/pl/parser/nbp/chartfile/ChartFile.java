package pl.parser.nbp.chartfile;

import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import pl.parser.nbp.ratechart.CurrencyRateChart;
import pl.parser.nbp.ratechart.CurrencyRateChartC;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ChartFile implements Comparable<ChartFile> {

    private final static String BASE_URL = "http://www.nbp.pl/kursy/xml/";
    private final static DateFormat PUBLICATION_DATE_FORMAT = new SimpleDateFormat("yyMMdd");
    private final static char COMMON_LETTER = 'z';

    private final String tableReference;
    private final Date publicationDate;
    private final ChartType type;

    public ChartFile(String fileName) {
        String formattedDate = fileName.substring(fileName.length() - 6);
        Date publicationDate;
        try {
            publicationDate = PUBLICATION_DATE_FORMAT.parse(formattedDate);
        } catch (ParseException e) {
            throw new RuntimeException("Date was not parsable");
        }
        this.publicationDate = publicationDate;
        this.type = ChartType.valueOf(fileName.substring(0, 1));
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
        Assert.isTrue(this.type.equals(ChartType.c), "This kind of chart is not allowed");
        CurrencyRateChartC currencyRateChartC;
        try {
            currencyRateChartC = new RestTemplate().getForObject(new URI(this.getUrl()), CurrencyRateChartC.class);
        } catch (URISyntaxException e) {
            throw new ChartNotLoadedException("Chart file " + this.getFileName() + " couldn't be loaded");
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