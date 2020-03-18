package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;

/**
 * Represents the in-memory model of the pet tracker data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final PetTracker petTracker;
    private final UserPrefs userPrefs;
    private final FilteredList<Pet> filteredPets;
    private final FilteredList<Slot> filteredSlots;

    /**
     * Initializes a ModelManager with the given petTracker and userPrefs.
     */
    public ModelManager(ReadOnlyPetTracker petTracker, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(petTracker, userPrefs);

        logger.fine("Initializing with pet tracker: " + petTracker + " and user prefs " + userPrefs);

        this.petTracker = new PetTracker(petTracker);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPets = new FilteredList<>(this.petTracker.getPetList());
        filteredSlots = new FilteredList<>(this.petTracker.getSlotList());
    }

    public ModelManager() {
        this(new PetTracker(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getPetTrackerFilePath() {
        return userPrefs.getPetTrackerFilePath();
    }

    @Override
    public void setPetTrackerFilePath(Path petTrackerFilePath) {
        requireNonNull(petTrackerFilePath);
        userPrefs.setPetTrackerFilePath(petTrackerFilePath);
    }

    //=========== Pet Tracker ================================================================================

    @Override
    public void setPetTracker(ReadOnlyPetTracker petTracker) {
        this.petTracker.resetData(petTracker);
    }

    @Override
    public ReadOnlyPetTracker getPetTracker() {
        return petTracker;
    }

    @Override
    public boolean hasPet(Pet pet) {
        requireNonNull(pet);
        return petTracker.hasPet(pet);
    }

    @Override
    public Pet getPet(Name name) {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public void deletePet(Pet target) {
        petTracker.removePet(target);
    }

    @Override
    public void addPet(Pet pet) {
        petTracker.addPet(pet);
        updateFilteredPetList(PREDICATE_SHOW_ALL_PETS);
    }

    @Override
    public void setPet(Pet target, Pet editedPet) {
        requireAllNonNull(target, editedPet);

        petTracker.setPet(target, editedPet);
    }

    //=========== Filtered Pet List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Pet} backed by the internal list of
     * {@code versionedpetTracker}
     */
    @Override
    public ObservableList<Pet> getFilteredPetList() {
        return filteredPets;
    }

    @Override
    public void updateFilteredPetList(Predicate<Pet> predicate) {
        requireNonNull(predicate);
        filteredPets.setPredicate(predicate);
    }

    //=========== Slot  ================================================================================

    @Override
    public void deleteSlot(Slot target) {
        petTracker.removeSlot(target);
    }

    @Override
    public void addSlot(Slot slot) {
        petTracker.addSlot(slot);
        updateFilteredSlotList(PREDICATE_SHOW_ALL_SLOTS);
    }

    @Override
    public void setSlot(Slot target, Slot editedSlot) {
        requireAllNonNull(target, editedSlot);
        petTracker.setSlot(target, editedSlot);
    }

    //=========== Filtered Slot List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Slot} backed by the internal list of
     * {@code versionedpetTracker}
     */
    @Override
    public ObservableList<Slot> getFilteredSlotList() {
        return filteredSlots;
    }

    @Override
    public void updateFilteredSlotList(Predicate<Slot> predicate) {
        requireNonNull(predicate);
        filteredSlots.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return petTracker.equals(other.petTracker)
                && userPrefs.equals(other.userPrefs)
                && filteredPets.equals(other.filteredPets)
                && filteredSlots.equals(other.filteredSlots);
    }

}
