package clzzz.helper.ui.list;

import java.util.List;
import java.util.stream.Collectors;

import clzzz.helper.model.foodcollection.FoodAmountAndPet;
import clzzz.helper.model.foodcollection.FoodCollection;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.ui.UiPart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Panel containing the list of display items.
 */
public class DisplayListPanel extends UiPart<Region> {
    private static final String FXML = "list/DisplayListPanel.fxml";
    private static final ObservableList<DisplayItem> EMPTY_DISPLAY_ITEM_LIST = FXCollections.observableArrayList();

    @FXML
    private ListView<DisplayItem> displayListView;
    @FXML
    private ListView<DisplayItem> displayInformationView;
    @FXML
    private VBox displayInformationViewContainer;

    public DisplayListPanel(ObservableList<DisplayItem> displayList) {
        super(FXML);
        displayListView.setItems(displayList);
        displayListView.setCellFactory(listView -> new DisplayListViewCell());
        displayInformationView.setCellFactory(listView -> new DisplayListViewCell());
    }

    /**
     * Changes the backing list of display items to {@code newDisplayList}.
     */
    public final void updateWith(ObservableList<DisplayItem> newDisplayList) {
        displayListView.setItems(newDisplayList);
        displayInformationView.setItems(EMPTY_DISPLAY_ITEM_LIST);
    }

    /**
     * Collapses the displayInformationView so that the displayListView occupies the entire screen.
     */
    public void collapseInformationView() {
        displayInformationViewContainer.setPrefWidth(0);
        displayInformationViewContainer.setMinWidth(0);
        HBox.setHgrow(displayInformationViewContainer, Priority.NEVER);
        displayInformationView.setPrefWidth(0);
        displayInformationView.setMinWidth(0);
        HBox.setHgrow(displayInformationView, Priority.NEVER);
        displayInformationView.getStyleClass().clear();
    }

    /**
     * Expands the displayInformationView for the display of inventory system.
     */
    public void expandInformationView() {
        displayInformationViewContainer.setPrefWidth(displayListView.getPrefWidth());
        displayInformationViewContainer.setMinWidth(displayListView.getPrefWidth());
        HBox.setHgrow(displayInformationViewContainer, Priority.ALWAYS);
        displayInformationView.setPrefWidth(displayListView.getPrefWidth());
        displayInformationView.setMinWidth(displayListView.getMinWidth());
        HBox.setHgrow(displayInformationView, Priority.ALWAYS);
        displayInformationView.getStyleClass().add("pane-with-border");
    }

    /**
     * Displays the amount breakdown of the food collection being clicked on through the list provided.
     */
    public final void handleClickOnList(ObservableList<DisplayItem> foodAmountAndPets) {
        displayInformationView.setItems(foodAmountAndPets);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code DisplayItem}.
     */
    class DisplayListViewCell extends ListCell<DisplayItem> {
        @Override
        protected void updateItem(DisplayItem item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                switch (item.getDisplaySystemType()) {
                case PETS:
                    setGraphic(new PetCard((Pet) item, getIndex() + 1).getRoot());
                    break;
                case SCHEDULE:
                    List<Slot> allSlots = displayListView.getItems()
                            .stream()
                            .map(slot -> (Slot) slot)
                            .collect(Collectors.toList());
                    setGraphic(new SlotCard((Slot) item, getIndex() + 1, allSlots).getRoot());
                    break;
                case INVENTORY:
                    setGraphic(new FoodCollectionCard((FoodCollection) item,
                            getIndex() + 1, DisplayListPanel.this::handleClickOnList).getRoot());
                    break;
                case FOOD_AMOUNT_AND_PET:
                    FoodAmountAndPet foodAmountAndPet = (FoodAmountAndPet) item;
                    setGraphic(new FoodAmountAndPetCard(foodAmountAndPet).getRoot());
                    break;
                default:
                    setGraphic(null);
                    setText(null);
                }
            }
        }
    }
}
