package pl.parser.nbp.CurrencyStatistics;

import pl.parser.nbp.Util.MathStatisticsUtil;

public final class RateStatistics {

    private final double mean;
    private final double standardDeviation;

    public RateStatistics(double[] rates) {
        this.mean = MathStatisticsUtil.avg(rates);
        this.standardDeviation = MathStatisticsUtil.stdDeviation(rates);
    }

    public double getMean() {
        return mean;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }
}
