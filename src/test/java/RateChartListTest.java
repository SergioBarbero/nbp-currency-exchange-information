import org.junit.jupiter.api.Test;
import pl.parser.nbp.RateChartList;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RateChartListTest {

    private RateChartList list;

    private RateChartListTest() {
        this.list = new RateChartList(2019, 2019);
    }

    @Test
    void getFileNameTest() {
        long time = 1546473600000L;
        String filename = list.getFileName(new Date(time), 'c');
        assertEquals("c002z190103", filename);
    }
}