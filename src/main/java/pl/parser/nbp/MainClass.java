package pl.parser.nbp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainClass {


    private static DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

    private static Calendar calendar = new GregorianCalendar();

    public static void main(String [] args) throws MalformedURLException, ParseException {
        DescriptiveStatistics mean = new DescriptiveStatistics();
        DescriptiveStatistics deviation = new DescriptiveStatistics();

        String currencyCode = args[0];
        Date startingDate = formatter.parse(args[1]);
        Date endingDate = formatter.parse(args[2]);

        int startYear = startingDate.getYear();
        int endYear = endingDate.getYear();

        SortedSet<String> filesNames = new TreeSet<>();

        RateChartList masterList = new RateChartList(startYear, endYear);
        String startFileName = masterList.getFileName(startingDate, 'c');
        String endFileName = masterList.getFileName(endingDate, 'c');

        SortedSet<String> searchedList = filesNames.subSet(startFileName, endFileName);

        for (String filename: searchedList) {
            try {
                RateChart chart = ChartFileParser.readXmlChartFile(filename);
                Currency currencyRate = chart.getRateByCurrency(currencyCode);
                float buyingRate = currencyRate.getBuyingRate();
                float sellingRate = currencyRate.getSellingRate();
                mean.addValue(buyingRate);
                deviation.addValue(sellingRate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        StandardDeviation sd = new StandardDeviation();

        double deviationResult = deviation.getStandardDeviation();
        double meanRestult = deviation.getMean();

        System.out.println(deviationResult);
        System.out.println(meanRestult);
    }
}
