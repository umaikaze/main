package clzzz.helper.logic.commands.slot;

import static clzzz.helper.logic.parser.CliSyntax.PREFIX_DATETIME;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_DURATION;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import clzzz.helper.commons.core.Messages;
import clzzz.helper.commons.core.index.Index;
import clzzz.helper.commons.util.CollectionUtil;
import clzzz.helper.logic.commands.Command;
import clzzz.helper.logic.commands.CommandResult;
import clzzz.helper.logic.commands.exceptions.CommandException;
import clzzz.helper.model.Model;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.slot.DateTime;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.model.slot.SlotDuration;

/**
 * Edits the details of an slot in the schedule.
 */
public class EditSlotCommand extends Command {

    public static final String COMMAND_WORD = "editslot";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the slot identified "
            + "by the index number used in the displayed slots list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DATETIME + "DATETIME] "
            + "[" + PREFIX_DURATION + "DURATION]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DURATION + "360";

    public static final String MESSAGE_EDIT_SLOT_SUCCESS = "Edited slot: %1$s\n";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditSlotDescriptor editSlotDescriptor;
    private final String warningMessage;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editSlotDescriptor details to edit the person with
     */
    public EditSlotCommand(Index index, EditSlotDescriptor editSlotDescriptor, String warningMessage) {
        requireNonNull(index);
        requireNonNull(editSlotDescriptor);

        this.index = index;
        this.editSlotDescriptor = new EditSlotDescriptor(editSlotDescriptor);
        this.warningMessage = warningMessage;
    }

    /**
     * Creates and returns a {@code Slot} with the details of {@code slotToEdit}
     * edited with {@code editSlotDescriptor}.
     */
    private static Slot createEditedSlot(Slot slotToEdit, EditSlotDescriptor editSlotDescriptor) {
        assert slotToEdit != null;

        Pet updatedPet = editSlotDescriptor.getPet().orElse(slotToEdit.getPet());
        DateTime updatedDateTime = editSlotDescriptor.getDateTime().orElse(slotToEdit.getDateTime());
        SlotDuration updatedDuration = editSlotDescriptor.getDuration().orElse(slotToEdit.getDuration());

        return new Slot(updatedPet, updatedDateTime, updatedDuration);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Slot> lastShownList = model.getFilteredSlotList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SLOT_DISPLAYED_INDEX);
        }

        Slot slotToEdit = lastShownList.get(index.getZeroBased());
        Slot editedSlot = createEditedSlot(slotToEdit, editSlotDescriptor);

        if (!editedSlot.isWithinOneDay()) {
            throw new CommandException(Messages.MESSAGE_SLOT_NOT_IN_ONE_DAY);
        }

        model.setSlot(slotToEdit, editedSlot);
        model.updateFilteredSlotList(Model.PREDICATE_SHOW_ALL_SLOTS);
        return new CommandResult(String.format(MESSAGE_EDIT_SLOT_SUCCESS, editedSlot) + warningMessage);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditSlotCommand)) {
            return false;
        }

        // state check
        EditSlotCommand e = (EditSlotCommand) other;
        return index.equals(e.index)
                && editSlotDescriptor.equals(e.editSlotDescriptor)
                && warningMessage.equals(e.warningMessage);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditSlotDescriptor {
        private Pet pet;
        private DateTime dateTime;
        private SlotDuration duration;

        public EditSlotDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditSlotDescriptor(EditSlotDescriptor toCopy) {
            setPet(toCopy.pet);
            setDateTime(toCopy.dateTime);
            setDuration(toCopy.duration);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(pet, dateTime, duration);
        }

        public Optional<Pet> getPet() {
            return Optional.ofNullable(pet);
        }

        public void setPet(Pet pet) {
            this.pet = pet;
        }

        public Optional<DateTime> getDateTime() {
            return Optional.ofNullable(dateTime);
        }

        public void setDateTime(DateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Optional<SlotDuration> getDuration() {
            return Optional.ofNullable(duration);
        }

        public void setDuration(SlotDuration duration) {
            this.duration = duration;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditSlotDescriptor)) {
                return false;
            }

            // state check
            EditSlotDescriptor e = (EditSlotDescriptor) other;

            return getPet().equals(e.getPet())
                    && getDateTime().equals(e.getDateTime())
                    && getDuration().equals(e.getDuration());
        }
    }
}
