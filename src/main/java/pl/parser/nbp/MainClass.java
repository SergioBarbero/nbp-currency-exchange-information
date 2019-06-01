package pl.parser.nbp;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainClass {


    private static DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
    private static SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");

    public static void main(String [] args) throws ParseException {
        DescriptiveStatistics mean = new DescriptiveStatistics();
        DescriptiveStatistics deviation = new DescriptiveStatistics();

        String currencyCode = args[0];
        Date startingDate = formatter.parse(args[1]);
        Date endingDate = formatter.parse(args[2]);

        int startYear = Integer.parseInt(yearFormatter.format(startingDate));
        int endYear = Integer.parseInt(yearFormatter.format(endingDate));

        RateChartList masterList = new RateChartList(startYear, endYear);
        String startFileName = masterList.getFileName(startingDate, 'c');
        String endFileName = masterList.getFileName(endingDate, 'c');

        SortedSet<String> searchedList = masterList.getFilesNames().subSet(startFileName, endFileName);

        for (String filename: searchedList) {
            RateChart chart = ChartFileParser.readXmlChartFile(filename);
            Currency currencyRate = chart.getRateByCurrency(currencyCode);
            float buyingRate = currencyRate.getBuyingRate();
            float sellingRate = currencyRate.getSellingRate();
            mean.addValue(buyingRate);
            deviation.addValue(sellingRate);
        }
        double deviationResult = deviation.getStandardDeviation();
        double meanResult = deviation.getMean();

        System.out.println(meanResult);
        System.out.println(deviationResult);
    }
}
