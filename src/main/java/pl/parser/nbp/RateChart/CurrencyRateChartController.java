package pl.parser.nbp.RateChart;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.parser.nbp.ChartFile.ChartFile;
import pl.parser.nbp.ChartFile.ChartType;
import pl.parser.nbp.ChartFile.ChartFileService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CurrencyRateChartController {
    private final static DateFormat PUBLICATION_DATE_FORMAT = new SimpleDateFormat("yyyy");

    private final ChartFileService chartFileService;

    public CurrencyRateChartController(ChartFileService ChartFileService) {
        this.chartFileService = ChartFileService;
    }

    @GetMapping("/currency-chart/{date}/{type}")
    public CurrencyRateChart getCurrencyRateChart(
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @PathVariable("type") char type) {
        return chartFileService.findFileBy(date, ChartType.valueOf(String.valueOf(type))).retrieveCurrencyRateChart();
    }

    @GetMapping("/currency-chart/{start-date}/{end-date}/{type}")
    public List<CurrencyRateChart> getCurrencyRateCharts(
            @PathVariable("start-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @PathVariable("end-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @PathVariable("type") char type) {
        return chartFileService
                .findFilesBy(startDate, endDate, ChartType.valueOf(String.valueOf(type))).stream()
                .filter(file -> ChartType.c.equals(file.getType()))
                .map(ChartFile::retrieveCurrencyRateChart)
                .collect(Collectors.toList());
    }
}
