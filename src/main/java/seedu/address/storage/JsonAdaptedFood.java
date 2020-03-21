package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
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
    public JsonAdaptedFood(String food) {
        String[] foodDetails = food.split(":");
        this.foodName = foodDetails[0];
        this.foodAmount = Integer.valueOf(foodDetails[1]);
    }

    /**
     * Converts a given {@code Food} into this class for Jackson use.
     */
    public JsonAdaptedFood(Food source) {
        foodName = source.foodName;
        foodAmount = source.foodAmount;
    }

    @JsonValue
    public String getFood() {
        return foodName + ":" + foodAmount;
    }

    /**
     * Converts this Jackson-friendly adapted food object into the model's {@code Food} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted food.
     */
    public Food toModelType() throws IllegalValueException {
        if (!Food.isValidFoodName(foodName)) {
            throw new IllegalValueException(Food.MESSAGE_NAME_CONSTRAINTS);
        }
        if (!Food.isValidFoodAmount(foodAmount)) {
            throw new IllegalValueException(Food.MESSAGE_AMOUNT_CONSTRAINTS);
        }
        return new Food(foodName, foodAmount);
    }

}
