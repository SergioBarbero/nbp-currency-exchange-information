package pl.parser.nbp.Util;

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class MathStatistics {

    public static double stdDeviation(double[] population) {
        double mean = MathStatistics.avg(population);
        double sumPowSquared = Arrays.stream(population).map(v -> Math.pow(v - mean, 2)).sum();
        return Math.sqrt(sumPowSquared / (population.length - 1));
    }

    public static double avg(double[] population) {
        double sum = DoubleStream.of(population).sum();
        return sum / population.length;
    }
}
