package seedu.address.model.slot;

import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * Tests that a {@code Slot}'s {@code DateTime} matches the given date.
 */
public class SlotDatePredicate implements Predicate<Slot> {
    private final LocalDateTime dateTime;

    public SlotDatePredicate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean test(Slot slot) {
        return dateTime.toLocalDate().isEqual(slot.getDate());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SlotDatePredicate // instanceof handles nulls
                && dateTime.equals(((SlotDatePredicate) other).dateTime)); // state check
    }
}
