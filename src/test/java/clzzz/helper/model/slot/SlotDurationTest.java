package clzzz.helper.model.slot;

import static clzzz.helper.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SlotDurationTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SlotDuration(null));
    }

    @Test
    void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidDuration = "";
        assertThrows(IllegalArgumentException.class, () -> new SlotDuration(invalidDuration));
    }

    @Test
    void isValidDuration() {
        // Cannot cast from String to long
        assertFalse(SlotDuration.isValidDuration("word")); // contains no digits
        assertFalse(SlotDuration.isValidDuration("ff")); // not a valid number in base 10
        assertFalse(SlotDuration.isValidDuration("0xff")); // not a valid number in base 10
        assertFalse(SlotDuration.isValidDuration("23.0")); // cannot contain the decimal point

        // Zero length duration
        assertFalse(SlotDuration.isValidDuration("0"));

        // Negative duration
        assertFalse(SlotDuration.isValidDuration("-1"));

        // Not shorter than length of day
        assertFalse(SlotDuration.isValidDuration("1440")); // exact length of day
        assertFalse(SlotDuration.isValidDuration("1441")); // longer than a day

        // Good slot durations
        assertTrue(SlotDuration.isValidDuration("023")); // allow leading '0' digits; parsed as 23 minutes
        assertTrue(SlotDuration.isValidDuration("1")); // minimum allowed length
        assertTrue(SlotDuration.isValidDuration("1439")); // maximum allowed length
    }
}
