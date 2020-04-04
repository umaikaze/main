package seedu.address.commons.util;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * Contains standard datetime and date patterns to be used in Pet Store Helper.
 */
public class DateTimeUtil {

    // datetime-related
    public static final String DATETIME_PATTERN = "d/M/yyyy HHmm";
    public static final DateTimeFormatter DATETIME_FORMAT =
            DateTimeFormatter.ofPattern(DATETIME_PATTERN).withResolverStyle(ResolverStyle.LENIENT);

    // date-related
    public static final String DATE_PATTERN = "d/M/yyyy";
    public static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    // back up-related
    public static final String BACK_UP_PATTERN = "yyyyMMdd_HH_mm_ss";
    public static final DateTimeFormatter BACK_UP_FORMAT =
            DateTimeFormatter.ofPattern(BACK_UP_PATTERN);

    public static boolean isValidDateString(String date) {
        String[] parsedDate = date.split("/");
        int day = Integer.parseInt(parsedDate[0]);
        int month = Integer.parseInt(parsedDate[1]);
        int year = Integer.parseInt(parsedDate[2]);
        return isValidDate(day, month, year);
    }

    public static boolean isValidTimeString(String time) {
        int timeInt = Integer.parseInt(time);
        if (timeInt < 0 || timeInt >= 2400) {
            return false;
        }
        if (timeInt % 100 >= 60) {
            return false;
        }
        return true;
    }

    public static boolean isValidDate(int day, int month, int year) {
        if (day < 1 || day > 31 || month < 1 || month > 12) {
            return false;
        }
        if (day <= 28) {
            return true;
        }
        switch (month) {
        case 2:
            if (isLeap(year)) {
                return day <= 29;
            }
            return false;
        case 4:
        case 6:
        case 9:
        case 11:
            return day <= 30;
        }
        return true;
    }

    public static boolean isLeap(int year) {
        if (year % 400 == 0) {
            return true;
        }
        if (year % 100 == 0) {
            return false;
        }
        if (year % 4 == 0) {
            return true;
        }
        return false;
    }
}
