package clzzz.helper.ui.calendar;

import java.time.LocalTime;

import clzzz.helper.model.slot.SlotDuration;

/**
 * A region of a calendar view that acts a buffer between other regions, for padding purposes.
 */
public class CalendarBuffer extends CalendarRegion {

    private static final String FXML = "calendar/CalendarBuffer.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public CalendarBuffer(LocalTime start, LocalTime end) {
        super(FXML, SlotDuration.between(start, end).toMinutes());
    }
}
