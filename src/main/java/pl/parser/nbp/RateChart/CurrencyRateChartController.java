package pl.parser.nbp.RateChart;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.parser.nbp.ChartFile.ChartFile;
import pl.parser.nbp.ChartFile.ChartFileBucket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

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

    @GetMapping("/currency-charts/{start-date}/{end-date}/{type}")
    public List<CurrencyRateChart> getCurrencyRateCharts(@PathVariable("start-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @PathVariable("end-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @PathVariable("type") char type) {
        int startYear = Integer.parseInt(publicationDateFormat.format(startDate));
        int endYear = Integer.parseInt(publicationDateFormat.format(endDate));
        ChartFileBucket bucket = new ChartFileBucket(startYear, endYear);
        ChartFile fileStart = bucket.findFile(startDate, type);
        ChartFile fileEnd = bucket.findFile(endDate, type);
        SortedSet<ChartFile> files = bucket.getFiles().subSet(fileStart, fileEnd);
        files.forEach(ChartFile::load);
        return files.stream().filter(file -> file.getType() == 'c').map(ChartFile::getChart).collect(Collectors.toList());
    }
}
