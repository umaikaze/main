package clzzz.helper.model.foodcollection;

import static clzzz.helper.testutil.Assert.assertThrows;
import static clzzz.helper.testutil.pet.TypicalPets.COCO;
import static clzzz.helper.testutil.pet.TypicalPets.GARFIELD;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import clzzz.helper.model.pet.Food;
import clzzz.helper.model.pet.Pet;

public class FoodCollectionTest {

    private Food foodA1 = new Food("first food", 1);
    private Food foodA2 = new Food("first food", 2);
    private Food foodB1 = new Food("second food", 1);
    private Food foodB2 = new Food("second food", 2);
    private Pet pet1 = COCO;
    private Pet pet2 = GARFIELD;

    @Test
    void isValidFoodCollectionName() {
        // null name
        assertThrows(NullPointerException.class, () -> FoodCollection.isValidFoodCollectionName(null));

        // invalid name
        assertFalse(FoodCollection.isValidFoodCollectionName("")); // empty string
        assertFalse(FoodCollection.isValidFoodCollectionName(" ")); // spaces only
        assertFalse(FoodCollection.isValidFoodCollectionName("one line isn't\nenough for this food"));

        // valid name
        assertTrue(FoodCollection.isValidFoodCollectionName("^")); // only non-alphanumeric characters
        assertTrue(FoodCollection.isValidFoodCollectionName("hot coca* for dogs"));
        assertTrue(FoodCollection.isValidFoodCollectionName("very delicious cat food")); // alphabets only
        assertTrue(FoodCollection.isValidFoodCollectionName("195")); // numbers only
        assertTrue(FoodCollection.isValidFoodCollectionName("best food 4 cats")); // alphanumeric characters
        assertTrue(FoodCollection.isValidFoodCollectionName("CAT FOOD very good hmm")); // with capital letters
        assertTrue(FoodCollection.isValidFoodCollectionName("meshed food made for lazy cats staying at home"));
        assertTrue(FoodCollection.isValidFoodCollectionName("can use more than ₉99 times"));
        assertTrue(FoodCollection.isValidFoodCollectionName("the Ⅸth test"));
        assertTrue(FoodCollection.isValidFoodCollectionName("high Ω3 content"));
        assertTrue(FoodCollection.isValidFoodCollectionName("∀ I forgot what this sign means"));
        assertTrue(FoodCollection.isValidFoodCollectionName("∆nutrition is top tier"));
        assertTrue(FoodCollection.isValidFoodCollectionName("∑s up all the benefits"));
        assertTrue(FoodCollection.isValidFoodCollectionName("⋮⋰⋯⋱"));
        assertTrue(FoodCollection.isValidFoodCollectionName("ni↗ce↘ fo↗od↘"));
        assertTrue(FoodCollection.isValidFoodCollectionName("⑨ strongest food"));
    }

    @Test
    void isValidFoodCollectionAmount() {
        // invalid amount
        assertFalse(FoodCollection.isValidFoodCollectionAmount(0)); // reaches zero
        assertFalse(FoodCollection.isValidFoodCollectionAmount(-1)); // negative
        assertFalse(FoodCollection.isValidFoodCollectionAmount(Integer.MIN_VALUE)); // negative

        // valid amount
        assertTrue(FoodCollection.isValidFoodCollectionAmount(1)); // positive integer
        assertTrue(FoodCollection.isValidFoodCollectionAmount(Integer.MAX_VALUE)); // positive integer
    }

    @Test
    void testEquals() {

        // food amount of food type is different
        assertFalse(FoodCollection.generateFoodCollection(foodA1, pet1).equals(
                FoodCollection.generateFoodCollection(foodB1, pet1)));
        assertFalse(FoodCollection.generateFoodCollection(foodA1, pet1).equals(
                FoodCollection.generateFoodCollection(foodA2, pet1)));

        // both unequal
        assertFalse(FoodCollection.generateFoodCollection(foodA1, pet1).equals(
                FoodCollection.generateFoodCollection(foodB2, pet1)));

        // equal
        assertTrue(FoodCollection.generateFoodCollection(foodA1, pet1).equals(
                FoodCollection.generateFoodCollection(foodA1, pet1)));
    }

    @Test
    void addFoodToCollectionTest() {

        FoodCollection foodCollection = FoodCollection.generateFoodCollection(foodA1, pet1);

        //food is of the same type
        assertTrue(foodCollection.addFoodToCollection(foodA1, pet2)); //same amount
        assertTrue(foodCollection.addFoodToCollection(foodA2, pet2)); //different amount

        //food is of different type
        assertFalse(foodCollection.addFoodToCollection(foodB1, pet2));
        assertFalse(foodCollection.addFoodToCollection(foodB2, pet2));
    }

    @Test
    public void getUnmodifiablePets_modifyList_throwsUnsupportedOperationException() {
        FoodCollection foodCollection = FoodCollection.generateFoodCollection(foodA1, pet1);

        assertThrows(UnsupportedOperationException.class, () ->
                foodCollection.getUnmodifiablePets().remove(0));
    }
}
