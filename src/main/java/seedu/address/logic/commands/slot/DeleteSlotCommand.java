package seedu.address.logic.commands.slot;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.general.Command;
import seedu.address.logic.commands.general.CommandResult;
import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.slot.Slot;

/**
 * Deletes a slot identified using it's displayed index from the address book.
 */
public class DeleteSlotCommand extends Command {

    public static final String COMMAND_WORD = "deleteslot";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the slot identified by the index number used in the displayed slots list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SLOT_SUCCESS = "Deleted slot: %1$s";

    private final Index targetIndex;

    public DeleteSlotCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Slot> lastShownList = model.getFilteredSlotList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
        }

        Slot slotToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteSlot(slotToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_SLOT_SUCCESS, slotToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteSlotCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteSlotCommand) other).targetIndex)); // state check
    }
}
