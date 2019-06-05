package pl.parser.nbp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@SpringBootApplication
public class Application {

    private static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
    private static String[] allowedCurrencyCodes = { "USD", "EUR", "CHF", "GBP" };

    public static void main(String [] args) throws ParseException {
        SpringApplication.run(Application.class, args);
    }
}
