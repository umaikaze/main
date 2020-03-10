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
import seedu.address.model.pet.Pet;

/**
 * Represents the in-memory model of the address book data.
 */
public class PshModelManager implements PshModel {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final PetTracker petTracker;
    private final PshUserPrefs userPrefs;
    private final FilteredList<Pet> filteredPets;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public PshModelManager(ReadOnlyPetTracker petTracker, PshReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(petTracker, userPrefs);

        logger.fine("Initializing with address book: " + petTracker + " and user prefs " + userPrefs);

        this.petTracker = new PetTracker(petTracker);
        this.userPrefs = new PshUserPrefs(userPrefs);
        filteredPets = new FilteredList<>(this.petTracker.getPetList());
    }

    public PshModelManager() {
        this(new PetTracker(), new PshUserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(PshReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public PshReadOnlyUserPrefs getUserPrefs() {
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
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
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

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof PshModelManager)) {
            return false;
        }

        // state check
        PshModelManager other = (PshModelManager) obj;
        return petTracker.equals(other.petTracker)
                && userPrefs.equals(other.userPrefs)
                && filteredPets.equals(other.filteredPets);
    }

}
