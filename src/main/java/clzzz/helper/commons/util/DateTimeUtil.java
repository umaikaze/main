package clzzz.helper.commons.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Contains standard datetime and date patterns to be used in Pet Store Helper.
 */
public class DateTimeUtil {

    // datetime-related
    public static final String DATETIME_PATTERN = "d/M/yyyy HHmm";
    public static final DateTimeFormatter DATETIME_FORMAT =
            DateTimeFormatter.ofPattern(DATETIME_PATTERN);

    // date-related
    public static final String DATE_PATTERN = "d/M/yyyy";
    public static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    // back up-related
    public static final String BACK_UP_PATTERN = "yyyyMMdd_HH_mm_ss";
    public static final DateTimeFormatter BACK_UP_FORMAT =
            DateTimeFormatter.ofPattern(BACK_UP_PATTERN);


    // datetime-related
    public static final LocalDateTime parseLocalDateTime(String text) {
        return LocalDateTime.parse(text, DATETIME_FORMAT);
    }

    public static final String formatLocalDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATETIME_FORMAT);
    }

    // date-related
    public static final LocalDate parseLocalDate(String text) {
        return LocalDate.parse(text, DATE_FORMAT);
    }

    public static final String formatLocalDate(LocalDate date) {
        return date.format(DATE_FORMAT);
    }
}
