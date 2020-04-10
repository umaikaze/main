package clzzz.helper.commons.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * Contains standard datetime and date patterns to be used in Pet Store Helper.
 */
public class DateTimeUtil {

    // constants

    // datetime-related
    public static final String DATETIME_PATTERN = "d/M/uuuu HHmm";
    //used to check the format of date time
    public static final DateTimeFormatter DATETIME_FORMAT =
            DateTimeFormatter.ofPattern(DATETIME_PATTERN).withResolverStyle(ResolverStyle.STRICT);

    // date-related
    public static final String DATE_PATTERN = "d/M/uuuu";
    //used to check if the date format is valid
    public static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern(DATE_PATTERN).withResolverStyle(ResolverStyle.STRICT);

    // back up-related
    public static final String BACK_UP_PATTERN = "uuuuMMdd_HH_mm_ss";
    public static final DateTimeFormatter BACK_UP_FORMAT = DateTimeFormatter.ofPattern(BACK_UP_PATTERN);


    // static methods

    // datetime-related

    /**
     * Obtains an instance of LocalDateTime from a text string using {@code DateTimeUtil.DATETIME_FORMAT}.
     */
    public static final LocalDateTime parseLocalDateTime(String text) {
        return LocalDateTime.parse(text, DATETIME_FORMAT);
    }

    /**
     * Formats a given datetime using {@code DateTimeUtil.DATETIME_FORMAT}.
     */
    public static final String formatLocalDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATETIME_FORMAT);
    }

    // date-related

    /**
     * Obtains an instance of LocalDate from a text string using {@code DateTimeUtil.DATE_FORMAT}.
     */
    public static final LocalDate parseLocalDate(String text) {
        return LocalDate.parse(text, DATE_FORMAT);
    }

    /**
     * Formats a given date using {@code DateTimeUtil.DATE_FORMAT}.
     */
    public static final String formatLocalDate(LocalDate date) {
        return date.format(DATE_FORMAT);
    }
}
