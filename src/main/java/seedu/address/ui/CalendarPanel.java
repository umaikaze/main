package seedu.address.ui;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.model.slot.Slot;

/**
 * Panel containing a calendar view of the schedule.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private List<Slot> allSlots;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    public CalendarPanel(ObservableList<Slot> allSlots) {
        super(FXML);
        this.allSlots = allSlots.stream().collect(Collectors.toList());
        construct();
    }

    /**
     * Changes the backing list of slots to {@code newDisplayList}.
     */
    public void updateWith(ObservableList<Slot> newAllSlots) {
        this.allSlots = newAllSlots.stream().collect(Collectors.toList());
    }

    /**
     * Fills the grid pane with the slots.
     */
    public void construct() {
        //TODO: actually construct a calendar
        gridPane.getChildren().clear();
        gridPane.add(new Label("in calendar! there's " + allSlots.size() + " in total"), 0, 0);
    }
}
