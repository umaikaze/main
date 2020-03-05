package seedu.address.model.pet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food object in Pet Shop Helper.
 * Guarantees: immutable; name is valid as declared in {@link #isValidFoodName(String)}
 */
public class Food {

    public static final String MESSAGE_CONSTRAINTS = "Food names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String foodName;
    public final Integer foodAmount;

    /**
     * Constructs a {@code Food}.
     *
     * @param foodName A valid tag name.
     */
    public Food(String foodName, int foodAmount) {
        requireNonNull(foodName);
        checkArgument(isValidFoodName(foodName), MESSAGE_CONSTRAINTS);
        this.foodName = foodName;
        checkArgument(isValidFoodAmount(foodAmount));
        this.foodAmount = foodAmount;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidFoodName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Return true if a given food amount is positive.
     */
    public static boolean isValidFoodAmount(Integer test) {
        return test > 0;
    }

    public boolean isSameType(Food other) {
        if (other == null) {
            return false;
        } else {
            return foodAmount.equals(other.foodAmount);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Food // instanceof handles nulls
                && foodName.equals(((Food) other).foodName)
                && foodAmount.equals(((Food) other).foodAmount)); // state check
    }

    @Override
    public int hashCode() {
        return foodName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[" + foodName + "]: " + foodAmount;
    }

}
