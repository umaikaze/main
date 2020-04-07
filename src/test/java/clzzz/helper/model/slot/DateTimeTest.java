package clzzz.helper.model.slot;

import static clzzz.helper.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import clzzz.helper.commons.util.DateTimeUtil;

class DateTimeTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateTime(null));
    }

    @Test
    void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new DateTime(invalidDate));
    }

    @Test
    void isValidDateTime() {
        // Non-date-times
        assertFalse(DateTime.isValidDateTime(" ")); // just empty space
        assertFalse(DateTime.isValidDateTime("not a datetime")); // a sentence

        // Badly formatted dates
        assertFalse(DateTime.isValidDateTime("not a date 2130")); // not a date
        assertFalse(DateTime.isValidDateTime("7-Mar-2020 2130")); // d MMM yyyy
        assertFalse(DateTime.isValidDateTime("2020-03-10 2130")); // yyyy mm dd
        assertFalse(DateTime.isValidDateTime("10-3-20 2130")); // d-M-yy
        assertFalse(DateTime.isValidDateTime("7_3_2020 2130")); // bad seperator
        assertFalse(DateTime.isValidDateTime("7-3-2020 2130")); // bad seperator
        assertFalse(DateTime.isValidDateTime("2020 2130")); // year only
        assertFalse(DateTime.isValidDateTime("7-3 2130")); // date and month only

        // Badly formatted times
        assertFalse(DateTime.isValidDateTime("7/3/2020 not a time")); // not a time
        assertFalse(DateTime.isValidDateTime("7/3/2020 45")); // HHmm not a 4 digit number
        assertFalse(DateTime.isValidDateTime("7/3/2020 12345")); // HHmm not a 4 digit number
        assertFalse(DateTime.isValidDateTime("7/3/2020 -1000")); // HHmm not a 4 digit number
        assertFalse(DateTime.isValidDateTime("7/3/2020 2400")); // HH greater than 23
        assertFalse(DateTime.isValidDateTime("7/3/2020 0060")); // mm greater than 59

        // Other invalid date-times
        assertFalse(DateTime.isValidDateTime("7/3/20202130")); // no space separator between date and time
        assertFalse(DateTime.isValidDateTime("2130 7/3/2020")); // wrong sequence of date and time
        String epoch = DateTimeUtil.formatLocalDateTime(LocalDate.EPOCH.atStartOfDay());
        assertFalse(DateTime.isValidDateTime(epoch)); // not after the epoch, exactly on the epoch

        // Good dates
        assertTrue(DateTime.isValidDateTime("7/3/2020 2130")); // d/M/yyyy
        assertTrue(DateTime.isValidDateTime("07/03/2020 2130")); // dd/MM/yyyy
        assertTrue(DateTime.isValidDateTime("7/03/2020 2130")); // d/MM/yyyy
        assertTrue(DateTime.isValidDateTime("07/3/2020 2130")); // dd/M/yyyy
    }
}
