package clzzz.helper.logic.commands.pet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static clzzz.helper.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.commands.CommandTestUtil;
import clzzz.helper.testutil.TypicalIndexes;
import clzzz.helper.testutil.pet.EditPetDescriptorBuilder;
import clzzz.helper.testutil.pet.PetBuilder;
import clzzz.helper.testutil.pet.TypicalPets;
import clzzz.helper.commons.core.Messages;
import clzzz.helper.commons.core.index.Index;
import clzzz.helper.logic.commands.pet.EditPetCommand.EditPetDescriptor;
import clzzz.helper.model.Model;
import clzzz.helper.model.ModelManager;
import clzzz.helper.model.PetTracker;
import clzzz.helper.model.UserPrefs;
import clzzz.helper.model.pet.Pet;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditPetCommand.
 */
public class EditPetCommandTest {

    private Model model = new ModelManager(TypicalPets.getTypicalPetTracker(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Pet editedPet = new PetBuilder().build();
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder(editedPet).build();
        EditPetCommand editCommand = new EditPetCommand(TypicalIndexes.INDEX_FIRST_PET, descriptor, "");

        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS, editedPet);

        Model expectedModel = new ModelManager(new PetTracker(model.getPetTracker()), new UserPrefs());
        expectedModel.setPet(model.getFilteredPetList().get(0), editedPet);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPet = Index.fromOneBased(model.getFilteredPetList().size());
        Pet lastPet = model.getFilteredPetList().get(indexLastPet.getZeroBased());

        PetBuilder petInList = new PetBuilder(lastPet);
        Pet editedPet = petInList.withName(CommandTestUtil.VALID_NAME_DOG).withGender(CommandTestUtil.VALID_GENDER_GARFIELD)
                .withTags(CommandTestUtil.VALID_TAG_LAZY).build();

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_DOG)
                .withGender(CommandTestUtil.VALID_GENDER_GARFIELD.toString()).withTags(CommandTestUtil.VALID_TAG_LAZY).build();
        EditPetCommand editCommand = new EditPetCommand(indexLastPet, descriptor, "");

        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS, editedPet);

        Model expectedModel = new ModelManager(new PetTracker(model.getPetTracker()), new UserPrefs());
        expectedModel.setPet(lastPet, editedPet);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditPetCommand editCommand = new EditPetCommand(TypicalIndexes.INDEX_FIRST_PET, new EditPetDescriptor(), "");
        Pet editedPet = model.getFilteredPetList().get(TypicalIndexes.INDEX_FIRST_PET.getZeroBased());

        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS, editedPet);

        Model expectedModel = new ModelManager(new PetTracker(model.getPetTracker()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showPetAtIndex(model, TypicalIndexes.INDEX_FIRST_PET);

        Pet petInFilteredList = model.getFilteredPetList().get(TypicalIndexes.INDEX_FIRST_PET.getZeroBased());
        Pet editedPet = new PetBuilder(petInFilteredList).withName(CommandTestUtil.VALID_NAME_DOG).build();
        EditPetCommand editCommand = new EditPetCommand(TypicalIndexes.INDEX_FIRST_PET,
                new EditPetDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_DOG).build(), "");

        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS, editedPet);

        Model expectedModel = new ModelManager(new PetTracker(model.getPetTracker()), new UserPrefs());
        expectedModel.setPet(model.getFilteredPetList().get(0), editedPet);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePetUnfilteredList_failure() {
        Pet firstPet = model.getFilteredPetList().get(TypicalIndexes.INDEX_FIRST_PET.getZeroBased());
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder(firstPet).build();
        EditPetCommand editCommand = new EditPetCommand(TypicalIndexes.INDEX_SECOND_PET, descriptor, "");

        CommandTestUtil.assertCommandFailure(editCommand, model, EditPetCommand.MESSAGE_DUPLICATE_PET);
    }

    @Test
    public void execute_duplicatePetFilteredList_failure() {
        CommandTestUtil.showPetAtIndex(model, TypicalIndexes.INDEX_FIRST_PET);

        // edit pet in filtered list into a duplicate in address book
        Pet petInList = model.getPetTracker().getPetList().get(TypicalIndexes.INDEX_SECOND_PET.getZeroBased());
        EditPetCommand editCommand = new EditPetCommand(TypicalIndexes.INDEX_FIRST_PET,
                new EditPetDescriptorBuilder(petInList).build(), "");

        CommandTestUtil.assertCommandFailure(editCommand, model, EditPetCommand.MESSAGE_DUPLICATE_PET);
    }

    @Test
    public void execute_invalidPetIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPetList().size() + 1);
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_GARFIELD).build();
        EditPetCommand editCommand = new EditPetCommand(outOfBoundIndex, descriptor, "");

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPetIndexFilteredList_failure() {
        CommandTestUtil.showPetAtIndex(model, TypicalIndexes.INDEX_FIRST_PET);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_PET;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPetTracker().getPetList().size());

        EditPetCommand editCommand = new EditPetCommand(outOfBoundIndex,
                new EditPetDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_GARFIELD).build(), "");

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditPetCommand standardCommand = new EditPetCommand(TypicalIndexes.INDEX_FIRST_PET, CommandTestUtil.DESC_COCO, "");

        // same values -> returns true
        EditPetDescriptor copyDescriptor = new EditPetDescriptor(CommandTestUtil.DESC_COCO);
        EditPetCommand commandWithSameValues = new EditPetCommand(TypicalIndexes.INDEX_FIRST_PET, copyDescriptor, "");
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPetCommand(TypicalIndexes.INDEX_SECOND_PET, CommandTestUtil.DESC_COCO, "")));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPetCommand(TypicalIndexes.INDEX_FIRST_PET, CommandTestUtil.DESC_GARFIELD, "")));
    }

}
