package w154.helper.model.pet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static w154.helper.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import w154.helper.testutil.pet.TypicalPets;

public class FoodCollectionListTest {
    private final FoodCollectionList foodCollectionList = new FoodCollectionList(TypicalPets.getTypicalPets());

    @Test
    public void contains_nullFood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> foodCollectionList.contains(null));
    }

    @Test
    public void contains_foodInList_returnsTrue() {
        Food food = (Food) TypicalPets.AMY.getFoodList().toArray()[0];
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
        Pet pet = TypicalPets.ELLE;

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
