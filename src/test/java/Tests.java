import org.junit.jupiter.api.Test;
import pl.parser.nbp.ChartFile.ChartFile;
import pl.parser.nbp.ChartFile.ChartFileService;
import pl.parser.nbp.ChartFile.ChartType;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Tests {

    private ChartFileService list = new ChartFileService(2019, 2019);

    @Test
    void findFileTest() {
        long time = 1546473600000L;
        ChartFile filename = list.findFileBy(new Date(time), ChartType.c);
        assertEquals("c002z190103", filename.getFileName());
    }
}
