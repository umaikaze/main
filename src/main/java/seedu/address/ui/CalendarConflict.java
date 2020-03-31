package seedu.address.ui;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.slot.Slot;

/**
 * A UI Component that displays information about a {@code Slot} in a calendar view.
 */
public class CalendarConflict extends UiPart<Region> {

    public static final double WIDTH_SCALING_FACTOR = 2;

    private static final String FXML = "CalendarConflict.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox slotPane;

    @FXML
    private Label ids;

    @FXML
    private Label dateTime;

    @FXML
    private Label petName;

    public CalendarConflict(List<Slot> conflictSlots, int lastDisplayedIndex) {
        super(FXML);
        LocalTime start = conflictSlots.get(0).getTime();
        //TODO: this way of getting end is really hacky....
        LocalTime end = conflictSlots.stream()
                .map(slot -> slot.getEndTime())
                .sorted()
                .reduce((a, b) -> b)
                .orElseThrow();
        int totalDuration = Math.toIntExact(Duration.between(start, end).toMinutes());
        slotPane.setPrefWidth(totalDuration * WIDTH_SCALING_FACTOR);
        //TODO: all the info displayed below are wrong (based on latest slot only)
        String idsString = IntStream.rangeClosed(lastDisplayedIndex - conflictSlots.size() + 1, lastDisplayedIndex)
                .mapToObj(i -> Integer.toString(i))
                .reduce((a, b) -> a + ", " + b).orElseThrow();
        ids.setText(idsString + ". " + conflictSlots.get(0).getDateTime().toLocalDate());
        dateTime.setText(start + " - " + end);
        Set<String> petNames = conflictSlots.stream()
                .map(slot -> slot.getPet().getName().toString())
                .collect(Collectors.toSet());
        petName.setText(petNames.toString());
    }
}
