package clzzz.helper.logic.commands.slot;

import static clzzz.helper.commons.core.Messages.MESSAGE_INVALID_SLOT_DISPLAYED_INDEX;
import static clzzz.helper.logic.commands.CommandTestUtil.SLOT_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.SLOT_DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DATETIME_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DURATION_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_NAME_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.assertCommandFailure;
import static clzzz.helper.logic.commands.CommandTestUtil.assertCommandSuccess;
import static clzzz.helper.logic.commands.CommandTestUtil.showSlotAtIndex;
import static clzzz.helper.testutil.TypicalIndexes.INDEX_FIRST_SLOT;
import static clzzz.helper.testutil.TypicalIndexes.INDEX_SECOND_SLOT;
import static clzzz.helper.testutil.pet.TypicalPets.getTypicalPetTrackerWithSlots;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import clzzz.helper.commons.core.index.Index;
import clzzz.helper.logic.commands.slot.EditSlotCommand.EditSlotDescriptor;
import clzzz.helper.model.Model;
import clzzz.helper.model.ModelManager;
import clzzz.helper.model.PetTracker;
import clzzz.helper.model.UserPrefs;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.testutil.slot.EditSlotDescriptorBuilder;
import clzzz.helper.testutil.slot.SlotBuilder;

class EditSlotCommandTest {

    private Model model = new ModelManager(getTypicalPetTrackerWithSlots(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Slot editedSlot = new SlotBuilder().build();
        EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder(editedSlot).build();
        EditSlotCommand editCommand = new EditSlotCommand(INDEX_FIRST_SLOT, descriptor, "");

        String expectedMessage = String.format(EditSlotCommand.MESSAGE_EDIT_SLOT_SUCCESS, editedSlot);

        Model expectedModel = new ModelManager(new PetTracker(model.getPetTracker()), new UserPrefs());
        expectedModel.setSlot(model.getFilteredSlotList().get(0), editedSlot);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastSlot = Index.fromOneBased(model.getFilteredSlotList().size());
        Slot lastSlot = model.getFilteredSlotList().get(indexLastSlot.getZeroBased());

        SlotBuilder slotInList = new SlotBuilder(lastSlot);
        Slot editedSlot = slotInList.withPet(VALID_NAME_COCO).withDateTime(VALID_DATETIME_COCO)
                .withDuration(VALID_DURATION_COCO).build();

        EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder().withPet(VALID_NAME_COCO)
                .withDateTime(VALID_DATETIME_COCO).withDuration(VALID_DURATION_COCO).build();
        EditSlotCommand editCommand = new EditSlotCommand(indexLastSlot, descriptor, "");

        String expectedMessage = String.format(EditSlotCommand.MESSAGE_EDIT_SLOT_SUCCESS, editedSlot);

        Model expectedModel = new ModelManager(new PetTracker(model.getPetTracker()), new UserPrefs());
        expectedModel.setSlot(lastSlot, editedSlot);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditSlotCommand editCommand = new EditSlotCommand(INDEX_FIRST_SLOT, new EditSlotDescriptor(), "");
        Slot editedSlot = model.getFilteredSlotList().get(INDEX_FIRST_SLOT.getZeroBased());

        String expectedMessage = String.format(EditSlotCommand.MESSAGE_EDIT_SLOT_SUCCESS, editedSlot);

        Model expectedModel = new ModelManager(new PetTracker(model.getPetTracker()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showSlotAtIndex(model, INDEX_FIRST_SLOT);

        Slot slotInFilteredList = model.getFilteredSlotList().get(INDEX_FIRST_SLOT.getZeroBased());
        Slot editedSlot = new SlotBuilder(slotInFilteredList).withPet(VALID_NAME_COCO).build();
        EditSlotCommand editCommand = new EditSlotCommand(INDEX_FIRST_SLOT,
                new EditSlotDescriptorBuilder().withPet(VALID_NAME_COCO).build(), "");

        String expectedMessage = String.format(EditSlotCommand.MESSAGE_EDIT_SLOT_SUCCESS, editedSlot);

        Model expectedModel = new ModelManager(new PetTracker(model.getPetTracker()), new UserPrefs());
        expectedModel.setSlot(model.getFilteredSlotList().get(0), editedSlot);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSlotIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSlotList().size() + 1);
        EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder()
                .withPet(VALID_NAME_COCO).build();
        EditSlotCommand editCommand = new EditSlotCommand(outOfBoundIndex, descriptor, "");

        assertCommandFailure(editCommand, model, MESSAGE_INVALID_SLOT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidSlotIndexFilteredList_failure() {
        showSlotAtIndex(model, INDEX_FIRST_SLOT);
        Index outOfBoundIndex = INDEX_SECOND_SLOT;

        // ensures that outOfBoundIndex is still in bounds of pet tracker list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPetTracker().getSlotList().size());

        EditSlotCommand editCommand = new EditSlotCommand(outOfBoundIndex,
                new EditSlotDescriptorBuilder().withPet(VALID_NAME_COCO).build(), "");

        assertCommandFailure(editCommand, model, MESSAGE_INVALID_SLOT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditSlotCommand standardCommand = new EditSlotCommand(INDEX_FIRST_SLOT, SLOT_DESC_COCO, "");

        // same values -> returns true
        EditSlotDescriptor copyDescriptor = new EditSlotDescriptor(SLOT_DESC_COCO);
        EditSlotCommand commandWithSameValues = new EditSlotCommand(INDEX_FIRST_SLOT, copyDescriptor, "");
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditSlotCommand(INDEX_SECOND_SLOT, SLOT_DESC_COCO, "")));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditSlotCommand(INDEX_FIRST_SLOT, SLOT_DESC_GARFIELD, "")));
    }
}
