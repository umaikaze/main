package clzzz.helper.ui.calendar;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import clzzz.helper.commons.util.DateTimeUtil;

/**
 * A region of a calendar view that indicates the date.
 */
public class CalendarDate extends CalendarRegion {
    /**
     * Used to determine the width of this component.
     */
    public static final Duration DURATION = Duration.ofMinutes(60);

    private static final String FXML = "calendar/CalendarDate.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private Label day;

    @FXML
    private Label date;

    public CalendarDate(LocalDate dateToShow) {
        super(FXML, DURATION.toMinutes());

        String dayText = dateToShow.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US);
        String dateText = dateToShow.format(DateTimeUtil.DATE_FORMAT);

        day.setText(dayText);
        date.setText(dateText);
    }

    public static int getWidth() {
        return (int) Math.round(DURATION.toMinutes() * WIDTH_SCALING_FACTOR);
    }
}
