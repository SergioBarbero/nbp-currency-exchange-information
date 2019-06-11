package pl.parser.nbp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

import java.text.ParseException;

@SpringBootApplication
public class Application {

    public static void main(String [] args) throws ParseException {
        SpringApplication.run(Application.class, args);
    }
}
