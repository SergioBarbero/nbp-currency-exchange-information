package pl.parser.nbp;

import pl.parser.nbp.ChartFile.ChartFile;
import pl.parser.nbp.ChartFile.ChartFileBucket;
import pl.parser.nbp.Util.MathStatistics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainClass {


    private static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
    private static String[] allowedCurrencyCodes = { "USD", "EUR", "CHF", "GBP" };


    public static void main(String [] args) throws ParseException {
        if (args.length < 3) {
            throw new IllegalArgumentException("Please, pass all needed arguments");
        }

        if (args.length > 3) {
            throw new IllegalArgumentException("Too many arguments");
        }

        String currencyCode = args[0];
        Date startingDate = formatter.parse(args[1]);
        Date endingDate = formatter.parse(args[2]);

        if (!Arrays.asList(allowedCurrencyCodes).contains(currencyCode)) {
            throw new IllegalArgumentException("Currency not supported");
        }

        int startYear = Integer.parseInt(yearFormatter.format(startingDate));
        int endYear = Integer.parseInt(yearFormatter.format(endingDate));

        ChartFileBucket masterList = new ChartFileBucket(startYear, endYear);
        ChartFile startFileName = masterList.findFile(startingDate, 'c');
        ChartFile endFileName = masterList.findFile(endingDate, 'c');

        NavigableSet<ChartFile> searchedList = masterList.getFiles().subSet(startFileName, true, endFileName, true);

        searchedList.forEach(ChartFile::load);
        double[] sellingRates = searchedList.stream().mapToDouble(e -> e.getChart().getRateByCurrency(currencyCode).getSellingRate()).toArray();
        double[] buyingRates = searchedList.stream().mapToDouble(e -> e.getChart().getRateByCurrency(currencyCode).getBuyingRate()).toArray();
        double meanResult = MathStatistics.avg(buyingRates);
        double deviationResult = MathStatistics.stdDeviation(sellingRates);

        System.out.println(String.format("%.4f", meanResult));
        System.out.println(String.format("%.4f", deviationResult));
    }
}
