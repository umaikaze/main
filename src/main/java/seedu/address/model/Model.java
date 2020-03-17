package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;

/**
 * The API of the Pet Store Helper Model component.
 */
public interface Model {

    /**
     * {@code Predicate} that always evaluate to true.
     */
    Predicate<Pet> PREDICATE_SHOW_ALL_PETS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true.
     */
    Predicate<Slot> PREDICATE_SHOW_ALL_SLOTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' pet tracker file path.
     */
    Path getPetTrackerFilePath();

    /**
     * Sets the user prefs' pet tracker file path.
     */
    void setPetTrackerFilePath(Path petTrackerFilePath);

    /**
     * Replaces pet tracker book data with the data in {@code petTracker}.
     */
    void setPetTracker(ReadOnlyPetTracker petTracker);

    /**
     * Returns the PetTracker.
     */
    ReadOnlyPetTracker getPetTracker();

    /**
     * Returns true if a pet with the same identity as {@code pet} exists in the pet tracker.
     */
    boolean hasPet(Pet pet);

    /**
     * Returns the pet with the given {@code name}.
     */
    Pet getPet(Name name);

    /**
     * Deletes the given pet.
     * The pet must exist in the pet tracker.
     */
    void deletePet(Pet target);

    /**
     * Adds the given pet.
     * {@code pet} must not already exist in the pet tracker.
     */
    void addPet(Pet pet);

    /**
     * Replaces the given pet {@code target} with {@code editedPet}.
     * {@code target} must exist in the pet tracker.
     * The pet identity of {@code editedPet} must not be the same as another existing pet in the pet tracker.
     */
    void setPet(Pet target, Pet editedPet);

    /**
     * Returns an unmodifiable view of the filtered pet list
     */
    ObservableList<Pet> getFilteredPetList();

    /**
     * Updates the filter of the filtered pet list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPetList(Predicate<Pet> predicate);

    // no need for hasSlot(), because duplicate slots are allowed

    /**
     * Deletes the given slot.
     * The slot must exist in the pet tracker.
     */
    void deleteSlot(Slot target);

    /**
     * Adds the given slot.
     */
    void addSlot(Slot slot);

    /**
     * Replaces the given slot {@code target} with {@code editedSlot}.
     * {@code target} must exist in the pet tracker.
     */
    void setSlot(Slot target, Slot editedSlot);

    /**
     * Returns an unmodifiable view of the filtered slot list
     */
    ObservableList<Slot> getFilteredSlotList();

    /**
     * Updates the filter of the filtered slot list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredSlotList(Predicate<Slot> predicate);
}
