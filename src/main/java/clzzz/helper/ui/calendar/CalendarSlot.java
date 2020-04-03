package clzzz.helper.ui.calendar;

import java.time.format.TextStyle;
import java.util.Locale;

import clzzz.helper.commons.util.DateTimeUtil;
import clzzz.helper.model.slot.Slot;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

/**
 * A region of a calendar view that represents a single slot.
 */
public class CalendarSlot extends CalendarRegion {

    private static final String FXML = "calendar/CalendarSlot.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private String idText;
    private String dateText;
    private String timeText;
    private String petText;

    @FXML
    private Label id;

    @FXML
    private Label time;

    @FXML
    private Label petName;

    public CalendarSlot(Slot slot, int displayedIndex) {
        super(FXML, slot.getDuration().toMinutes());

        this.idText = String.format("%d.", displayedIndex);
        this.dateText = String.format("%s, %s",
                slot.getDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US),
                slot.getDate().format(DateTimeUtil.DATE_FORMAT));
        this.timeText = String.format("%s - %s", slot.getTime(), slot.getEndTime());
        this.petText = slot.getPet().getName().toString();

        setText();
    }

    private void setText() {
        id.setText(idText);
        time.setText(timeText);
        petName.setText(petText);
    }

    /**
     * Creates a tooltip containing information about this slot.
     */
    public Tooltip createTooltip() {
        String pattern = "%s\n"
                + "Date: %s\n"
                + "Time: %s\n"
                + "Pet: %s";
        String tooltipText = String.format(pattern,
                idText, dateText, timeText, petText);
        Tooltip tooltip = new Tooltip(tooltipText);
        tooltip.getStyleClass().add("tooltip-slot");
        tooltip.setShowDuration(javafx.util.Duration.seconds(30));
        return tooltip;
    }
}
