package seedu.address.ui;

import java.time.Duration;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * A UI Component that displays information about a {@code Slot} in a calendar view.
 */
public class CalendarBuffer extends UiPart<Region> {

    private static final String FXML = "CalendarBuffer.fxml";

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

    public CalendarBuffer(Duration duration) {
        super(FXML);
        slotPane.setPrefWidth(duration.toMinutes() * WIDTH_SCALING_FACTOR);
    }
}
