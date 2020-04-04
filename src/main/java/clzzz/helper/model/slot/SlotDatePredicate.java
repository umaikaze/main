package clzzz.helper.model.slot;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Slot}'s {@code DateTime} matches the given date.
 */

public class SlotDatePredicate implements Predicate<Slot> {
    private final List<LocalDate> dates;

    public SlotDatePredicate(List<LocalDate> dates) {
        this.dates = dates;
    }

    @Override
    public boolean test(Slot slot) {
        for (LocalDate date : dates) {
            if (date.isEqual(slot.getDate())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SlotDatePredicate // instanceof handles nulls
                && dates.equals(((SlotDatePredicate) other).dates)); // state check
    }
}
