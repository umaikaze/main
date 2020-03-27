package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.pet.FoodCollection;
import seedu.address.model.pet.Pet;

/**
 * An UI component that displays information of a {@code Pet}.
 */

/*
public class FoodCollectionSummaryCard extends UiPart<Region> {

    private static final String FXML = "FoodCollectionSummaryCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
/*
    public final Pet pet;
    public final FoodCollection foodCollection;


    @FXML
    private HBox cardPane;
    @FXML
    private Label petName;
    private Label foodAmount;

    public FoodCollectionSummaryCard(Pet pet, FoodCollection foodCollection) {
        super(FXML);
        this.pet = pet;
        this.foodCollection = foodCollection;
        petName.setText(pet.getName().toString());
        foodAmount.setText(foodCollection.getAmount(pet));

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PetCard)) {
            return false;
        }

        // state check
        FoodCollectionSummaryCard card = (FoodCollectionSummaryCard) other;
        return foodCollection.equals((card.foodCollection))
                && pet.equals(card.pet);
    }
}
*/