package clzzz.helper.logic.commands.pet;

import static clzzz.helper.testutil.Assert.assertThrows;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import clzzz.helper.commons.core.GuiSettings;
import clzzz.helper.logic.commands.CommandResult;
import clzzz.helper.logic.commands.exceptions.CommandException;
import clzzz.helper.model.Model;
import clzzz.helper.model.PetTracker;
import clzzz.helper.model.ReadOnlyPetTracker;
import clzzz.helper.model.ReadOnlyUserPrefs;
import clzzz.helper.model.foodcollection.FoodCollection;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.testutil.pet.PetBuilder;
import clzzz.helper.ui.DisplaySystemType;
import clzzz.helper.ui.list.DisplayItem;
import javafx.collections.ObservableList;

public class AddPetCommandTest {

    @Test
    public void constructor_nullPet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPetCommand(null, null));
    }

    @Test
    public void execute_petAcceptedByModel_addSuccessful() throws Exception {
        AddPetCommandTest.ModelStubAcceptingPetAdded modelStub =
                new AddPetCommandTest.ModelStubAcceptingPetAdded();
        Pet validPet = new PetBuilder().build();

        CommandResult commandResult = new AddPetCommand(validPet, "").execute(modelStub);

        assertEquals(String.format(AddPetCommand.MESSAGE_SUCCESS, validPet), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPet), modelStub.petsAdded);
    }

    @Test
    public void execute_duplicatePet_throwsCommandException() {
        Pet validPet = new PetBuilder().build();
        AddPetCommand addCommand = new AddPetCommand(validPet, "");
        ModelStub modelStub = new ModelStubWithPet(validPet);

        assertThrows(CommandException.class, AddPetCommand.MESSAGE_DUPLICATE_PET, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Pet alice = new PetBuilder().withName("Alice").build();
        Pet bob = new PetBuilder().withName("Bob").build();
        AddPetCommand addAliceCommand = new AddPetCommand(alice, "");
        AddPetCommand addBobCommand = new AddPetCommand(bob, "");

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddPetCommand addAliceCommandCopy = new AddPetCommand(alice, "");
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
        public void updateAll() {
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
