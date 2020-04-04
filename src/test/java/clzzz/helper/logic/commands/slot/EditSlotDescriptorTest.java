package clzzz.helper.logic.commands.slot;

import static clzzz.helper.logic.commands.CommandTestUtil.SLOT_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.SLOT_DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DATETIME_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DURATION_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_NAME_GARFIELD;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.commands.slot.EditSlotCommand.EditSlotDescriptor;
import clzzz.helper.testutil.slot.EditSlotDescriptorBuilder;

class EditSlotDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditSlotDescriptor descriptorWithSameValues = new EditSlotDescriptor(SLOT_DESC_COCO);
        assertTrue(SLOT_DESC_COCO.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(SLOT_DESC_COCO.equals(SLOT_DESC_COCO));

        // null -> returns false
        assertFalse(SLOT_DESC_COCO.equals(null));

        // different types -> returns false
        assertFalse(SLOT_DESC_COCO.equals(5));

        // different values -> returns false
        assertFalse(SLOT_DESC_COCO.equals(SLOT_DESC_GARFIELD));

        // different pet -> returns false
        EditSlotDescriptor editedCocoSlot = new EditSlotDescriptorBuilder(SLOT_DESC_COCO)
                .withPet(VALID_NAME_GARFIELD).build();
        assertFalse(SLOT_DESC_COCO.equals(editedCocoSlot));

        // different datetime -> returns false
        editedCocoSlot = new EditSlotDescriptorBuilder(SLOT_DESC_COCO)
                .withDateTime(VALID_DATETIME_GARFIELD).build();
        assertFalse(SLOT_DESC_COCO.equals(editedCocoSlot));

        // different duration -> returns false
        editedCocoSlot = new EditSlotDescriptorBuilder(SLOT_DESC_COCO)
                .withDuration(VALID_DURATION_GARFIELD).build();
        assertFalse(SLOT_DESC_COCO.equals(editedCocoSlot));
    }
}
