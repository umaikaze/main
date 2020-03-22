package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.pet.FoodCollection;

/**
 * An UI component that displays information of a {@code Pet}.
 */

public class FoodCollectionCard extends  UiPart<Region> {


/**
 * An UI component that displays information of a {@code Pet}.
 */

    private static final String FXML = "FoodCollectionCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     */
    public final FoodCollection foodCollection;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label amount;


    public FoodCollectionCard(FoodCollection foodCollection, int displayedIndex) {
        super(FXML);
        this.foodCollection = foodCollection;
        id.setText(displayedIndex + ". ");
        name.setText(foodCollection.getFoodCollectionName());
        amount.setText(foodCollection.getFoodCollectionAmount().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FoodCollectionCard)) {
            return false;
        }

        // state check
        FoodCollectionCard card = (FoodCollectionCard) other;
        return id.getText().equals(card.id.getText())
                && foodCollection.equals(card.foodCollection);
    }
}
