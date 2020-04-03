package clzzz.helper.logic.commands.slot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.commands.CommandTestUtil;
import clzzz.helper.testutil.slot.EditSlotDescriptorBuilder;
import clzzz.helper.logic.commands.slot.EditSlotCommand.EditSlotDescriptor;

class EditSlotDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditSlotDescriptor descriptorWithSameValues = new EditSlotDescriptor(CommandTestUtil.SLOT_DESC_COCO);
        assertTrue(CommandTestUtil.SLOT_DESC_COCO.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(CommandTestUtil.SLOT_DESC_COCO.equals(CommandTestUtil.SLOT_DESC_COCO));

        // null -> returns false
        assertFalse(CommandTestUtil.SLOT_DESC_COCO.equals(null));

        // different types -> returns false
        assertFalse(CommandTestUtil.SLOT_DESC_COCO.equals(5));

        // different values -> returns false
        assertFalse(CommandTestUtil.SLOT_DESC_COCO.equals(CommandTestUtil.SLOT_DESC_GARFIELD));

        // different pet -> returns false
        EditSlotDescriptor editedCocoSlot = new EditSlotDescriptorBuilder(CommandTestUtil.SLOT_DESC_COCO)
                .withPet(CommandTestUtil.VALID_NAME_GARFIELD).build();
        assertFalse(CommandTestUtil.SLOT_DESC_COCO.equals(editedCocoSlot));

        // different datetime -> returns false
        editedCocoSlot = new EditSlotDescriptorBuilder(CommandTestUtil.SLOT_DESC_COCO)
                .withDateTime(CommandTestUtil.VALID_DATETIME_GARFIELD).build();
        assertFalse(CommandTestUtil.SLOT_DESC_COCO.equals(editedCocoSlot));

        // different duration -> returns false
        editedCocoSlot = new EditSlotDescriptorBuilder(CommandTestUtil.SLOT_DESC_COCO)
                .withDuration(CommandTestUtil.VALID_DURATION_GARFIELD).build();
        assertFalse(CommandTestUtil.SLOT_DESC_COCO.equals(editedCocoSlot));
    }
}
