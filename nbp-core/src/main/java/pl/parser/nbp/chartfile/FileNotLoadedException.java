package pl.parser.nbp.chartfile;

public class FileNotLoadedException extends RuntimeException {
    public FileNotLoadedException(String message) {
        super(message);
    }

    public FileNotLoadedException(String message, Throwable cause) {
        super(message, cause);
    }
}
