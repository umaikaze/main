package clzzz.helper.logic.commands.pet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static clzzz.helper.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.commands.CommandTestUtil;
import clzzz.helper.testutil.TypicalIndexes;
import clzzz.helper.testutil.pet.TypicalPets;
import clzzz.helper.commons.core.Messages;
import clzzz.helper.commons.core.index.Index;
import clzzz.helper.model.Model;
import clzzz.helper.model.ModelManager;
import clzzz.helper.model.UserPrefs;
import clzzz.helper.model.pet.Pet;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeletePetCommand}.
 */
public class DeletePetCommandTest {

    private Model model = new ModelManager(TypicalPets.getTypicalPetTracker(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Pet petToDelete = model.getFilteredPetList().get(TypicalIndexes.INDEX_FIRST_PET.getZeroBased());
        DeletePetCommand deleteCommand = new DeletePetCommand(TypicalIndexes.INDEX_FIRST_PET);

        String expectedMessage = String.format(DeletePetCommand.MESSAGE_DELETE_PET_SUCCESS, petToDelete);

        ModelManager expectedModel = new ModelManager(model.getPetTracker(), new UserPrefs());
        expectedModel.deletePet(petToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPetList().size() + 1);
        DeletePetCommand deleteCommand = new DeletePetCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showPetAtIndex(model, TypicalIndexes.INDEX_FIRST_PET);

        Pet petToDelete = model.getFilteredPetList().get(TypicalIndexes.INDEX_FIRST_PET.getZeroBased());
        DeletePetCommand deleteCommand = new DeletePetCommand(TypicalIndexes.INDEX_FIRST_PET);

        String expectedMessage = String.format(DeletePetCommand.MESSAGE_DELETE_PET_SUCCESS, petToDelete);

        Model expectedModel = new ModelManager(model.getPetTracker(), new UserPrefs());
        expectedModel.deletePet(petToDelete);
        showNoPet(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showPetAtIndex(model, TypicalIndexes.INDEX_FIRST_PET);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_PET;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPetTracker().getPetList().size());

        DeletePetCommand deleteCommand = new DeletePetCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeletePetCommand deleteFirstCommand = new DeletePetCommand(TypicalIndexes.INDEX_FIRST_PET);
        DeletePetCommand deleteSecondCommand = new DeletePetCommand(TypicalIndexes.INDEX_SECOND_PET);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePetCommand deleteFirstCommandCopy = new DeletePetCommand(TypicalIndexes.INDEX_FIRST_PET);
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
    private void showNoPet(Model model) {
        model.updateFilteredPetList(p -> false);

        assertTrue(model.getFilteredPetList().isEmpty());
    }
}
