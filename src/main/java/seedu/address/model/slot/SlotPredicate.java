package seedu.address.model.slot;

import java.util.function.Predicate;

/**
 * Tests that a {@code Slot}'s {@code Name} or {@code LocalDateTime} matches any of the keywords given.
 */
public abstract class SlotPredicate implements Predicate<Slot> {

    public abstract boolean test(Slot slot);

    public abstract boolean equals(Object other);
}
