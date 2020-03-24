package seedu.address.model.slot;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Tests that a {@code Slot}'s {@code DateTime} matches the given date.
 */

public class SlotDatePredicate implements Predicate<Slot> {
    private final LocalDate date;

    public SlotDatePredicate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean test(Slot slot) {
        return date.isEqual(slot.getDate());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SlotDatePredicate // instanceof handles nulls
                && date.equals(((SlotDatePredicate) other).date)); // state check
    }
}
