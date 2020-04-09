package pl.parser.nbp.util;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.parser.nbp.util.DateUtil.getYearFromDate;

public class DateUtilTest {

    private final static DateFormat FORMAT = new SimpleDateFormat("yyMMdd");

    @Test
    void shouldGetYearFromDate() throws ParseException {
        Date date = FORMAT.parse("200104");
        assertThat(getYearFromDate(date)).isEqualTo(2020);
    }
}
