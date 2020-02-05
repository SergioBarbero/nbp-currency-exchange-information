package pl.parser.nbp.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateUtil {

    private DateUtil() {
        throw new AssertionError();
    }

    public static int getYearFromDate(Date date) {
        Calendar gregorianCalendarEnd = new GregorianCalendar();
        gregorianCalendarEnd.setTime(date);
        return gregorianCalendarEnd.get(Calendar.YEAR);
    }
}
