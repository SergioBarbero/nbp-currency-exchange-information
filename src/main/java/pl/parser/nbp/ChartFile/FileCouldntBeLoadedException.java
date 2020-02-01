package pl.parser.nbp.ChartFile;

public class FileCouldntBeLoadedException extends RuntimeException {
    public FileCouldntBeLoadedException(String message) {
        super(message);
    }

    public FileCouldntBeLoadedException(String message, Throwable cause) {
        super(message, cause);
    }
}
