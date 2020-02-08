package pl.parser.nbp.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static Date getDateFromStringWithFormatYYMMDD(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyMMdd");
        return format.parse(date);
    }
}
