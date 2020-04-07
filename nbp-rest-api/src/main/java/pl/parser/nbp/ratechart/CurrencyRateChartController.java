package pl.parser.nbp.ratechart;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.parser.nbp.chartfile.ChartFile;
import pl.parser.nbp.chartfile.ChartType;
import pl.parser.nbp.chartfile.ChartFileService;

import java.util.Date;
import java.util.List;

@RestController
public class CurrencyRateChartController {
    private final CurrencyRateChartService currencyRateChartService;

    public CurrencyRateChartController(CurrencyRateChartService currencyRateChartService) {
        this.currencyRateChartService = currencyRateChartService;
    }

    @GetMapping("/currency-chart/{date}/{type}")
    @ResponseBody
    public ResponseEntity<CurrencyRateChart> getCurrencyRateChart(
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @PathVariable("type") char type) {
        CurrencyRateChart currencyRateChart = currencyRateChartService.getCurrencyRateChart(date, ChartType.valueOf(String.valueOf(type)));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(currencyRateChart);
    }

    @GetMapping("/currency-chart/{start-date}/{end-date}/{type}")
    @ResponseBody
    public ResponseEntity<List<CurrencyRateChartC>> getCurrencyRateCharts(
            @PathVariable("start-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @PathVariable("end-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @PathVariable("type") char type) {
        List<CurrencyRateChartC> currencyRateCharts = currencyRateChartService.getCurrencyRateCharts(startDate, endDate, ChartType.valueOf(String.valueOf(type)));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(currencyRateCharts);
    }
}
