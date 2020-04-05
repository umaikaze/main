package clzzz.helper.model.pet;

import static clzzz.helper.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DateOfBirthTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateOfBirth(null));
    }

    @Test
    void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(invalidDate));
    }

    @Test
    void isValidDateOfBirth() {
        // Non-dates
        assertFalse(DateOfBirth.isValidDateFormat(" ")); // just empty space
        assertFalse(DateOfBirth.isValidDateFormat("not a date")); // a sentence

        // Badly formatted dates
        assertFalse(DateOfBirth.isValidDateFormat("7-Mar-2020")); // d MMM yyyy
        assertFalse(DateOfBirth.isValidDateFormat("2020-03-10")); // yyyy mm dd
        assertFalse(DateOfBirth.isValidDateFormat("10-3-20")); // d-M-yy
        assertFalse(DateOfBirth.isValidDateFormat("7_3_2020")); // bad seperator
        assertFalse(DateOfBirth.isValidDateFormat("7-3-2020")); // bad seperator
        assertFalse(DateOfBirth.isValidDateFormat("2020")); // year only
        assertFalse(DateOfBirth.isValidDateFormat("7-3")); // date and month only

        //Invalid dates
        assertFalse(DateOfBirth.isValidDate("29/2/2019"));
        assertFalse(DateOfBirth.isValidDate("-1/1/2019"));
        assertFalse(DateOfBirth.isValidDate("31/4/2019"));


        // Good dates
        assertTrue(DateOfBirth.isValidDate("7/3/2020")); // d/M/uuuu
        assertTrue(DateOfBirth.isValidDate("07/03/2020")); // dd/MM/uuuu
        assertTrue(DateOfBirth.isValidDate("7/03/2020")); // d/MM/uuuu
        assertTrue(DateOfBirth.isValidDate("07/3/2020")); // dd/M/uuuu
    }
}
