package clzzz.helper.logic.commands.slot;

import static clzzz.helper.logic.commands.CommandTestUtil.assertCommandFailure;
import static clzzz.helper.logic.commands.CommandTestUtil.assertCommandSuccess;
import static clzzz.helper.logic.commands.CommandTestUtil.showSlotAtIndex;
import static clzzz.helper.testutil.TypicalIndexes.INDEX_FIRST_SLOT;
import static clzzz.helper.testutil.TypicalIndexes.INDEX_SECOND_SLOT;
import static clzzz.helper.testutil.pet.TypicalPets.getTypicalPetTrackerWithSlots;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import clzzz.helper.commons.core.Messages;
import clzzz.helper.commons.core.index.Index;
import clzzz.helper.model.Model;
import clzzz.helper.model.ModelManager;
import clzzz.helper.model.UserPrefs;
import clzzz.helper.model.slot.Slot;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteSlotCommand}.
 */
class DeleteSlotCommandTest {

    private Model model = new ModelManager(getTypicalPetTrackerWithSlots(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Slot slotToDelete = model.getFilteredSlotList().get(INDEX_FIRST_SLOT.getZeroBased());
        DeleteSlotCommand deleteCommand = new DeleteSlotCommand(INDEX_FIRST_SLOT);

        String expectedMessage = String.format(DeleteSlotCommand.MESSAGE_DELETE_SLOT_SUCCESS, slotToDelete);

        ModelManager expectedModel = new ModelManager(model.getPetTracker(), new UserPrefs());
        expectedModel.deleteSlot(slotToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSlotList().size() + 1);
        DeleteSlotCommand deleteCommand = new DeleteSlotCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_SLOT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showSlotAtIndex(model, INDEX_FIRST_SLOT);

        Slot slotToDelete = model.getFilteredSlotList().get(INDEX_FIRST_SLOT.getZeroBased());
        DeleteSlotCommand deleteCommand = new DeleteSlotCommand(INDEX_FIRST_SLOT);

        String expectedMessage = String.format(DeleteSlotCommand.MESSAGE_DELETE_SLOT_SUCCESS, slotToDelete);

        Model expectedModel = new ModelManager(model.getPetTracker(), new UserPrefs());
        expectedModel.deleteSlot(slotToDelete);
        showNoSlot(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showSlotAtIndex(model, INDEX_FIRST_SLOT);

        Index outOfBoundIndex = INDEX_SECOND_SLOT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPetTracker().getSlotList().size());

        DeleteSlotCommand deleteCommand = new DeleteSlotCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_SLOT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteSlotCommand deleteFirstCommand = new DeleteSlotCommand(INDEX_FIRST_SLOT);
        DeleteSlotCommand deleteSecondCommand = new DeleteSlotCommand(INDEX_SECOND_SLOT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteSlotCommand deleteFirstCommandCopy = new DeleteSlotCommand(INDEX_FIRST_SLOT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no slots.
     */
    private void showNoSlot(Model model) {
        model.updateFilteredSlotList(p -> false);

        assertTrue(model.getFilteredSlotList().isEmpty());
    }
}
