package pl.parser.nbp.chartfile;

public class ChartNotLoadedException extends RuntimeException {
    public ChartNotLoadedException(String message) {
        super(message);
    }

    public ChartNotLoadedException(String message, Throwable cause) {
        super(message, cause);
    }
}
