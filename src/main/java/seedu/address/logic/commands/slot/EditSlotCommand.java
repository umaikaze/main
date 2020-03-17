package seedu.address.logic.commands.slot;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.slot.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.slot.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.slot.CliSyntax.PREFIX_PETNAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SLOTS;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.general.Command;
import seedu.address.logic.commands.general.CommandResult;
import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;

/**
 * Edits the details of an slot in the schedule.
 */
public class EditSlotCommand extends Command {

    public static final String COMMAND_WORD = "editslot";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the slot identified "
            + "by the index number used in the displayed slots list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_PETNAME + "NAME] "
            + "[" + PREFIX_DATETIME + "DATETIME] "
            + "[" + PREFIX_DURATION + "DURATION]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PETNAME + "Garfield "
            + PREFIX_DURATION + "360";

    public static final String MESSAGE_EDIT_SLOT_SUCCESS = "Edited slot: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditSlotDescriptor editSlotDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editSlotDescriptor details to edit the person with
     */
    public EditSlotCommand(Index index, EditSlotDescriptor editSlotDescriptor) {
        requireNonNull(index);
        requireNonNull(editSlotDescriptor);

        this.index = index;
        this.editSlotDescriptor = new EditSlotDescriptor(editSlotDescriptor);
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

        model.setSlot(slotToEdit, editedSlot);
        model.updateFilteredSlotList(PREDICATE_SHOW_ALL_SLOTS);
        return new CommandResult(String.format(MESSAGE_EDIT_SLOT_SUCCESS, editedSlot));
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
        if (!(other instanceof EditSlotCommand)) {
            return false;
        }

        // state check
        EditSlotCommand e = (EditSlotCommand) other;
        return index.equals(e.index)
                && editSlotDescriptor.equals(e.editSlotDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditSlotDescriptor {
        private Pet pet;
        private LocalDateTime dateTime;
        private Duration duration;

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

        public void setPet(Pet pet) {
            this.pet = pet;
        }

        public Optional<Pet> getPet() {
            return Optional.ofNullable(pet);
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Optional<LocalDateTime> getDateTime() {
            return Optional.ofNullable(dateTime);
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public Optional<Duration> getDuration() {
            return Optional.ofNullable(duration);
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
