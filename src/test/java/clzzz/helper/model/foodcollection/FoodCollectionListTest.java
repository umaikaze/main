package clzzz.helper.model.foodcollection;

import static clzzz.helper.testutil.Assert.assertThrows;
import static clzzz.helper.testutil.pet.TypicalPets.AMY;
import static clzzz.helper.testutil.pet.TypicalPets.ELLE;
import static clzzz.helper.testutil.pet.TypicalPets.getTypicalPets;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import clzzz.helper.model.pet.Food;
import clzzz.helper.model.pet.Pet;

public class FoodCollectionListTest {
    private final FoodCollectionList foodCollectionList = new FoodCollectionList(getTypicalPets());

    @Test
    public void contains_nullFood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> foodCollectionList.contains(null));
    }

    @Test
    public void contains_foodInList_returnsTrue() {
        Food food = (Food) AMY.getFoodList().toArray()[0];
        assertTrue(foodCollectionList.contains(food));
    }

    @Test
    public void contains_foodNotInList_returnsFalse() {
        Food food = new Food("not in the list", 10);
        assertFalse(foodCollectionList.contains(food));

    }

    @Test
    public void addFood_and_getFoodCollection() {
        Food food = new Food("unique food", 10);
        Pet pet = ELLE;

        foodCollectionList.addFood(food, pet);
        assertTrue(foodCollectionList.contains(food));
        FoodCollection foodCollection = foodCollectionList.getFoodCollection(food.foodName);
        assertTrue(foodCollection.isSameType(food) & foodCollection.getAmount().equals(food.foodAmount));
    }

    @Test
    public void getUnmodifiablePets_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                foodCollectionList.asUnmodifiableObservableList().remove(0));
    }

}
