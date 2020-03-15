package seedu.address.logic.commands.pet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.pet.PshCommandTestUtil.DESC_COCO;
import static seedu.address.logic.commands.pet.PshCommandTestUtil.DESC_GARFIELD;
import static seedu.address.logic.commands.pet.PshCommandTestUtil.VALID_NAME_GARFIELD;
import static seedu.address.logic.commands.pet.PshCommandTestUtil.VALID_GENDER_GARFIELD;
import static seedu.address.logic.commands.pet.PshCommandTestUtil.VALID_TAG_LAZY;
import static seedu.address.logic.commands.pet.PshCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.pet.PshCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.pet.PshCommandTestUtil.showPetAtIndex;
import static seedu.address.testutil.pet.PshTypicalIndexes.INDEX_FIRST_PET;
import static seedu.address.testutil.pet.PshTypicalIndexes.INDEX_SECOND_PET;
import static seedu.address.testutil.TypicalPets.getTypicalPetTracker;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.PshMessages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.pet.EditPetCommand.EditPetDescriptor;
import seedu.address.model.PetTracker;
import seedu.address.model.PshModel;
import seedu.address.model.PshModelManager;
import seedu.address.model.PshUserPrefs;
import seedu.address.model.pet.Pet;
import seedu.address.testutil.pet.EditPetDescriptorBuilder;
import seedu.address.testutil.PetBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditPetCommand.
 */
public class EditPetCommandTest {

    private PshModel model = new PshModelManager(getTypicalPetTracker(), new PshUserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Pet editedPet = new PetBuilder().build();
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder(editedPet).build();
        EditPetCommand editCommand = new EditPetCommand(INDEX_FIRST_PET, descriptor);

        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS, editedPet);

        PshModel expectedModel = new PshModelManager(new PetTracker(model.getPetTracker()), new PshUserPrefs());
        expectedModel.setPet(model.getFilteredPetList().get(0), editedPet);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPet = Index.fromOneBased(model.getFilteredPetList().size());
        Pet lastPet = model.getFilteredPetList().get(indexLastPet.getZeroBased());

        PetBuilder petInList = new PetBuilder(lastPet);
        Pet editedPet = petInList.withName(VALID_NAME_GARFIELD).withGender(VALID_GENDER_GARFIELD)
                .withTags(VALID_TAG_LAZY).build();

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(VALID_NAME_GARFIELD)
                .withGender(VALID_GENDER_GARFIELD.toString()).withTags(VALID_TAG_LAZY).build();
        EditPetCommand editCommand = new EditPetCommand(indexLastPet, descriptor);

        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS, editedPet);

        PshModel expectedModel = new PshModelManager(new PetTracker(model.getPetTracker()), new PshUserPrefs());
        expectedModel.setPet(lastPet, editedPet);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditPetCommand editCommand = new EditPetCommand(INDEX_FIRST_PET, new EditPetDescriptor());
        Pet editedPet = model.getFilteredPetList().get(INDEX_FIRST_PET.getZeroBased());

        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS, editedPet);

        PshModel expectedModel = new PshModelManager(new PetTracker(model.getPetTracker()), new PshUserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPetAtIndex(model, INDEX_FIRST_PET);

        Pet petInFilteredList = model.getFilteredPetList().get(INDEX_FIRST_PET.getZeroBased());
        Pet editedPet = new PetBuilder(petInFilteredList).withName(VALID_NAME_GARFIELD).build();
        EditPetCommand editCommand = new EditPetCommand(INDEX_FIRST_PET,
                new EditPetDescriptorBuilder().withName(VALID_NAME_GARFIELD).build());

        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS, editedPet);

        PshModel expectedModel = new PshModelManager(new PetTracker(model.getPetTracker()), new PshUserPrefs());
        expectedModel.setPet(model.getFilteredPetList().get(0), editedPet);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePetUnfilteredList_failure() {
        Pet firstPet = model.getFilteredPetList().get(INDEX_FIRST_PET.getZeroBased());
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder(firstPet).build();
        EditPetCommand editCommand = new EditPetCommand(INDEX_SECOND_PET, descriptor);

        assertCommandFailure(editCommand, model, EditPetCommand.MESSAGE_DUPLICATE_PET);
    }

    @Test
    public void execute_duplicatePetFilteredList_failure() {
        showPetAtIndex(model, INDEX_FIRST_PET);

        // edit pet in filtered list into a duplicate in address book
        Pet petInList = model.getPetTracker().getPetList().get(INDEX_SECOND_PET.getZeroBased());
        EditPetCommand editCommand = new EditPetCommand(INDEX_FIRST_PET,
                new EditPetDescriptorBuilder(petInList).build());

        assertCommandFailure(editCommand, model, EditPetCommand.MESSAGE_DUPLICATE_PET);
    }

    @Test
    public void execute_invalidPetIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPetList().size() + 1);
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(VALID_NAME_GARFIELD).build();
        EditPetCommand editCommand = new EditPetCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, PshMessages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
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
                new EditPetDescriptorBuilder().withName(VALID_NAME_GARFIELD).build());

        assertCommandFailure(editCommand, model, PshMessages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditPetCommand standardCommand = new EditPetCommand(INDEX_FIRST_PET, DESC_COCO);

        // same values -> returns true
        EditPetDescriptor copyDescriptor = new EditPetDescriptor(DESC_COCO);
        EditPetCommand commandWithSameValues = new EditPetCommand(INDEX_FIRST_PET, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPetCommand(INDEX_SECOND_PET, DESC_COCO)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPetCommand(INDEX_FIRST_PET, DESC_GARFIELD)));
    }

}
