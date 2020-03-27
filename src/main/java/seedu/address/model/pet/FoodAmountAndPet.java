package seedu.address.model.pet;

import seedu.address.ui.DisplayItem;
import seedu.address.ui.DisplaySystemType;

import static java.util.Objects.requireNonNull;

public class FoodAmountAndPet implements DisplayItem {
    private final Integer foodAmount;
    private final Pet pet;

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

    public Integer getFoodAmount() {
        return foodAmount;
    }

    public Pet getPet() {
        return pet;
    }
}
