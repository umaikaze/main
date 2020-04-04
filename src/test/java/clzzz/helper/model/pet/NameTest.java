package clzzz.helper.model.pet;

import static clzzz.helper.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class NameTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("coca*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("very big dog")); // alphabets only
        assertTrue(Name.isValidName("101")); // numbers only
        assertTrue(Name.isValidName("no 1 dogggg")); // alphanumeric characters
        assertTrue(Name.isValidName("Ozzy Pawsborne")); // with capital letters
        assertTrue(Name.isValidName("Doc McDoggins Successor of Paws Davis the 3rd")); // long names
    }
}
