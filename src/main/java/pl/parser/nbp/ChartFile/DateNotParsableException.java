package pl.parser.nbp.ChartFile;

public class DateNotParsableException extends RuntimeException {
    public DateNotParsableException(String message) {
        super(message);
    }

    public DateNotParsableException(String message, Throwable cause) {
        super(message, cause);
    }
}
