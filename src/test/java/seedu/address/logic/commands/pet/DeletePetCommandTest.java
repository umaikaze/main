package seedu.address.logic.commands.pet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.pet.PshCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.pet.PshCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.pet.PshCommandTestUtil.showPetAtIndex;
import static seedu.address.testutil.pet.PshTypicalIndexes.INDEX_FIRST_PET;
import static seedu.address.testutil.pet.PshTypicalIndexes.INDEX_SECOND_PET;
import static seedu.address.testutil.TypicalPets.getTypicalPetTracker;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.PshMessages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.PshModel;
import seedu.address.model.PshModelManager;
import seedu.address.model.PshUserPrefs;
import seedu.address.model.pet.Pet;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeletePetCommand}.
 */
public class DeletePetCommandTest {

    private PshModel model = new PshModelManager(getTypicalPetTracker(), new PshUserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Pet petToDelete = model.getFilteredPetList().get(INDEX_FIRST_PET.getZeroBased());
        DeletePetCommand deleteCommand = new DeletePetCommand(INDEX_FIRST_PET);

        String expectedMessage = String.format(DeletePetCommand.MESSAGE_DELETE_PET_SUCCESS, petToDelete);

        PshModelManager expectedModel = new PshModelManager(model.getPetTracker(), new PshUserPrefs());
        expectedModel.deletePet(petToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPetList().size() + 1);
        DeletePetCommand deleteCommand = new DeletePetCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, PshMessages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPetAtIndex(model, INDEX_FIRST_PET);

        Pet petToDelete = model.getFilteredPetList().get(INDEX_FIRST_PET.getZeroBased());
        DeletePetCommand deleteCommand = new DeletePetCommand(INDEX_FIRST_PET);

        String expectedMessage = String.format(DeletePetCommand.MESSAGE_DELETE_PET_SUCCESS, petToDelete);

        PshModel expectedModel = new PshModelManager(model.getPetTracker(), new PshUserPrefs());
        expectedModel.deletePet(petToDelete);
        showNoPet(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPetAtIndex(model, INDEX_FIRST_PET);

        Index outOfBoundIndex = INDEX_SECOND_PET;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPetTracker().getPetList().size());

        DeletePetCommand deleteCommand = new DeletePetCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, PshMessages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeletePetCommand deleteFirstCommand = new DeletePetCommand(INDEX_FIRST_PET);
        DeletePetCommand deleteSecondCommand = new DeletePetCommand(INDEX_SECOND_PET);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePetCommand deleteFirstCommandCopy = new DeletePetCommand(INDEX_FIRST_PET);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different pet -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPet(PshModel model) {
        model.updateFilteredPetList(p -> false);

        assertTrue(model.getFilteredPetList().isEmpty());
    }
}
