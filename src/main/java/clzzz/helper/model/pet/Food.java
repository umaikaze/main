package clzzz.helper.model.pet;

import static clzzz.helper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents a Food object in Pet Store Helper.
 * Guarantees: immutable; name is valid as declared in {@link #isValidFoodName(String)}
 */
public class Food {

    public static final String MESSAGE_CONSTRAINTS = "Name and amount of food should both exist "
            + "and separated by a colon ':'.";
    public static final String MESSAGE_NAME_CONSTRAINTS = "Food names should only contain alphanumeric characters and "
            + "spaces only, and it should not be blank";
    public static final String MESSAGE_AMOUNT_CONSTRAINTS = "Food amount must be a positive integer number.";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String foodName;
    public final Integer foodAmount;

    /**
     * Constructs a {@code Food}.
     *
     * @param foodName A valid food name.
     */
    public Food(String foodName, int foodAmount) {
        requireNonNull(foodName);
        checkArgument(isValidFoodName(foodName), MESSAGE_NAME_CONSTRAINTS);
        this.foodName = formatFoodName(foodName);
        checkArgument(isValidFoodAmount(foodAmount), MESSAGE_AMOUNT_CONSTRAINTS);
        this.foodAmount = foodAmount;
    }

    /**
     * Returns true if a given string is a valid food name.
     */
    public static boolean isValidFoodName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given food amount is positive.
     */
    public static boolean isValidFoodAmount(Integer test) {
        return test > 0;
    }

    /**
     * Transfer food name in to the format of "Xxx Xxx ...".
     * @param foodName The food name passed in by user
     * @return The formatted food name
     */
    public String formatFoodName(String foodName) {
        String[] foodNameSubStrings = foodName.split(" ");
        String formattedFoodName = "";
        for (String n : foodNameSubStrings) {
            n = n.substring(0, 1).toUpperCase() + n.substring(1).toLowerCase();
            formattedFoodName += (n + " ");
        }
        return formattedFoodName.trim();
    }

    /**
     * Returns true if the input food object is of same type as this food.
     */
    public boolean isSameType(Food other) {
        if (other == null) {
            return false;
        } else {
            return foodName.equals(other.foodName);
        }
    }

    /**
     * Returns true if the input food object is of same amount as this food.
     */
    public boolean isSameAmount(Food other) {
        if (other == null) {
            return false;
        } else {
            return foodAmount.equals(other.foodAmount);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || ((other instanceof Food) // instanceof handles nulls
                && isSameType((Food) other)
                && isSameAmount((Food) other)); // state checks
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodAmount, foodName);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[" + foodName + "]: " + foodAmount;
    }

}
