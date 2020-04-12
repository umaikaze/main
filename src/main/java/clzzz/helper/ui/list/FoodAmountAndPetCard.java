package clzzz.helper.ui.list;

import static java.util.Objects.requireNonNull;

import clzzz.helper.model.foodcollection.FoodAmountAndPet;
import clzzz.helper.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;


/**
 * An UI component that displays information of a {@code FoodAmountAndPet}.
 */
public class FoodAmountAndPetCard extends UiPart<Region> {

    private static final String FXML = "list/FoodAmountAndPetCard.fxml";

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

        petName.setText(foodAmountAndPet.getPetName());
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
