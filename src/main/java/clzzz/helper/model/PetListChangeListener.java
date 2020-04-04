package clzzz.helper.model;

import java.util.List;

import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.slot.Slot;
import javafx.collections.ListChangeListener;

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

            if (removedPets.size() > 0 && addedPets.size() > 0) {
                for (int i = 0; i < removedPets.size(); i++) {
                    updateSlotsDueToPetEdit(removedPets.get(i), addedPets.get(i));
                }
            } else if (removedPets.size() > 0) {
                for (Pet removedPet : removedPets) {
                    removeExcessSlot(removedPet);
                }
            }
        }
    }

    protected Slot petReplacedSlot(Slot slot, Pet pet) {
        return new Slot(pet, slot.getDateTime(), slot.getDuration());
    }

    /**
     * To be filled up with methods accessible from UniquePetList to update the slots list
     * @param removed the pet that was removed on pet list change. We assume that only 1 pet is deleted at a time.
     * @param added the pet that was added on pet list change. We assume that only 1 pet is added at a time.
     */
    protected abstract void updateSlotsDueToPetEdit(Pet removed, Pet added);

    protected abstract void removeExcessSlot(Pet removed);
}
