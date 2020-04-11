package clzzz.helper.model;

import clzzz.helper.model.foodcollection.FoodCollection;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.slot.Slot;
import javafx.collections.ObservableList;

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

    /**
     * Returns an unmodifiable view of the food collection list.
     */
    ObservableList<FoodCollection> getFoodCollectionList();
}
