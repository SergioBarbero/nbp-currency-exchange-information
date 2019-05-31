import org.junit.jupiter.api.Test;
import pl.parser.nbp.RateChartList;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RateChartListTest {

    @Test
    void getFileNameTest() {
        try {
            RateChartList list = new RateChartList(2019);
            long time = 1546473600000L;
            Date date = new Date(time);
            String filename = list.getFileName(date, 'c');
            assertEquals("c002z190103", filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}