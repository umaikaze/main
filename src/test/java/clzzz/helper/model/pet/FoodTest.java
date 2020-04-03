package clzzz.helper.model.pet;

import static clzzz.helper.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FoodTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Food(null, 0));
    }

    @Test
    void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        String validName = "kibbles";
        int invalidAmount = -1;
        int validAmount = 1;
        assertThrows(IllegalArgumentException.class, () -> new Food(invalidName, validAmount));
        assertThrows(IllegalArgumentException.class, () -> new Food(validName, invalidAmount));
    }

    @Test
    void isValidFoodName() {
        // null name
        assertThrows(NullPointerException.class, () -> Food.isValidFoodName(null));

        // invalid name
        assertFalse(Food.isValidFoodName("")); // empty string
        assertFalse(Food.isValidFoodName(" ")); // spaces only
        assertFalse(Food.isValidFoodName("one line isn't\nenough for this food")); // multiple lines

        // valid name
        assertTrue(Food.isValidFoodName("^")); // only non-alphanumeric characters
        assertTrue(Food.isValidFoodName("hot coca* for dogs")); // contains non-alphanumeric characters
        assertTrue(Food.isValidFoodName("very delicious cat food")); // alphabets only
        assertTrue(Food.isValidFoodName("195")); // numbers only
        assertTrue(Food.isValidFoodName("best food 4 cats")); // alphanumeric characters
        assertTrue(Food.isValidFoodName("CAT FOOD very good hmm")); // with capital letters
        assertTrue(Food.isValidFoodName("meshed food made for lazy cats staying at home")); // long names
        assertTrue(Food.isValidFoodName("can use more than ₉99 times")); // subscripts
        assertTrue(Food.isValidFoodName("the Ⅸth test")); // roman numbers
        assertTrue(Food.isValidFoodName("high Ω3 content"));
        assertTrue(Food.isValidFoodName("∀ I forgot what this sign means"));
        assertTrue(Food.isValidFoodName("∆nutrition is top tier"));
        assertTrue(Food.isValidFoodName("∑s up all the benefits"));
        assertTrue(Food.isValidFoodName("⋮⋰⋯⋱"));
        assertTrue(Food.isValidFoodName("ni↗ce↘ fo↗od↘"));
        assertTrue(Food.isValidFoodName("⑨ strongest food"));
    }

    @Test
    void isValidFoodAmount() {
        // invalid amount
        assertFalse(Food.isValidFoodAmount(0)); // reaches zero
        assertFalse(Food.isValidFoodAmount(-1)); // negative
        assertFalse(Food.isValidFoodAmount(Integer.MIN_VALUE)); // negative

        // valid amount
        assertTrue(Food.isValidFoodAmount(1)); // positive integer
        assertTrue(Food.isValidFoodAmount(Integer.MAX_VALUE)); // positive integer
    }

    @Test
    void testEquals() {
        String foodName1 = "first food";
        String foodName2 = "second food";
        int amt1 = 1;
        int amt2 = 2;
        // one equal but the other isn't
        assertFalse(new Food(foodName1, amt1).equals(new Food(foodName2, amt1)));
        assertFalse(new Food(foodName1, amt1).equals(new Food(foodName1, amt2)));

        // both unequal
        assertFalse(new Food(foodName1, amt1).equals(new Food(foodName2, amt2)));

        // equal
        assertTrue(new Food(foodName1, amt1).equals(new Food(foodName1, amt1)));
    }
}
