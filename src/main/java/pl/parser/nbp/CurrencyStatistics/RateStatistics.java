package pl.parser.nbp.CurrencyStatistics;

import pl.parser.nbp.Util.MathStatisticsUtil;

public class RateStatistics {

    private double mean;

    private double standardDeviation;

    public RateStatistics(double[] rates) {
        this.mean = MathStatisticsUtil.avg(rates);
        this.standardDeviation = MathStatisticsUtil.stdDeviation(rates);
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }
}
