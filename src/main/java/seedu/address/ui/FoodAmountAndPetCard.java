package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.model.pet.FoodAmountAndPet;


/**
 * An UI component that displays information of a {@code FoodAmountAndPet}.
 */
public class FoodAmountAndPetCard extends UiPart<Region> {

    private static final String FXML = "FoodAmountAndPetCard.fxml";

    public final FoodAmountAndPet foodAmountAndPet;

    @FXML
    private HBox cardPane;
    @FXML
    private Label petName;
    @FXML
    private Label foodAmount;

    public FoodAmountAndPetCard(FoodAmountAndPet foodAmountAndPet) {
        super(FXML);
        requireNonNull(foodAmountAndPet);
        this.foodAmountAndPet = foodAmountAndPet;

        petName.setText(foodAmountAndPet.getPet().getName().toString());
        foodAmount.setText(foodAmountAndPet.getFoodAmount().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FoodAmountAndPetCard)) {
            return false;
        }

        // state check
        FoodAmountAndPetCard card = (FoodAmountAndPetCard) other;
        return foodAmountAndPet.equals(card.foodAmountAndPet);
    }
}
