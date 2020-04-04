package clzzz.helper.model.pet;

import static clzzz.helper.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SpeciesTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Species(null));
    }

    @Test
    void constructor_invalidSpecies_throwsIllegalArgumentException() {
        String invalidSpecies = "";
        assertThrows(IllegalArgumentException.class, () -> new Species(invalidSpecies));
    }

    @Test
    void isValidSpecies() {
        // null Species
        assertThrows(NullPointerException.class, () -> Species.isValidSpecies(null));

        // invalid Species
        assertFalse(Species.isValidSpecies("")); // empty string
        assertFalse(Species.isValidSpecies(" ")); // spaces only
        assertFalse(Species.isValidSpecies("^")); // only non-alphanumeric characters
        assertFalse(Species.isValidSpecies("dog*")); // contains non-alphanumeric characters

        // valid Species
        assertTrue(Species.isValidSpecies("dog cat")); // alphabets only
        assertTrue(Species.isValidSpecies("101")); // numbers only
        assertTrue(Species.isValidSpecies("genetically engineered cat girls "
                + "prototype 1")); // alphanumeric characters
        assertTrue(Species.isValidSpecies("Omega Wolf")); // with capital letters
        assertTrue(Species.isValidSpecies("As if the previous species name is not long enough here is "
                + "one to bring it to the next level")); // long Speciess
    }
}
