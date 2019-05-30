package pl.parser.nbp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainClass {

    private final static String baseUrl = "http://www.nbp.pl/kursy/xml/";

    public static void main(String [] args) throws MalformedURLException {
        ObjectMapper xmlMapper = new XmlMapper();

        URL u = new URL(baseUrl + "c073z070413.xml" );
        try (InputStream in = u.openStream()) {
            String content = new String(in.readAllBytes());
            System.out.println(content);
            RateChart rateChart = xmlMapper.readValue(content, RateChart.class);
            System.out.println("good");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
