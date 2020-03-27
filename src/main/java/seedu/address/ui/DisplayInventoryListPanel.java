package seedu.address.ui;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.pet.FoodCollection;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;

/**
 * Panel containing the list of display items.
 */

/*
public class DisplayInventoryListPanel extends UiPart<Region> {
    private static final String FXML = "DisplayListInventoryPanel.fxml";

    @FXML
    private ListView<DisplayItem> displayInventoryListView;
    private ListView<FoodCollectionSummaryCard> foodCollectionSummary;

    public DisplayInventoryListPanel(ObservableList<DisplayItem> displayList) {
        super(FXML);
        displayInventoryListView.setItems(displayList);
        displayInventoryListView.setCellFactory(listView -> new DisplayInventoryListPanelListViewCell());
    }*/

    /**
     * Changes the backing list of display items to {@code newDisplayList}.
     */
    /*
    public final void updateWith(ObservableList<DisplayItem> newDisplayList) {
        displayInventoryListView.setItems(newDisplayList);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code DisplayItem}.
     */
    /*
    class DisplayInventoryListPanelListViewCell extends ListCell<DisplayItem> {
        @Override
        protected void updateItem(DisplayItem item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FoodCollectionSummaryCard((FoodCollection) item, getIndex() + 1).getRoot());
                }
            }
        }
    }
}
*/