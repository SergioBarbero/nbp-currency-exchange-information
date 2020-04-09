package pl.parser.nbp.util;

import java.util.Arrays;
import java.util.stream.DoubleStream;

public final class MathStatisticsUtil {

    private MathStatisticsUtil() {
        throw new AssertionError();
    }

    public static double stdDeviation(double[] population) {
        double mean = MathStatisticsUtil.avg(population);
        double sumPowSquared = Arrays.stream(population).map(v -> Math.pow(v - mean, 2)).sum();
        return Math.sqrt(sumPowSquared / (population.length - 1));
    }

    public static double avg(double[] population) {
        double sum = DoubleStream.of(population).sum();
        return sum / population.length;
    }
}
