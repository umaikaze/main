package seedu.address.model.pet;

import seedu.address.ui.DisplayItem;
import seedu.address.ui.DisplaySystemType;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Food Collection object in Pet Shop Helper.
 * Guarantees: immutable; name is valid as declared in {@link #isValidFoodCollectionName(String)}
 */
public class FoodCollection implements DisplayItem {

    public static final String MESSAGE_CONSTRAINTS = "Name and amount of food collection should both exist "
            + "and separated by a colon ':'.";
    public static final String MESSAGE_NAME_CONSTRAINTS = "Food collection names should only contain alphanumeric "
            + "characters and spaces only, and it should not be blank";
    public static final String MESSAGE_AMOUNT_CONSTRAINTS = "Food collection amount must be a positive integer number.";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String foodCollectionName;
    public Integer foodCollectionAmount;

    /**
     * Constructs a {@code Food}.
     *
     * @param foodCollectionName A valid food collection name.
     */
    private FoodCollection(String foodCollectionName, int foodCollectionAmount) {
        requireNonNull(foodCollectionName);
        checkArgument(isValidFoodCollectionName(foodCollectionName), MESSAGE_NAME_CONSTRAINTS);
        this.foodCollectionName = foodCollectionName;
        checkArgument(isValidFoodCollectionAmount(foodCollectionAmount), MESSAGE_AMOUNT_CONSTRAINTS);
        this.foodCollectionAmount = foodCollectionAmount;
    }

    /**
     * Returns true if the input food object is of same type as this food collection.
     */
    public boolean isSameType(Food other) {
        if (other == null) {
            return false;
        } else {
            return foodCollectionName.equals(other.foodName);
        }
    }

    public boolean addFoodToCollection(Food other) {
        if (foodCollectionName.equals(other.foodName)) {
            foodCollectionAmount += other.foodAmount;
            return true;
        } else {
            return false;
        }
    }

    public static FoodCollection GenerateFoodCollection(Food food) {
        return new FoodCollection(food.foodName, food.foodAmount);
    }

    /**
     * Returns true if a given string is a valid food collection name.
     */
    public static boolean isValidFoodCollectionName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given food collection amount is positive.
     */
    public static boolean isValidFoodCollectionAmount(Integer test) {
        return test > 0;
    }

    /**
     * Returns true if the input food collection object is of same type as this food collection.
     */
    public boolean isSameType(FoodCollection other) {
        if (other == null) {
            return false;
        } else {
            return foodCollectionName.equals(other.foodCollectionName);
        }
    }

    /**
     * Returns true if the input food collection object is of same amount as this food collection.
     */
    public boolean isSameAmount(FoodCollection other) {
        if (other == null) {
            return false;
        } else {
            return foodCollectionAmount.equals(other.foodCollectionAmount);
        }
    }

    public String getFoodCollectionName() {
        return foodCollectionName;
    }

    public Integer getFoodCollectionAmount() {
        return foodCollectionAmount;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || ((other instanceof Food) // instanceof handles nulls
                && isSameType((FoodCollection) other)
                && isSameAmount((FoodCollection) other)); // state checks
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodCollectionAmount, foodCollectionName);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[" + foodCollectionName + "]: " + foodCollectionAmount;
    }

    @Override
    public DisplaySystemType getDisplaySystemType() {
        return DisplaySystemType.INVENTORY;
    }
}
