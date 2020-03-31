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
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.general.DisplayCommand;
import seedu.address.model.pet.FoodCollection;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;
import seedu.address.ui.DisplayItem;
import seedu.address.ui.DisplaySystemType;

/**
 * Represents the in-memory model of the pet tracker data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final PetTracker petTracker;
    private final UserPrefs userPrefs;
    private final FilteredList<Pet> filteredPets;
    private final FilteredList<Slot> filteredSlots;
    private final FilteredList<FoodCollection> filteredFoodCollections;
    private DisplaySystemType currentDisplaySystemType;
    private ObservableList<DisplayItem> filteredDisplayItems;

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
        filteredFoodCollections = new FilteredList<>(this.petTracker.getFoodCollectionList());

        filteredDisplayItems = CollectionUtil.map(filteredPets, pet -> pet); // display list of pets initially
        currentDisplaySystemType = DisplaySystemType.PETS;
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
        return petTracker.getPet(name);
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
        changeSystemToFilteredPets();
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
        changeSystemToFilteredSlots();
    }

    //=========== Filtered Food Collection List Accessors =============================================================

    /**
     * Updates the filter of the filtered food collection list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    public void updateFilteredFoodCollectionList(Predicate<FoodCollection> predicate) {
        requireNonNull(predicate);
        filteredFoodCollections.setPredicate(predicate);
    }

    public ObservableList<FoodCollection> getFilteredFoodCollectionList() {
        return filteredFoodCollections;
    }

    //=========== Common methods =============================================================

    @Override
    public ObservableList<DisplayItem> getFilteredDisplayList() {
        return filteredDisplayItems;
    }


    public DisplaySystemType getCurrentDisplaySystemType() {
        return currentDisplaySystemType;
    }

    /**
     * Changes the display system to pets system. Note this is specifically written to accommodate `findpets` command
     * which allows users to execute `findpets` in a different system display.
     */
    public void changeSystemToFilteredPets() {
        filteredDisplayItems = CollectionUtil.map(filteredPets, pet -> pet);
        currentDisplaySystemType = DisplaySystemType.PETS;
    }

    /**
     * Changes the display system to pets system. Note this is specifically written to accommodate `findslots` command
     * which allows users to execute `findslots` in a different system display.
     */
    public void changeSystemToFilteredSlots() {
        filteredDisplayItems = CollectionUtil.map(filteredSlots, slot -> slot);
        currentDisplaySystemType = DisplaySystemType.SCHEDULE;
    }

    @Override
    /**
     * Used for display all pets/slots/inventory in display command.
     */
    public void changeDisplaySystem(DisplaySystemType newDisplayType) throws IllegalValueException {
        switch (newDisplayType) {
        case PETS:
            filteredDisplayItems = CollectionUtil.map(filteredPets, pet -> pet);
            updateFilteredPetList(PREDICATE_SHOW_ALL_PETS);
            break;
        case SCHEDULE:
            filteredDisplayItems = CollectionUtil.map(filteredSlots, slot -> slot);
            updateFilteredSlotList(PREDICATE_SHOW_ALL_SLOTS);
            break;
        case INVENTORY:
            filteredDisplayItems =
                    CollectionUtil.map(petTracker.getFoodCollectionList(), foodCollection -> foodCollection);
            updateFilteredFoodCollectionList(PREDICATE_SHOW_ALL_FOOD_COLLECTIONS);
            break;
        case CALENDAR:
            break;
        default:
            throw new IllegalValueException(DisplayCommand.MESSAGE_INVALID_SYSTEM_TYPE);
        }
        currentDisplaySystemType = newDisplayType;
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
