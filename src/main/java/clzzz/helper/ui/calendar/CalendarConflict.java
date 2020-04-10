package clzzz.helper.ui.calendar;

import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import clzzz.helper.commons.util.DateTimeUtil;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.model.slot.SlotDuration;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

/**
 * A region of a calendar view that represents a multiple conflicted slots.
 */
public class CalendarConflict extends CalendarRegion {

    public static final String CONFLICT_MESSAGE =
                "Do `conflicts` to view details about each individual conflicted slots";

    private static final String FXML = "calendar/CalendarConflict.fxml";

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
    private Label ids;

    @FXML
    private Label time;

    @FXML
    private Label petName;

    public CalendarConflict(List<Slot> conflictSlots, LocalTime start, LocalTime end, int lastDisplayedIndex) {
        super(FXML, SlotDuration.between(start, end).toMinutes());
        Slot lastSlot = conflictSlots.get(conflictSlots.size() - 1);

        String idsString = IntStream.rangeClosed(lastDisplayedIndex - conflictSlots.size() + 1, lastDisplayedIndex)
                .mapToObj(i -> Integer.toString(i))
                .reduce((a, b) -> a + ", " + b).orElseThrow();
        this.idText = String.format("%s.", idsString);
        this.dateText = String.format("%s, %s",
                lastSlot.getDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US),
                lastSlot.getDate().format(DateTimeUtil.DATE_FORMAT));
        this.timeText = String.format("%s - %s", start, end);
        this.petText = conflictSlots.stream()
                .map(slot -> slot.getPet().getName().toString())
                .collect(Collectors.toSet())
                .toString();

        setText();
    }

    private void setText() {
        ids.setText(idText);
        time.setText(timeText);
        petName.setText(petText);
    }

    /**
     * Creates a tooltip containing information about the conflicted slots.
     */
    public Tooltip createTooltip() {
        String pattern = "%s\n"
                + "Date: %s\n"
                + "Time period affected: %s\n"
                + "Affected pets: %s\n"
                + "%s";
        String tooltipText = String.format(pattern,
                idText, dateText, timeText, petText, CONFLICT_MESSAGE);
        Tooltip tooltip = new Tooltip(tooltipText);
        tooltip.getStyleClass().add("tooltip-conflict");
        tooltip.setShowDuration(javafx.util.Duration.seconds(30));
        return tooltip;
    }
}
