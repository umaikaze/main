package clzzz.helper.ui.calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import clzzz.helper.model.slot.Slot;
import clzzz.helper.model.slot.SlotDuration;
import clzzz.helper.ui.UiPart;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * Panel containing a calendar view of the schedule.
 */
public class CalendarPanel extends UiPart<Region> {

    public static final String NO_SLOTS = "No slots!";

    private static final String FXML = "calendar/CalendarPanel.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final ObservableList<Slot> allSlots;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    public CalendarPanel(ObservableList<Slot> allSlots) {
        super(FXML);
        this.allSlots = allSlots;
        ListChangeListener<Slot> reconstructOnChange = (Change<? extends Slot> c) -> {
            while (c.next()) {
                construct();
            }
        };
        allSlots.addListener(reconstructOnChange);
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
        LocalTime earliestTime = allSlots.stream()
                .map(slot -> slot.getTime())
                .reduce((time1, time2) -> (time1.isBefore(time2) ? time1 : time2))
                .get();
        construct(earliestTime);
    }

    /**
     * Fills the grid pane with the slots, given the earliest time of all slots.
     */
    private void construct(LocalTime earliestTime) {
        int rowIndex = 0;
        Holding h = new Holding();
        Slot firstSlot = allSlots.get(0);
        flushOutDate(rowIndex, firstSlot.getDate());
        flushOutBuffer(earliestTime, rowIndex, earliestTime, firstSlot.getTime());
        for (int slotIndex = 0; slotIndex < allSlots.size(); slotIndex++) {
            Slot currSlot = allSlots.get(slotIndex);
            if (h.size() > 0 && !h.overlaps(currSlot)) {
                flushOutHolding(earliestTime, rowIndex, h, slotIndex);
                if (h.sameDay(currSlot)) {
                    flushOutBuffer(earliestTime, rowIndex, h.end(), currSlot.getTime());
                } else {
                    rowIndex++;
                    flushOutDate(rowIndex, currSlot.getDate());
                    flushOutBuffer(earliestTime, rowIndex, earliestTime, currSlot.getTime());
                }
                h.reset();
            }
            h.add(currSlot);
        }
        flushOutHolding(earliestTime, rowIndex, h, allSlots.size());
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
     * Flushes out and renders a {@code CalendarBuffer}.
     */
    private void flushOutBuffer(LocalTime earliestTime, int rowIndex,
            LocalTime bufferStartTime, LocalTime bufferEndTime) {
        if (!bufferEndTime.isAfter(bufferStartTime)) {
            return;
        }
        int colIndex = getColIndex(earliestTime, bufferStartTime);
        int colSpan = getColSpan(bufferStartTime, bufferEndTime);
        CalendarBuffer calendarBuffer = new CalendarBuffer(bufferStartTime, bufferEndTime);
        gridPane.add(calendarBuffer.getRoot(), colIndex, rowIndex, colSpan, 1);
    }

    /**
     * Flushes out and renders a {@code CalendarDate}.
     */
    private void flushOutDate(int rowIndex, LocalDate date) {
        int colIndex = 0;
        int colSpan = getColSpan(CalendarDate.DURATION);
        CalendarDate calendarDate = new CalendarDate(date);
        gridPane.add(calendarDate.getRoot(), colIndex, rowIndex, colSpan, 1);
    }

    /**
     * Flushes out and renders all slots in holding.
     */
    private void flushOutHolding(LocalTime earliestTime, int rowIndex, Holding h, int lastDisplayedIndex) {
        if (h.size() == 1) {
            flushOutHoldingSingle(earliestTime, rowIndex, h.slots.get(0), lastDisplayedIndex);
        } else {
            flushOutHoldingMultiple(earliestTime, rowIndex, h, lastDisplayedIndex);
        }
    }

    /**
     * Flushes out and renders a {@code CalendarSlot}.
     */
    private void flushOutHoldingSingle(LocalTime earliestTime, int rowIndex, Slot slot, int lastDisplayedIndex) {
        int colIndex = getColIndex(earliestTime, slot.getTime());
        int colSpan = getColSpan(slot.getDuration());
        CalendarSlot calendarSlot = new CalendarSlot(slot, lastDisplayedIndex);
        Tooltip.install(calendarSlot.getRoot(), calendarSlot.createTooltip());
        gridPane.add(calendarSlot.getRoot(), colIndex, rowIndex, colSpan, 1);
    }

    /**
     * Flushes out and renders a {@code CalendarConflict}.
     */
    private void flushOutHoldingMultiple(LocalTime earliestTime, int rowIndex, Holding h, int lastDisplayedIndex) {
        int colIndex = getColIndex(earliestTime, h.start());
        int colSpan = getColSpan(h.start(), h.end());
        CalendarConflict calendarConflict = new CalendarConflict(h.all(), h.start(), h.end(), lastDisplayedIndex);
        Tooltip.install(calendarConflict.getRoot(), calendarConflict.createTooltip());
        gridPane.add(calendarConflict.getRoot(), colIndex, rowIndex, colSpan, 1);
    }

    private int getColIndex(LocalTime earliestTime, LocalTime slotTime) {
        assert slotTime.isBefore(earliestTime) : "Given slot time is earlier than the earliest time!";
        int delta = 0;
        if (earliestTime.isBefore(slotTime)) {
            delta = Math.toIntExact(SlotDuration.between(earliestTime, slotTime).toMinutes());
        }
        return CalendarDate.getWidth() + delta;
    }

    private int getColSpan(SlotDuration duration) {
        return Math.toIntExact(duration.toMinutes());
    }

    private int getColSpan(LocalTime startTime, LocalTime endTime) {
        return getColSpan(SlotDuration.between(startTime, endTime));
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
