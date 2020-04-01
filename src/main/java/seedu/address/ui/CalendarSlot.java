package seedu.address.ui;

import java.time.format.TextStyle;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.model.slot.Slot;

/**
 * A region of a calendar view that represents a single slot.
 */
public class CalendarSlot extends CalendarRegion {

    private static final String FXML = "CalendarSlot.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private Label id;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Label petName;

    public CalendarSlot(Slot slot, int displayedIndex) {
        super(FXML, slot.getDuration().toMinutes());

        String idText = String.format("%d.", displayedIndex);
        String dateText = String.format("%s, %s",
                slot.getDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US),
                slot.getDate().format(DateTimeUtil.DATE_FORMAT));
        String timeText = String.format("%s - %s", slot.getTime(), slot.getEndTime());
        String petText = slot.getPet().getName().toString();

        id.setText(idText);
        date.setText(dateText);
        time.setText(timeText);
        petName.setText(petText);
    }

    public Tooltip createTooltip() {
        String pattern = "%s\n"
                + "Date: %s\n"
                + "Time: %s\n"
                + "Pet: %s";
        String tooltipText = String.format(pattern,
                id.getText(), date.getText(), time.getText(), petName.getText());
        Tooltip tooltip = new Tooltip(tooltipText);
        tooltip.getStyleClass().add("tooltip-slot");
        tooltip.setShowDuration(javafx.util.Duration.seconds(30));
        return tooltip;
    }
}
