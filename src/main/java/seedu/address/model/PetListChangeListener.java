package seedu.address.model;

import java.util.List;

import javafx.collections.ListChangeListener;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;

/**
 * Listener class to look out for changes in the pet list and use to modify schedule correspondingly
 */
public abstract class PetListChangeListener implements ListChangeListener<Pet> {

    @Override
    public void onChanged (Change<? extends Pet> petChange) {
        List<? extends Pet> removedPets;
        List<? extends Pet> addedPets;

        while (petChange.next()) {
            removedPets = petChange.getRemoved();
            addedPets = petChange.getAddedSubList();

            assert (removedPets.size() < 2 && addedPets.size() < 2);

            if (removedPets.size() > 0 && addedPets.size() > 0) {
                updateSlotsDueToPetEdit(removedPets.get(0), addedPets.get(0));
            } else if (removedPets.size() > 0) {
                removeExcessSlot(removedPets.get(0));
            }
        }
    }

    protected Slot petReplacedSlot(Slot slot, Pet pet) {
        return new Slot(pet, slot.getDateTime(), slot.getDuration());
    }

    /**
     * To be filled up with methods accessible from UniquePetList to update the slots list
     * @param removed the pet that was removed on pet list change. We assert that only 1 pet may be deleted at a time.
     * @param added the pet that was added on pet list change. We assert that only 1 pet may be added at a time.
     */
    protected abstract void updateSlotsDueToPetEdit(Pet removed, Pet added);

    protected abstract void removeExcessSlot(Pet removed);
}
