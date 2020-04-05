package clzzz.helper.commons.util;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * Contains standard datetime and date patterns to be used in Pet Store Helper.
 */
public class DateTimeUtil {

    // datetime-related
    public static final String DATETIME_PATTERN = "d/M/uuuu HHmm";
    //used to check the format of date time
    public static final DateTimeFormatter DATETIME_FORMAT =
            DateTimeFormatter.ofPattern(DATETIME_PATTERN).withResolverStyle(ResolverStyle.LENIENT);
    // used to check if the time is valid
    public static final String TIME_PATTERN = "HHmm";
    public static final DateTimeFormatter STRICT_TIME_FORMAT =
            DateTimeFormatter.ofPattern(TIME_PATTERN).withResolverStyle(ResolverStyle.STRICT);


    // date-related
    public static final String DATE_PATTERN = "d/M/uuuu";
    //used to check if the date format is valid
    public static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    //used to check if the date is valid
    public static final DateTimeFormatter STRICT_DATE_FORMAT =
            DateTimeFormatter.ofPattern(DATE_PATTERN).withResolverStyle(ResolverStyle.STRICT);

    // back up-related
    public static final String BACK_UP_PATTERN = "uuuuMMdd_HH_mm_ss";
    public static final DateTimeFormatter BACK_UP_FORMAT =
            DateTimeFormatter.ofPattern(BACK_UP_PATTERN);

}
