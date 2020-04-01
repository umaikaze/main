package seedu.address.logic.commands.general;

import static java.util.Objects.requireNonNull;
import seedu.address.model.Model;
import seedu.address.ui.DisplaySystemType;

/**
 * Saves the current state of the pet tracker in a separate JSON file.
 */
public class BackUpCommand extends Command {

    public static final String COMMAND_WORD = "backup";

    public static final String MESSAGE_SUCCESS  = "Current Pet Tracker information has been backed up.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, false, false, DisplaySystemType.NO_CHANGE, false, true);
    }
}
