package clzzz.helper.ui.list;

import clzzz.helper.commons.util.CollectionUtil;
import clzzz.helper.model.foodcollection.FoodCollection;
import clzzz.helper.ui.UiPart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code FoodCollection}.
 */

public class FoodCollectionCard extends UiPart<Region> {
    private static final String FXML = "list/FoodCollectionCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     */
    public final FoodCollection foodCollection;
    public final MouseClickedHandler mouseClickedHandler;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label amount;


    public FoodCollectionCard(FoodCollection foodCollection, int displayedIndex,
                              MouseClickedHandler mouseClickedHandler) {
        super(FXML);
        this.foodCollection = foodCollection;
        this.mouseClickedHandler = mouseClickedHandler;
        id.setText(displayedIndex + ". ");
        name.setText(foodCollection.getName());
        amount.setText(foodCollection.getAmount().toString());
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

    /**
     * Handler of a mouth clicking event. Triggers the displaying of amount breakdown of this food collection.
     */
    @FXML
    private void handleMouthClicked() {
        ObservableList<DisplayItem> displayItems = CollectionUtil.map(foodCollection.getUnmodifiablePets(), pet -> pet);
        mouseClickedHandler.handle(displayItems);
    }

    /**
     * Represents a function that can handle a mouth click.
     */
    @FunctionalInterface
    public interface MouseClickedHandler {

        void handle(ObservableList<DisplayItem> foodAmountAndPets);
    }
}
