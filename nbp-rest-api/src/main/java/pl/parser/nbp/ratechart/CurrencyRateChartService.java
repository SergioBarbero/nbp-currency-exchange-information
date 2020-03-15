package pl.parser.nbp.ratechart;

import org.springframework.stereotype.Service;
import pl.parser.nbp.chartfile.ChartFile;
import pl.parser.nbp.chartfile.ChartFileService;
import pl.parser.nbp.chartfile.ChartType;
import pl.parser.nbp.chartfile.FileNotFoundException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyRateChartService {

    private final static DateFormat PUBLICATION_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final ChartFileService chartFileService;

    public CurrencyRateChartService(ChartFileService chartFileService) {
        this.chartFileService = chartFileService;
    }

    public CurrencyRateChart getCurrencyRateChart(Date date, ChartType type) {
        return chartFileService
                .findFileBy(date, type).map(ChartFile::retrieveCurrencyRateChart)
                .orElseThrow(() -> new FileNotFoundException("Chart from " + PUBLICATION_DATE_FORMAT.format(date) + " was not found"));
    }

    public List<CurrencyRateChart> getCurrencyRateCharts(Date startDate, Date endDate, ChartType type) {
        List<CurrencyRateChart> currencyRateCharts = chartFileService.findFilesBy(startDate, endDate, type).stream()
                .map(ChartFile::retrieveCurrencyRateChart)
                .collect(Collectors.toList());
        if (currencyRateCharts.isEmpty()) {
            throw new FileNotFoundException("There was no charts from "
                    + PUBLICATION_DATE_FORMAT.format(startDate) + " to " + PUBLICATION_DATE_FORMAT.format(endDate));
        }
        return currencyRateCharts;
    }


}
