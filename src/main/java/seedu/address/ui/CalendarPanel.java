package seedu.address.ui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.model.slot.Slot;

/**
 * Panel containing a calendar view of the schedule.
 */
public class CalendarPanel extends UiPart<Region> {

    public static final String NO_SLOTS = "No slots!";

    private static final String FXML = "CalendarPanel.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final ObservableList<Slot> allSlots;
    private LocalTime earliestTime;
    private int smallestTimeInterval;
    private int rowIndex;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    public CalendarPanel(ObservableList<Slot> allSlots) {
        super(FXML);
        this.allSlots = allSlots;
        ListChangeListener<Slot> reconstructOnChange = (Change<? extends Slot> c) -> {
            while (c.next()) {
                if (!c.getList().isEmpty()) {
                    init();
                }
                construct();
            }
        };
        allSlots.addListener(reconstructOnChange);
        if (!allSlots.isEmpty()) {
            init();
        }
    }

    /**
     * Initialize key variables needed for constructing the calendar.
     */
    private void init() {
        earliestTime = allSlots.stream()
                .map(slot -> slot.getTime())
                .reduce((time1, time2) -> (time1.isBefore(time2) ? time1 : time2))
                .get();
        smallestTimeInterval = Math.toIntExact(allSlots.stream()
                .map(slot -> slot.getDuration().toMinutes())
                .reduce((a, b) -> gcd(a, b))
                .orElse(1L));
        rowIndex = 0;
    }

    /**
     * Fills the grid pane with the slots.
     */
    public void construct() {
        clearAll();
        if (allSlots.isEmpty()) {
            Label label = new Label(NO_SLOTS);
            label.getStyleClass().add("calendar_big_label");
            gridPane.add(label, 0, 0);
            return;
        }
        Holding h = new Holding();
        Slot firstSlot = allSlots.get(0);
        flushOutDate(firstSlot.getDate());
        flushOutBuffer(earliestTime, firstSlot.getTime());
        for (int slotIndex = 0; slotIndex < allSlots.size(); slotIndex++) {
            Slot currSlot = allSlots.get(slotIndex);
            if (h.size() == 0 || h.overlaps(currSlot)) {
                h.add(currSlot);
                continue;
            }
            flushOutHolding(h, slotIndex);
            if (h.sameDay(currSlot)) {
                flushOutBuffer(h.end(), currSlot.getTime());
                h.reset();
                h.add(currSlot);
                continue;
            }
            h.reset();
            rowIndex++;
            flushOutDate(currSlot.getDate());
            if (currSlot.getTime().isAfter(earliestTime)) {
                flushOutBuffer(earliestTime, currSlot.getTime());
            }
            h.add(currSlot);
        }
        flushOutHolding(h, allSlots.size());
    }

    /**
     * Clears the previously constructed calendar.
     */
    private void clearAll() {
        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();
    }

    /**
     * Flushes out and renders a {@code CalendaBuffer}.
     */
    private void flushOutBuffer(LocalTime bufferStartTime, LocalTime bufferEndTime) {
        if (!bufferEndTime.isAfter(bufferStartTime)) {
            return;
        }
        int colIndex = getColIndex(bufferStartTime);
        int colSpan = getColSpan(Duration.between(bufferStartTime, bufferEndTime));
        CalendarBuffer calendarBuffer = new CalendarBuffer(bufferStartTime, bufferEndTime);
        gridPane.add(calendarBuffer.getRoot(), colIndex, rowIndex, colSpan, 1);
    }

    /**
     * Flushes out and renders a {@code CalendaDate}.
     */
    private void flushOutDate(LocalDate date) {
        int colIndex = 0;
        int colSpan = getColSpan(CalendarDate.DURATION);
        CalendarDate calendarDate = new CalendarDate(date);
        gridPane.add(calendarDate.getRoot(), colIndex, rowIndex, colSpan, 1);
    }

    /**
     * Flushes out and renders all slots in holding.
     */

    private void flushOutHolding(Holding h, int lastDisplayedIndex) {
        if (h.size() == 1) {
            flushOutHoldingSingle(h.slots.get(0), lastDisplayedIndex);
        } else {
            flushOutHoldingMultiple(h, lastDisplayedIndex);
        }
    }

    /**
     * Flushes out and renders a {@code CalendarSlot}.
     */
    private void flushOutHoldingSingle(Slot slot, int lastDisplayedIndex) {
        int colIndex = getColIndex(slot.getTime());
        int colSpan = getColSpan(slot.getDuration());
        CalendarSlot calendarSlot = new CalendarSlot(slot, lastDisplayedIndex);
        Tooltip.install(calendarSlot.getRoot(), calendarSlot.createTooltip());
        gridPane.add(calendarSlot.getRoot(), colIndex, rowIndex, colSpan, 1);
    }

    /**
     * Flushes out and renders a {@code CalendarConflict}.
     */

    private void flushOutHoldingMultiple(Holding h, int lastDisplayedIndex) {
        int colIndex = getColIndex(h.start());
        int colSpan = getColSpan(h.start(), h.end());
        CalendarConflict calendarConflict = new CalendarConflict(h.all(), h.start(), h.end(), lastDisplayedIndex);
        Tooltip.install(calendarConflict.getRoot(), calendarConflict.createTooltip());
        gridPane.add(calendarConflict.getRoot(), colIndex, rowIndex, colSpan, 1);
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private int getColIndex(LocalTime slotTime) {
        assert slotTime.isBefore(earliestTime) : "Given slot time is earlier than the earliest time!";
        return CalendarDate.getWidth()
                + Math.toIntExact(Duration.between(earliestTime, slotTime).toMinutes()) / smallestTimeInterval;
    }

    private int getColSpan(Duration duration) {
        return Math.toIntExact(duration.toMinutes()) / smallestTimeInterval;
    }

    private int getColSpan(LocalTime startTime, LocalTime endTime) {
        return getColSpan(Duration.between(startTime, endTime));
    }

    /**
     * Encapsulates the list of all currently conflicted slots in holding.
     */
    private class Holding {
        private List<Slot> slots = new ArrayList<>();
        private LocalTime startTime = LocalTime.MAX;
        private LocalTime endTime = LocalTime.MIN;

        Holding() {}

        void add(Slot slot) {
            slots.add(slot);
            startTime = startTime.isBefore(slot.getTime()) ? startTime : slot.getTime();
            endTime = endTime.isAfter(slot.getEndTime()) ? endTime : slot.getEndTime();
        }

        void reset() {
            slots = new ArrayList<>();
            startTime = LocalTime.MAX;
            endTime = LocalTime.MIN;
        }

        boolean overlaps(Slot slot) {
            return slots.stream().anyMatch(s -> slot.isInConflictWith(s));
        }

        boolean sameDay(Slot slot) {
            return slots.get(0).isSameDate(slot);
        }

        int size() {
            return slots.size();
        }

        List<Slot> all() {
            return slots;
        }

        LocalTime start() {
            return startTime;
        }

        LocalTime end() {
            return endTime;
        }

        @Override
        public String toString() {
            return slots.toString();
        }
    }
}
