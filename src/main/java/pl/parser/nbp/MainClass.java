package pl.parser.nbp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MainClass {

    private final static String baseUrl = "http://www.nbp.pl/kursy/xml/";

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd", Locale.ENGLISH);

    public static void main(String [] args) throws MalformedURLException {
        //String currencyCode = args[0];
        //LocalDate startingDate = LocalDate.parse(args[1], formatter);
        //LocalDate endingDate = LocalDate.parse(args[2], formatter);

        //int startYear = startingDate.getYear();
        int startYear = 2002;
        //int endYear = endingDate.getYear();
        int endYear = 2002;
        RateChartList startList = null;
        RateChartList endList = null;

        try {
            startList = new RateChartList(startYear);
            endList = new RateChartList(endYear);
        } catch (IOException e) {
            e.printStackTrace();
        }

        startList = startList.byType('c').sublist("c003z020104", "c007z020110");


        ObjectMapper xmlMapper = new XmlMapper();
        RateChartList list = null;
        try {
            list = new RateChartList(startYear);
        } catch (IOException e) {
            e.printStackTrace();
        }

        URL u = new URL(baseUrl + "c073z070413.xml");
        try (InputStream in = u.openStream()) {
            String rateChartcontent = new String(in.readAllBytes());
            RateChart rateChart = xmlMapper.readValue(rateChartcontent, RateChart.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
