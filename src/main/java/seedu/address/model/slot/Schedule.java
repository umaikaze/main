package seedu.address.model.slot;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.slot.exceptions.SlotNotFoundException;

/**
 * A collection of slots that does not allow nulls.
 *
 * Unlike {@link package seedu.address.model.pet.UniquePetList}, a schedule is allowed to contain duplicate slots.
 *
 * Supports a minimal set of list operations.
 */
public class Schedule {

    private final ObservableList<Slot> internalList = FXCollections.observableArrayList();
    private final ObservableList<Slot> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the schedule contains an identical slot as the given argument.
     */
    public boolean contains(Slot toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a slot to the schedule.
     */
    public void add(Slot toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the slot {@code target} in the schedule with {@code editedSlot}.
     * {@code target} must exist in the schedule.
     */
    public void setSlot(Slot target, Slot editedSlot) {
        requireAllNonNull(target, editedSlot);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new SlotNotFoundException();
        }

        internalList.set(index, editedSlot);
    }

    /**
     * Removes the given slot from the schedule.
     * The slot must exist in the schedule.
     */
    public void remove(Slot toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new SlotNotFoundException();
        }
    }

    public void setSlots(Schedule replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this schedule with {@code slots}.
     */
    public void setSlots(List<Slot> slots) {
        requireAllNonNull(slots);
        internalList.setAll(slots);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Slot> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && internalList.equals(((Schedule) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
