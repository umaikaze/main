package seedu.address.commons.util;

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
}
