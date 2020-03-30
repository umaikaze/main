package seedu.address.logic.commands.slot;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.pet.TypicalPets.getTypicalModelManager;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.general.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.PetTracker;
import seedu.address.model.ReadOnlyPetTracker;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.pet.FoodCollection;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;
import seedu.address.testutil.slot.SlotBuilder;
import seedu.address.ui.DisplayItem;
import seedu.address.ui.DisplaySystemType;

class AddSlotCommandTest {

    private Model typicalModel = getTypicalModelManager();

    @Test
    public void constructor_nullSlot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddSlotCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() {
        ModelStubAcceptingSlotAdded modelStub = new ModelStubAcceptingSlotAdded();
        Slot validSlot = new SlotBuilder(typicalModel).build();

        CommandResult commandResult = new AddSlotCommand(validSlot).execute(modelStub);

        assertEquals(String.format(AddSlotCommand.MESSAGE_SUCCESS, validSlot), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validSlot), modelStub.slotsAdded);
    }

    @Test
    public void equals() {
        Slot alice = new SlotBuilder(typicalModel).withPet(typicalModel.getPet(new Name("Coco"))).build();
        Slot bob = new SlotBuilder(typicalModel).withPet(typicalModel.getPet(new Name("Garfield"))).build();
        AddSlotCommand addAliceCommand = new AddSlotCommand(alice);
        AddSlotCommand addBobCommand = new AddSlotCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddSlotCommand addAliceCommandCopy = new AddSlotCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    // TODO Merge with the one in AddPetCommandTest
    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getPetTrackerFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPetTrackerFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPetTracker(ReadOnlyPetTracker petTracker) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyPetTracker getPetTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPet(Pet pet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Pet getPet(Name name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePet(Pet target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPet(Pet pet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPet(Pet target, Pet editedPet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Pet> getFilteredPetList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPetList(Predicate<Pet> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSlot(Slot target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSlot(Slot slot) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSlot(Slot target, Slot editedSlot) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Slot> getFilteredSlotList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredSlotList(Predicate<Slot> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<FoodCollection> getFilteredFoodCollectionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFoodCollectionList(Predicate<FoodCollection> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<DisplayItem> getFilteredDisplayList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public DisplaySystemType getCurrentDisplaySystemType() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void changeDisplaySystem(DisplaySystemType newDisplayType) throws IllegalValueException {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accept the slot being added.
     */
    private class ModelStubAcceptingSlotAdded extends ModelStub {
        final ArrayList<Slot> slotsAdded = new ArrayList<>();

        @Override
        public void addSlot(Slot slot) {
            requireNonNull(slot);
            slotsAdded.add(slot);
        }

        @Override
        public ReadOnlyPetTracker getPetTracker() {
            return new PetTracker();
        }
    }
}
