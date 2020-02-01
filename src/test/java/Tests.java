import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.parser.nbp.ChartFile.ChartFile;
import pl.parser.nbp.ChartFile.ChartType;
import pl.parser.nbp.ChartFile.ChartFileService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Tests {

    @Autowired
    private ChartFileService ChartFileService;

    @Test
    void findFileTest() {
        long time = 1546473600000L;
        ChartFile filename = ChartFileService.findFileBy(new Date(time), ChartType.c);
        assertEquals("c002z190103", filename.getFileName());
    }
}
