package clzzz.helper.model.pet;

import static clzzz.helper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.Objects;

import clzzz.helper.ui.DisplaySystemType;
import clzzz.helper.ui.list.DisplayItem;

/**
 * Represents a relationship between pet and an integer attribute amount, which indicates the amount of a certain
 * type of food required by a pet. This item is stored under a FoodCollection object and thus the type of food the pet
 * requires is specified in FoodCollection.
 */
public class FoodAmountAndPet implements DisplayItem {
    public static final String MESSAGE_AMOUNT_CONSTRAINTS = "Food amount must be a positive integer number.";

    private final Integer foodAmount;
    private final Pet pet;


    /**
     * Instantiates a FoodAmountAndPet object which represents the relationship that the given pet requires
     * the given amount of a certain type of food.
     */
    public FoodAmountAndPet(int foodAmount, Pet pet) {
        requireNonNull(pet);
        checkArgument(isValidFoodAmount(foodAmount), MESSAGE_AMOUNT_CONSTRAINTS);
        this.foodAmount = foodAmount;
        this.pet = pet;
    }

    /**
     * Returns true if a given food amount is positive.
     */
    public static boolean isValidFoodAmount(Integer test) {
        return test > 0;
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
