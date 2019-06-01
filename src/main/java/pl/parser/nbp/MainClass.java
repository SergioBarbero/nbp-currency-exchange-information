package pl.parser.nbp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainClass {


    private static DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
    private static SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");

    public static void main(String [] args) throws ParseException {
        String currencyCode = args[0];
        Date startingDate = formatter.parse(args[1]);
        Date endingDate = formatter.parse(args[2]);

        int startYear = Integer.parseInt(yearFormatter.format(startingDate));
        int endYear = Integer.parseInt(yearFormatter.format(endingDate));

        RateChartList masterList = new RateChartList(startYear, endYear);
        ChartFile startFileName = masterList.findFile(startingDate, 'c');
        ChartFile endFileName = masterList.findFile(endingDate, 'c');

        NavigableSet<ChartFile> searchedList = masterList.getFilesNames().subSet(startFileName, true, endFileName, true);

        for (ChartFile file: searchedList) {
            file.load();
        }
        double[] sellingRates = searchedList.stream().mapToDouble(e -> e.getChart().getRateByCurrency(currencyCode).getSellingRate()).toArray();
        double[] buyingRates = searchedList.stream().mapToDouble(e -> e.getChart().getRateByCurrency(currencyCode).getBuyingRate()).toArray();
        double meanResult = MathStatistics.avg(buyingRates);
        double deviationResult = MathStatistics.stdDeviation(sellingRates);

        System.out.println(meanResult);
        System.out.println(deviationResult);
    }
}
