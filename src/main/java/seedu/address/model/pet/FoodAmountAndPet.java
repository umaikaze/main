package seedu.address.model.pet;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.ui.DisplaySystemType;
import seedu.address.ui.list.DisplayItem;

/**
 * Represents a relationship between pet and an integer attribute amount, which indicates the amount of a certain
 * type of food required by a pet. This item is stored under a FoodCollection object and thus the type of food the pet
 * requires is specified in FoodCollection.
 */
public class FoodAmountAndPet implements DisplayItem {
    private final Integer foodAmount;
    private final Pet pet;

    /**
     * Instantiates a FoodAmountAndPet object which represents the relationship that the given pet requires
     * the given amount of a certain type of food.
     */
    public FoodAmountAndPet(int foodAmount, Pet pet) {
        requireNonNull(foodAmount);
        requireNonNull(pet);
        this.foodAmount = foodAmount;
        this.pet = pet;
    }

    @Override
    public DisplaySystemType getDisplaySystemType() {
        return DisplaySystemType.FOOD_AMOUNT_AND_PET;
    }

    /**
     * Returns the food amount of the certain type of food kept by this pet.
     */
    public Integer getFoodAmount() {
        return foodAmount;
    }

    /**
     * Returns the pet involved in this relationship.
     */
    public Pet getPet() {
        return pet;
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodAmount, pet);
    }

    @Override
    public String toString() {
        return "FoodAmountAndPet{" + "foodAmount=" + foodAmount + ", pet=" + pet + '}';
    }
}
