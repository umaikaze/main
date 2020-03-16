package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.pet.Food;

/**
 * Jackson-friendly version of {@link Food}.
 */
class JsonAdaptedFood {

    private final String foodName;
    private final Integer foodAmount;

    /**
     * Constructs a {@code JsonAdaptedFood} with the given {@code foodName}.
     */
    @JsonCreator
    public JsonAdaptedFood(@JsonProperty("foodName") String foodName,
                           @JsonProperty("foodAmount") Integer foodAmount) {
        this.foodName = foodName;
        this.foodAmount = foodAmount;
    }

    /**
     * Converts a given {@code Food} into this class for Jackson use.
     */
    public JsonAdaptedFood(Food source) {
        foodName = source.foodName;
        foodAmount = source.foodAmount;
    }

    @JsonValue
    public String getFoodName() {
        return foodName;
    }

    @JsonValue
    public String getFoodAmount() {
        return foodName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Food} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Food toModelType() throws IllegalValueException {
        if (!Food.isValidFoodName(foodName)) {
            throw new IllegalValueException(Food.MESSAGE_CONSTRAINTS);
        }
        if (!Food.isValidFoodAmount(foodAmount)) {
            throw new IllegalValueException(Food.MESSAGE_AMOUNT_CONSTRAINTS);
        }
        return new Food(foodName, foodAmount);
    }

}
