package seedu.address.logic.commands.slot;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.PshModel.PREDICATE_SHOW_ALL_SLOTS;

import seedu.address.logic.commands.general.CommandResult;
import seedu.address.logic.commands.general.PshCommand;
import seedu.address.model.PshModel;

/**
 * Lists all slots in the schedule to the user.
 */
public class ListSlotCommand extends PshCommand {

    public static final String COMMAND_WORD = "listslots";

    public static final String MESSAGE_SUCCESS = "Listed all slots";

    @Override
    public CommandResult execute(PshModel model) {
        requireNonNull(model);
        model.updateFilteredSlotList(PREDICATE_SHOW_ALL_SLOTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
