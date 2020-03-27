package seedu.address.model.pet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashMap;
import java.util.Objects;

import seedu.address.ui.DisplayItem;
import seedu.address.ui.DisplaySystemType;

/**
 * Represents a Food Collection object in Pet Shop Helper. It is used to model a collection of the same type
 * of food that could exist in a pet list. (If there are 3 pets in a list, each requiring 10 units of cat food,
 * a food collection of the cat food in the list will have name as catfood and amount as 30.)
 * Guarantees: immutable; name is valid as declared in {@link #isValidFoodCollectionName(String)}
 */
public class FoodCollection implements DisplayItem {

    public static final String MESSAGE_CONSTRAINTS = "Name and amount of food collection should both exist "
            + "and separated by a colon ':'.";
    public static final String MESSAGE_NAME_CONSTRAINTS = "Food collection names should only contain alphanumeric "
            + "characters and spaces only, and it should not be blank";
    public static final String MESSAGE_AMOUNT_CONSTRAINTS = "Food collection amount must be a positive integer number.";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private final String name;
    private int amount;
    private HashMap<String, Integer> pets = new HashMap<>();

    /**
     * Constructs a {@code Food}.
     *
     * @param food A valid food to generate Food Collection from.
     * @param pet The owner of the food being added.
     */
    private FoodCollection(Food food, Pet pet) {
        requireNonNull(food);
        requireNonNull(pet);
        checkArgument(isValidFoodCollectionName(food.foodName), MESSAGE_NAME_CONSTRAINTS);
        this.name = food.foodName;
        checkArgument(isValidFoodCollectionAmount(amount), MESSAGE_AMOUNT_CONSTRAINTS);
        this.amount = food.foodAmount;
        this.pets.put(pet.getName().fullName, amount);
    }

    /**
     * Adds food into the food collection.
     * @param other The food to be added.
     * @return true if it is successfully added, which means
     * the type of food has the same name as that of the food collection.
     */
    public boolean addFoodToCollection(Food other, Pet pet) {
        if (isSameType(other)) {
            amount += other.foodAmount;
            pets.put(pet.getName().fullName, amount);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Generates a food collection from a given food object.
     */
    public static FoodCollection generateFoodCollection(Food food, Pet pet) {
        return new FoodCollection(food, pet);
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
            return name.equals(other.name);
        }
    }

    /**
     * Returns true if the input food object is of same type as this food collection.
     */
    public boolean isSameType(Food other) {
        if (other == null) {
            return false;
        } else {
            return name.equals(other.foodName);
        }
    }

    /**
     * Returns true if the input food collection object is of same amount as this food collection.
     */
    public boolean isSameAmount(FoodCollection other) {
        if (other == null) {
            return false;
        } else {
            return amount == other.amount;
        }
    }

    public String getName() {
        return name;
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || ((other instanceof FoodCollection) // instanceof handles nulls
                && isSameType((FoodCollection) other)
                && isSameAmount((FoodCollection) other)); // state checks
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, name);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[" + name + "]: " + amount;
    }

    @Override
    public DisplaySystemType getDisplaySystemType() {
        return DisplaySystemType.INVENTORY;
    }
}
