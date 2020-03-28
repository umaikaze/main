package seedu.address.logic.commands.pet;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.general.CommandResult;
import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PetTracker;
import seedu.address.model.ReadOnlyPetTracker;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.pet.FoodCollection;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;
import seedu.address.testutil.pet.PetBuilder;
import seedu.address.ui.DisplayItem;
import seedu.address.ui.DisplaySystemType;

public class AddPetCommandTest {

    @Test
    public void constructor_nullPet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPetCommand(null));
    }

    @Test
    public void execute_petAcceptedByModel_addSuccessful() throws Exception {
        seedu.address.logic.commands.pet.AddPetCommandTest.ModelStubAcceptingPetAdded modelStub =
                new seedu.address.logic.commands.pet.AddPetCommandTest.ModelStubAcceptingPetAdded();
        Pet validPet = new PetBuilder().build();

        CommandResult commandResult = new AddPetCommand(validPet).execute(modelStub);

        assertEquals(String.format(AddPetCommand.MESSAGE_SUCCESS, validPet), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPet), modelStub.petsAdded);
    }

    @Test
    public void execute_duplicatePet_throwsCommandException() {
        Pet validPet = new PetBuilder().build();
        AddPetCommand addCommand = new AddPetCommand(validPet);
        ModelStub modelStub = new ModelStubWithPet(validPet);

        assertThrows(CommandException.class, AddPetCommand.MESSAGE_DUPLICATE_PET, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Pet alice = new PetBuilder().withName("Alice").build();
        Pet bob = new PetBuilder().withName("Bob").build();
        AddPetCommand addAliceCommand = new AddPetCommand(alice);
        AddPetCommand addBobCommand = new AddPetCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddPetCommand addAliceCommandCopy = new AddPetCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different pet -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
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
        public void setPetTrackerFilePath(Path petTrackerFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPet(Pet pet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyPetTracker getPetTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPetTracker(ReadOnlyPetTracker newData) {
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
        public void deleteSlot(Slot slot) {
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
        public void changeDisplaySystem(DisplaySystemType newDisplayType) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single pet.
     */
    private class ModelStubWithPet extends ModelStub {
        private final Pet pet;

        ModelStubWithPet(Pet pet) {
            requireNonNull(pet);
            this.pet = pet;
        }

        @Override
        public boolean hasPet(Pet pet) {
            requireNonNull(pet);
            return this.pet.isSamePet(pet);
        }
    }

    /**
     * A Model stub that always accept the pet being added.
     */
    private class ModelStubAcceptingPetAdded extends ModelStub {
        final ArrayList<Pet> petsAdded = new ArrayList<>();

        @Override
        public boolean hasPet(Pet pet) {
            requireNonNull(pet);
            return petsAdded.stream().anyMatch(pet::isSamePet);
        }

        @Override
        public void addPet(Pet pet) {
            requireNonNull(pet);
            petsAdded.add(pet);
        }

        @Override
        public ReadOnlyPetTracker getPetTracker() {
            return new PetTracker();
        }
    }

}
