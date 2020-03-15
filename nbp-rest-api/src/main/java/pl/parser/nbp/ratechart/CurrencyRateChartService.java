package pl.parser.nbp.ratechart;

import org.springframework.stereotype.Service;
import pl.parser.nbp.chartfile.ChartFile;
import pl.parser.nbp.chartfile.ChartFileService;
import pl.parser.nbp.chartfile.ChartType;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyRateChartService {

    private final ChartFileService chartFileService;

    public CurrencyRateChartService(ChartFileService chartFileService) {
        this.chartFileService = chartFileService;
    }

    public CurrencyRateChart getCurrencyRateChart(Date date, ChartType type) {
        return chartFileService.findFileBy(date, type).retrieveCurrencyRateChart();
    }

    public List<CurrencyRateChart> getCurrencyRateCharts(Date startDate, Date endDate, ChartType type) {
        return chartFileService.findFilesBy(startDate, endDate, type).stream()
                .filter(file -> ChartType.c.equals(file.getType()))
                .map(ChartFile::retrieveCurrencyRateChart)
                .collect(Collectors.toList());
    }


}
