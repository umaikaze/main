package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyPetTracker {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Pet> getPetList();

    /**
     * Returns an unmodifiable view of the slots list.
     */
    ObservableList<Slot> getSlotList();
}
