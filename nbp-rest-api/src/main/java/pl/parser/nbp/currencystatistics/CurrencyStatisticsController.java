package pl.parser.nbp.currencystatistics;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.parser.nbp.rate.CurrencyCode;

import java.util.Date;

@RestController
public class CurrencyStatisticsController {

    private final CurrencyStatisticsService currencyStatisticsService;

    public CurrencyStatisticsController(CurrencyStatisticsService currencyStatisticsService) {
        this.currencyStatisticsService = currencyStatisticsService;
    }

    @GetMapping("/statistics/{start-date}/{end-date}/{currency}")
    @ResponseBody
    public ResponseEntity<CurrencyStatistics> getStatisticsForRangeAndType(
            @PathVariable("start-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @PathVariable("end-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @PathVariable("currency") String currencyCode) {

        // TODO Validation of currency code
        CurrencyCode code = CurrencyCode.valueOf(currencyCode);
        CurrencyStatistics statisticsInRangeAndType = currencyStatisticsService.getStatisticsInRangeAndType(startDate, endDate, code);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(statisticsInRangeAndType);
    }
}
