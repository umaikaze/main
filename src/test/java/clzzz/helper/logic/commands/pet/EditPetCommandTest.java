package clzzz.helper.logic.commands.pet;

import static clzzz.helper.commons.core.Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX;
import static clzzz.helper.logic.commands.CommandTestUtil.DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_GENDER_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_NAME_DOG;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_NAME_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_TAG_LAZY;
import static clzzz.helper.logic.commands.CommandTestUtil.assertCommandFailure;
import static clzzz.helper.logic.commands.CommandTestUtil.assertCommandSuccess;
import static clzzz.helper.logic.commands.CommandTestUtil.showPetAtIndex;
import static clzzz.helper.testutil.TypicalIndexes.INDEX_FIRST_PET;
import static clzzz.helper.testutil.TypicalIndexes.INDEX_SECOND_PET;
import static clzzz.helper.testutil.pet.TypicalPets.getTypicalPetTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import clzzz.helper.commons.core.index.Index;
import clzzz.helper.logic.commands.pet.EditPetCommand.EditPetDescriptor;
import clzzz.helper.model.Model;
import clzzz.helper.model.ModelManager;
import clzzz.helper.model.PetTracker;
import clzzz.helper.model.UserPrefs;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.testutil.pet.EditPetDescriptorBuilder;
import clzzz.helper.testutil.pet.PetBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditPetCommand.
 */
public class EditPetCommandTest {

    private Model model = new ModelManager(getTypicalPetTracker(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Pet editedPet = new PetBuilder().build();
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder(editedPet).build();
        EditPetCommand editCommand = new EditPetCommand(INDEX_FIRST_PET, descriptor, "");

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
        Pet editedPet = petInList.withName(VALID_NAME_DOG).withGender(VALID_GENDER_GARFIELD)
                .withTags(VALID_TAG_LAZY).build();

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(VALID_NAME_DOG)
                .withGender(VALID_GENDER_GARFIELD.toString()).withTags(VALID_TAG_LAZY).build();
        EditPetCommand editCommand = new EditPetCommand(indexLastPet, descriptor, "");

        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS, editedPet);

        Model expectedModel = new ModelManager(new PetTracker(model.getPetTracker()), new UserPrefs());
        expectedModel.setPet(lastPet, editedPet);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditPetCommand editCommand = new EditPetCommand(INDEX_FIRST_PET, new EditPetDescriptor(), "");
        Pet editedPet = model.getFilteredPetList().get(INDEX_FIRST_PET.getZeroBased());

        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS, editedPet);

        Model expectedModel = new ModelManager(new PetTracker(model.getPetTracker()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPetAtIndex(model, INDEX_FIRST_PET);

        Pet petInFilteredList = model.getFilteredPetList().get(INDEX_FIRST_PET.getZeroBased());
        Pet editedPet = new PetBuilder(petInFilteredList).withName(VALID_NAME_DOG).build();
        EditPetCommand editCommand = new EditPetCommand(INDEX_FIRST_PET,
                new EditPetDescriptorBuilder().withName(VALID_NAME_DOG).build(), "");

        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS, editedPet);

        Model expectedModel = new ModelManager(new PetTracker(model.getPetTracker()), new UserPrefs());
        expectedModel.setPet(model.getFilteredPetList().get(0), editedPet);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePetUnfilteredList_failure() {
        Pet firstPet = model.getFilteredPetList().get(INDEX_FIRST_PET.getZeroBased());
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder(firstPet).build();
        EditPetCommand editCommand = new EditPetCommand(INDEX_SECOND_PET, descriptor, "");

        assertCommandFailure(editCommand, model, EditPetCommand.MESSAGE_DUPLICATE_PET);
    }

    @Test
    public void execute_duplicatePetFilteredList_failure() {
        showPetAtIndex(model, INDEX_FIRST_PET);

        // edit pet in filtered list into a duplicate in address book
        Pet petInList = model.getPetTracker().getPetList().get(INDEX_SECOND_PET.getZeroBased());
        EditPetCommand editCommand = new EditPetCommand(INDEX_FIRST_PET,
                new EditPetDescriptorBuilder(petInList).build(), "");

        assertCommandFailure(editCommand, model, EditPetCommand.MESSAGE_DUPLICATE_PET);
    }

    @Test
    public void execute_invalidPetIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPetList().size() + 1);
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(VALID_NAME_GARFIELD).build();
        EditPetCommand editCommand = new EditPetCommand(outOfBoundIndex, descriptor, "");

        assertCommandFailure(editCommand, model, MESSAGE_INVALID_PET_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPetIndexFilteredList_failure() {
        showPetAtIndex(model, INDEX_FIRST_PET);
        Index outOfBoundIndex = INDEX_SECOND_PET;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPetTracker().getPetList().size());

        EditPetCommand editCommand = new EditPetCommand(outOfBoundIndex,
                new EditPetDescriptorBuilder().withName(VALID_NAME_GARFIELD).build(), "");

        assertCommandFailure(editCommand, model, MESSAGE_INVALID_PET_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditPetCommand standardCommand = new EditPetCommand(INDEX_FIRST_PET, DESC_COCO, "");

        // same values -> returns true
        EditPetDescriptor copyDescriptor = new EditPetDescriptor(DESC_COCO);
        EditPetCommand commandWithSameValues = new EditPetCommand(INDEX_FIRST_PET, copyDescriptor, "");
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPetCommand(INDEX_SECOND_PET, DESC_COCO, "")));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPetCommand(INDEX_FIRST_PET, DESC_GARFIELD, "")));
    }

}
