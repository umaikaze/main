package clzzz.helper.logic.commands.slot;

import static java.util.Objects.requireNonNull;

import java.util.List;

import clzzz.helper.commons.core.Messages;
import clzzz.helper.commons.core.index.Index;
import clzzz.helper.logic.commands.Command;
import clzzz.helper.logic.commands.CommandResult;
import clzzz.helper.logic.commands.exceptions.CommandException;
import clzzz.helper.model.Model;
import clzzz.helper.model.slot.Slot;

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
            throw new CommandException(Messages.MESSAGE_INVALID_SLOT_DISPLAYED_INDEX);
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
