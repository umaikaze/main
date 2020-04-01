package seedu.address.ui.calendar;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * A UI Component that that constitutes a region of a calendar view.
 */
public abstract class CalendarRegion extends UiPart<Region> {

    public static final double WIDTH_SCALING_FACTOR = 2;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    protected HBox slotPane;

    public CalendarRegion(String fxml, long minutes) {
        super(fxml);
        slotPane.setPrefWidth(minutes * WIDTH_SCALING_FACTOR);
    }
}
