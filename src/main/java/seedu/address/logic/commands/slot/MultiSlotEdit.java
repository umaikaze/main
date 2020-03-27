package seedu.address.logic.commands.slot;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SLOTS;

import java.time.Duration;
import java.time.LocalDateTime;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.general.CommandResult;
import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.logic.commands.slot.EditSlotCommand.EditSlotDescriptor;
import seedu.address.model.Model;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;

/**
 * Edits the details of slots in the schedule that was occupied by the pet before edit.
 */
public class MultiSlotEdit {

    public static final String ALL_SLOTS_OCCUPIED_BY_PET_EDITED = "%1$s\n all slots occupied by pet edited";

    private final String editPetMessage;
    private final Slot[] slotsToEdit;
    private final EditSlotDescriptor[] editSlotDescriptors;

    /**
     * @param slotsToEdit list of slots affected
     * @param editSlotDescriptors details to edit the slots with
     */
    public MultiSlotEdit(CommandResult editPetResult, Slot[] slotsToEdit,
                         EditSlotDescriptor[] editSlotDescriptors) {
        requireNonNull(editPetResult);
        requireNonNull(slotsToEdit);
        requireNonNull(editSlotDescriptors);

        this.editPetMessage = editPetResult.getFeedbackToUser();
        this.slotsToEdit = slotsToEdit;
        this.editSlotDescriptors = editSlotDescriptors;
    }

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Slot[] editedSlots = new Slot[slotsToEdit.length];

        for (int i = 0; i < slotsToEdit.length; i++) {
            editedSlots[i] = createEditedSlot(slotsToEdit[i], editSlotDescriptors[i]);
            if (!editedSlots[i].isWithinOneDay()) {
                throw new CommandException(Messages.MESSAGE_SLOT_NOT_IN_ONE_DAY);
            }
        }

        for (int i = 0; i < slotsToEdit.length; i++) {
            model.setSlot(slotsToEdit[i], editedSlots[i]);
        }

        model.updateFilteredSlotList(PREDICATE_SHOW_ALL_SLOTS);
        return new CommandResult(String.format(ALL_SLOTS_OCCUPIED_BY_PET_EDITED, editPetMessage));
    }

    /**
     * Creates and returns a {@code Slot} with the details of {@code slotToEdit}
     * edited with {@code editSlotDescriptor}.
     */
    private static Slot createEditedSlot(Slot slotToEdit, EditSlotDescriptor editSlotDescriptor) {
        assert slotToEdit != null;

        Pet updatedPet = editSlotDescriptor.getPet().orElse(slotToEdit.getPet());
        LocalDateTime updatedDateTime = editSlotDescriptor.getDateTime().orElse(slotToEdit.getDateTime());
        Duration updatedDuration = editSlotDescriptor.getDuration().orElse(slotToEdit.getDuration());

        return new Slot(updatedPet, updatedDateTime, updatedDuration);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MultiSlotEdit)) {
            return false;
        }

        // state check
        MultiSlotEdit e = (MultiSlotEdit) other;
        return slotsToEdit.equals(e.slotsToEdit)
                && editSlotDescriptors.equals(e.editSlotDescriptors);
    }
}
