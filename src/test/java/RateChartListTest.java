import org.junit.jupiter.api.Test;
import pl.parser.nbp.RateChart;
import pl.parser.nbp.RateChartList;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RateChartListTest {

    private RateChartList list = new RateChartList(2019, 2019);

    @Test
    void getFileNameTest() {
        long time = 1546473600000L;
        String filename = list.getFileName(new Date(time), 'c');
        assertEquals("c002z190103", filename);
    }

    @Test
    void buildUrlTest() {
        String expectedUrl = "http://www.nbp.pl/kursy/xml/dir.txt";
        String gotUrl = RateChartList.buildUrl(2019);
        assertEquals(expectedUrl, gotUrl);
    }
}