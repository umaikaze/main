package seedu.address.model;

import java.util.List;

import javafx.collections.ListChangeListener;
import seedu.address.model.pet.Pet;

/**
 * Listener class to look out for changes in the pet list and use to modify schedule correspondingly
 */
public abstract class PetListChangeListener implements ListChangeListener<Pet> {

    @Override
    public void onChanged (Change<? extends Pet> petChange) {
        List<? extends Pet> removed = petChange.getRemoved();
        List<? extends Pet> added = petChange.getAddedSubList();

        assert (removed.size() < 2 && added.size() < 2);

        updateSlotsDueToPetEdit(removed.get(0), added.get(0));
        removeExcessSlot(removed.get(0));
    }

    protected abstract void updateSlotsDueToPetEdit(Pet removed, Pet added);

    protected abstract void removeExcessSlot(Pet removed);
}
