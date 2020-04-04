package clzzz.helper.ui.list;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import clzzz.helper.commons.util.DateTimeUtil;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Slot}.
 */
public class SlotCard extends UiPart<Region> {

    private static final String FXML = "list/SlotCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Slot slot;

    @FXML
    private HBox cardPane;
    @FXML
    private Label dateTime;
    @FXML
    private Label petName;
    @FXML
    private Label id;

    public SlotCard(Slot slot, int displayedIndex, List<Slot> allSlots) {
        super(FXML);
        this.slot = slot;

        String idText = String.format("%d. %s", displayedIndex, slot.hasConflict(allSlots) ? "[CONFLICT]" : "");
        String dateTimeText = String.format("%s, %s, %s - %s",
                slot.getDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US),
                slot.getDate().format(DateTimeUtil.DATE_FORMAT),
                slot.getTime(), slot.getEndTime());
        String petText = slot.getPet().getName().toString();

        id.setText(idText);
        dateTime.setText(dateTimeText);
        petName.setText(petText);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SlotCard)) {
            return false;
        }

        // state check
        SlotCard card = (SlotCard) other;
        return id.getText().equals(card.id.getText())
                && slot.equals(card.slot);
    }
}
