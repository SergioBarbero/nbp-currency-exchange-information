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

    public CurrencyRateChart getCurrencyRateChart(Date date) {
        return chartFileService
                .findFileBy(date, ChartType.c).map(ChartFile::retrieveCurrencyRateChart)
                .orElseThrow(() -> new FileNotFoundException("Chart from " + PUBLICATION_DATE_FORMAT.format(date) + " was not found"));
    }

    public List<CurrencyRateChartC> getCurrencyRateCharts(Date startDate, Date endDate) {
        List<CurrencyRateChartC> currencyRateCharts = chartFileService.findFilesBy(startDate, endDate, ChartType.c).stream()
                .map(ChartFile::retrieveCurrencyRateChart)
                .map(CurrencyRateChartC.class::cast)
                .collect(Collectors.toList());
        if (currencyRateCharts.isEmpty()) {
            throw new FileNotFoundException("There was no charts from "
                    + PUBLICATION_DATE_FORMAT.format(startDate) + " to " + PUBLICATION_DATE_FORMAT.format(endDate));
        }
        return currencyRateCharts;
    }


}
