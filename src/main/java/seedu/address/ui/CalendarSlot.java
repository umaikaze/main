package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.slot.Slot;

/**
 * A UI Component that displays information about a {@code Slot} in a calendar view.
 */
public class CalendarSlot extends UiPart<Region> {

    private static final String FXML = "CalendarSlot.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public static final double WIDTH_SCALING_FACTOR = 2;

    @FXML
    private HBox slotPane;

    @FXML
    private Label id;

    @FXML
    private Label dateTime;

    @FXML
    private Label petName;

    public CalendarSlot(Slot slot, int displayedIndex) {
        super(FXML);
        slotPane.setPrefWidth(slot.getDuration().toMinutes() * WIDTH_SCALING_FACTOR);
        id.setText(displayedIndex + ". " + slot.getDate().getDayOfMonth() + "/" + slot.getDate().getMonthValue());
        dateTime.setText(slot.getTime() + " (+" + slot.getDuration().toMinutes() + ")");
        petName.setText(slot.getPet().getName().toString());
    }
}
