package pl.parser.nbp.RateChart;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.parser.nbp.ChartFile.ChartFile;
import pl.parser.nbp.ChartFile.ChartFileBucket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class CurrencyRateChartController {
    private static DateFormat publicationDateFormat = new SimpleDateFormat("yyyy");

    @GetMapping("/currency-chart/{date}/{type}")
    public CurrencyRateChart getCurrencyRateChart(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @PathVariable("type") char type) {
        int year = Integer.parseInt(publicationDateFormat.format(date));
        ChartFileBucket bucket = new ChartFileBucket(year, year);
        ChartFile file = bucket.findFile(date, type);
        file.load();
        CurrencyRateChart chart = file.getChart();
        return chart;
    }
}
