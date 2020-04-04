package clzzz.helper.model.slot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Slot} is in conflict with any other slots.
 */
public class SlotConflictPredicate implements Predicate<Slot> {
    private final List<Slot> allSlots;

    public SlotConflictPredicate(List<Slot> allSlots) {
        this.allSlots = new ArrayList<>(allSlots);
    }

    @Override
    public boolean test(Slot slot) {
        return slot.hasConflict(allSlots);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SlotConflictPredicate // instanceof handles nulls
                && allSlots.equals(((SlotConflictPredicate) other).allSlots)); // state check
    }
}
