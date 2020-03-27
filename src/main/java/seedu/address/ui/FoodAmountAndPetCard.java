package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.general.CommandResult;
import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.model.pet.Food;
import seedu.address.model.pet.FoodAmountAndPet;
import seedu.address.model.pet.Pet;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * An UI component that displays information of a {@code Pet}.
 */


public class FoodAmountAndPetCard extends UiPart<Region> {

    private static final String FXML = "FoodAmountAndPetCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

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