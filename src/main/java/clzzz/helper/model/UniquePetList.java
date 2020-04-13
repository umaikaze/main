package clzzz.helper.model;

import static clzzz.helper.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import clzzz.helper.model.foodcollection.FoodCollection;
import clzzz.helper.model.foodcollection.FoodCollectionList;
import clzzz.helper.model.pet.Food;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.pet.exceptions.DuplicatePetException;
import clzzz.helper.model.pet.exceptions.PetNotFoundException;
import clzzz.helper.model.slot.Schedule;
import clzzz.helper.model.slot.Slot;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * A list of pets that enforces uniqueness between its elements and does not allow nulls.
 * A pet is considered unique by comparing using {@code Pet#isSamePet(Pet)}. As such, adding and updating of
 * pets uses Pet#isSamePet(Pet) for equality so as to ensure that the pet being added or updated is
 * unique in terms of identity in the UniquePetList. However, the removal of a pet uses Pet#equals(Object) so
 * as to ensure that the pet with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Pet#isSamePet(Pet)
 */
public class UniquePetList implements Iterable<Pet> {

    private final ObservableList<Pet> internalList = FXCollections.observableArrayList();
    private final ObservableList<Pet> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private final FoodCollectionList foodCollectionList = new FoodCollectionList(internalList);
    private final Schedule slots = new Schedule();

    public UniquePetList() {
        setInternalListListenerForFoodCollectionList();
        setInternalListListenerForSchedule();
    }

    /**
     * Sets a Listener on {@code internalList} so that whenever the {@code internalList} changes, the
     * foodCollectionList is updated according to the change of internalList.
     */
    public void setInternalListListenerForFoodCollectionList() {
        internalList.addListener((ListChangeListener<Pet>) change -> {
            if (change.next()) {
                updateFoodCollectionList();
            }
        });
    }

    public void setInternalListListenerForSchedule() {
        internalList.addListener(new PetListChangeListener() {
            @Override
            protected void updateSlotsDueToPetEdit(Pet removed, Pet added) {
                ObservableList<Slot> toEdit = slots.getInternalUnmodifiableListForPetName(removed.getName().fullName);
                for (Slot slot : toEdit) {
                    slots.setSlot(slot, petReplacedSlot(slot, added));
                }
            }

            @Override
            protected void removeExcessSlot(Pet removed) {
                ObservableList<Slot> toRemove = slots.getInternalUnmodifiableListForPetName(removed.getName().fullName);
                for (Slot slot : toRemove) {
                    slots.remove(slot);
                }
            }
        });
    }

    /**
     * Returns true if the list contains an equivalent pet as the given argument.
     */
    public boolean contains(Pet toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePet);
    }

    /**
     * Returns the pet in the list with the matching name.
     */
    public Pet getPet(Name name) {
        requireNonNull(name);
        List<Pet> petsWithMatchingName = internalList.stream()
                .filter(pet -> pet.getName().equals(name))
                .collect(Collectors.toList());
        assert petsWithMatchingName.size() <= 1 : "Duplicate pets detected!";
        if (petsWithMatchingName.size() == 0) {
            throw new PetNotFoundException();
        }
        return petsWithMatchingName.get(0);
    }

    /**
     * Adds a pet to the list.
     * The pet must not already exist in the list.
     */
    public void add(Pet toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePetException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the pet {@code target} in the list with {@code editedPet}.
     * {@code target} must exist in the list.
     * The pet identity of {@code editedPet} must not be the same as another existing pet in the list.
     */
    public void setPet(Pet target, Pet editedPet) {
        requireAllNonNull(target, editedPet);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PetNotFoundException();
        }

        if (!target.isSamePet(editedPet) && contains(editedPet)) {
            throw new DuplicatePetException();
        }

        internalList.set(index, editedPet);
    }

    /**
     * Removes the equivalent pet from the list.
     * The pet must exist in the list.
     */
    public void remove(Pet toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PetNotFoundException();
        }
    }

    /**
     * Replace the contents of the list entire with the contents of {@code replacement}
     */
    public void setPets(UniquePetList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code pets}.
     * {@code pets} must not contain duplicate pets.
     */
    public void setPets(List<Pet> pets) {
        requireAllNonNull(pets);
        if (!petsAreUnique(pets)) {
            throw new DuplicatePetException();
        }

        internalList.setAll(pets);
    }

    public List<Food> getFoodList() {
        List<Food> foods = new ArrayList<>();
        for (Pet pet:internalList) {
            foods.addAll(pet.getFoodList());
        }
        return foods;
    }

    // Slots methods

    /**
     * Replaces the contents of the schedule with {@code slots}.
     */
    public void setSlots(List<Slot> slots) {
        this.slots.setSlots(slots);
    }

    public void addSlot(Slot slot) {
        slots.add(slot);
    }

    /**
     * Removes {@code key} from this {@code PetTracker}.
     * {@code key} must exist in the pet tracker.
     */
    public void removeSlot(Slot key) {
        slots.remove(key);
    }

    /**
     * Replaces the given slot {@code target} in the list with {@code editedSlot}.
     * {@code target} must exist in the pet tracker.
     */
    public void setSlot(Slot target, Slot editedSlot) {
        requireNonNull(editedSlot);
        slots.setSlot(target, editedSlot);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Pet> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns the list of food collections as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<FoodCollection> acquireUnmodifiableFoodCollectionList() {
        return foodCollectionList.asUnmodifiableObservableList();
    }

    public ObservableList<Slot> acquireUnmodifiableSlotsList() {
        return slots.asUnmodifiableObservableList();
    }

    @Override
    public Iterator<Pet> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePetList // instanceof handles nulls
                && internalList.equals(((UniquePetList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code pets} contains only unique pets.
     */
    private boolean petsAreUnique(List<Pet> pets) {
        for (int i = 0; i < pets.size() - 1; i++) {
            for (int j = i + 1; j < pets.size(); j++) {
                if (pets.get(i).isSamePet(pets.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    private void updateFoodCollectionList() {
        foodCollectionList.update(internalList);
    }
}

/**
 * Listener class to look out for changes in the pet list and use to modify schedule correspondingly
 */
abstract class PetListChangeListener implements ListChangeListener<Pet> {

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

    protected Slot petReplacedSlot(Slot slot, Pet newPet) {
        return slot.replacePetWith(newPet);
    }

    /**
     * To be filled up with methods accessible from UniquePetList to update the slots list
     * @param removed the pet that was removed on pet list change. We assume that only 1 pet is deleted at a time.
     * @param added the pet that was added on pet list change. We assume that only 1 pet is added at a time.
     */
    protected abstract void updateSlotsDueToPetEdit(Pet removed, Pet added);

    protected abstract void removeExcessSlot(Pet removed);
}
