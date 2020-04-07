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
        assertFalse(DateOfBirth.isValidDateOfBirth(" ")); // just empty space
        assertFalse(DateOfBirth.isValidDateOfBirth("not a date")); // a sentence

        // Badly formatted dates
        assertFalse(DateOfBirth.isValidDateOfBirth("7-Mar-2020")); // d MMM yyyy
        assertFalse(DateOfBirth.isValidDateOfBirth("2020-03-10")); // yyyy mm dd
        assertFalse(DateOfBirth.isValidDateOfBirth("10-3-20")); // d-M-yy
        assertFalse(DateOfBirth.isValidDateOfBirth("7_3_2020")); // bad seperator
        assertFalse(DateOfBirth.isValidDateOfBirth("7-3-2020")); // bad seperator
        assertFalse(DateOfBirth.isValidDateOfBirth("2020")); // year only
        assertFalse(DateOfBirth.isValidDateOfBirth("7-3")); // date and month only

        //Invalid dates
        assertFalse(DateOfBirth.isValidDateOfBirth("29/2/2019"));
        assertFalse(DateOfBirth.isValidDateOfBirth("-1/1/2019"));
        assertFalse(DateOfBirth.isValidDateOfBirth("31/4/2019"));


        // Good dates
        assertTrue(DateOfBirth.isValidDateOfBirth("7/3/2020")); // d/M/uuuu
        assertTrue(DateOfBirth.isValidDateOfBirth("07/03/2020")); // dd/MM/uuuu
        assertTrue(DateOfBirth.isValidDateOfBirth("7/03/2020")); // d/MM/uuuu
        assertTrue(DateOfBirth.isValidDateOfBirth("07/3/2020")); // dd/M/uuuu
    }
}
