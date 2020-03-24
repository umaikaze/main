package seedu.address.logic.commands.slot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.getSlotDescCoco;
import static seedu.address.logic.commands.CommandTestUtil.getSlotDescGarfield;
import static seedu.address.testutil.pet.TypicalPets.GARFIELD;
import static seedu.address.testutil.pet.TypicalPets.getTypicalModelManager;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.slot.EditSlotCommand.EditSlotDescriptor;
import seedu.address.model.Model;
import seedu.address.testutil.slot.EditSlotDescriptorBuilder;

class EditSlotDescriptorTest {

    private Model model = getTypicalModelManager();

    @Test
    public void equals() {
        // same values -> returns true
        EditSlotDescriptor descriptorWithSameValues = new EditSlotDescriptor(getSlotDescCoco(model));
        assertTrue(getSlotDescCoco(model).equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(getSlotDescCoco(model).equals(getSlotDescCoco(model)));

        // null -> returns false
        assertFalse(getSlotDescCoco(model).equals(null));

        // different types -> returns false
        assertFalse(getSlotDescCoco(model).equals(5));

        // different values -> returns false
        assertFalse(getSlotDescCoco(model).equals(getSlotDescGarfield(model)));

        // different pet -> returns false
        EditSlotDescriptor editedCocoSlot = new EditSlotDescriptorBuilder(getSlotDescCoco(model))
                .withPet(GARFIELD).build();
        assertFalse(getSlotDescCoco(model).equals(editedCocoSlot));

        // different datetime -> returns false
        editedCocoSlot = new EditSlotDescriptorBuilder(getSlotDescCoco(model))
                .withDateTime(VALID_DATETIME_GARFIELD).build();
        assertFalse(getSlotDescCoco(model).equals(editedCocoSlot));

        // different duration -> returns false
        editedCocoSlot = new EditSlotDescriptorBuilder(getSlotDescCoco(model))
                .withDuration(VALID_DURATION_GARFIELD).build();
        assertFalse(getSlotDescCoco(model).equals(editedCocoSlot));
    }
}
