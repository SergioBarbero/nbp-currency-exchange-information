import org.junit.jupiter.api.Test;
import pl.parser.nbp.ChartFile.ChartFile;
import pl.parser.nbp.ChartFile.ChartFileWrapper;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Tests {

    private ChartFileWrapper list = new ChartFileWrapper(2019, 2019);

    @Test
    void findFileTest() {
        long time = 1546473600000L;
        ChartFile filename = list.findFile(new Date(time), 'c');
        assertEquals("c002z190103", filename.getFileName());
    }

    @Test
    void buildUrlTest() {
        String expectedUrl = "http://www.nbp.pl/kursy/xml/dir.txt";
        String gotUrl = ChartFileWrapper.buildUrl(2019);
        assertEquals(expectedUrl, gotUrl);
    }
}
