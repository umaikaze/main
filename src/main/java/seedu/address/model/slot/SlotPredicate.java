package seedu.address.model.slot;

import java.util.function.Predicate;

/**
 * Tests that a {@code Slot}'s {@code Name} or {@code LocalDateTime} matches any of the keywords given.
 */
public interface SlotPredicate extends Predicate<Slot> {

    @Override
    boolean test(Slot slot);

    @Override
    boolean equals(Object other);
}
