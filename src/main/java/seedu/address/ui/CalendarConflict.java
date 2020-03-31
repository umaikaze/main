package seedu.address.ui;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import seedu.address.model.slot.Slot;

/**
 * A region of a calendar view that represents a multiple conflicted slots.
 */
public class CalendarConflict extends CalendarRegion {

    public static final String CONFLICT_MESSAGE =
                "Do `conflicts` to view details about each individual conflicted slots";

    private static final String FXML = "CalendarConflict.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private Label ids;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Label petName;

    public CalendarConflict(List<Slot> conflictSlots, LocalTime start, LocalTime end, int lastDisplayedIndex) {
        super(FXML, Duration.between(start, end).toMinutes());
        Slot lastSlot = conflictSlots.get(conflictSlots.size() - 1);

        String idsString = IntStream.rangeClosed(lastDisplayedIndex - conflictSlots.size() + 1, lastDisplayedIndex)
                .mapToObj(i -> Integer.toString(i))
                .reduce((a, b) -> a + ", " + b).orElseThrow();
        String idText = String.format("%s.", idsString);
        String dateText = String.format("%s, %s/%s/%s",
                lastSlot.getDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US),
                lastSlot.getDate().getDayOfMonth(), lastSlot.getDate().getMonthValue(), lastSlot.getDate().getYear());
        String timeText = String.format("%s - %s", start, end);
        String petText = conflictSlots.stream()
                .map(slot -> slot.getPet().getName().toString())
                .collect(Collectors.toSet())
                .toString();

        ids.setText(idText);
        date.setText(dateText);
        time.setText(timeText);
        petName.setText(petText);
    }

    public Tooltip createTooltip() {
        String pattern = "%s\n"
                + "Date: %s\n"
                + "Time period affected: %s\n"
                + "Affected pets: %s\n"
                + "%s";
        String tooltipText = String.format(pattern,
                ids.getText(), date.getText(), time.getText(), petName.getText(), CONFLICT_MESSAGE);
        Tooltip tooltip = new Tooltip(tooltipText);
        tooltip.getStyleClass().add("tooltip-conflict");
        tooltip.setShowDuration(javafx.util.Duration.seconds(30));
        return tooltip;
    }
}
