package pl.parser.nbp.Util;

import java.util.stream.DoubleStream;

public class MathStatistics {

    public static double stdDeviation(double[] population) {
        double mean = MathStatistics.avg(population);
        double sumPowSquared = 0;
        for (double v : population) {
            sumPowSquared += Math.pow(v - mean, 2);
        }
        return Math.sqrt(sumPowSquared / (population.length - 1));
    }

    public static double avg(double[] population) {
        double sum = DoubleStream.of(population).sum();
        return sum / population.length;
    }
}
