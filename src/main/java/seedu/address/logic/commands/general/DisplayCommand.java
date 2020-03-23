package seedu.address.logic.commands.general;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.DisplaySystemType;

/**
 * Displays the specified system.
 */
public class DisplayCommand extends Command {

    public static final String COMMAND_WORD = "display";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the display board to show the specified system.\n"
            + "Parameter: SYSTEM (must be p (pets) or s (schedule)).\n"
            + "Example: display p";

    public static final String MESSAGE_SUCCESS = "Display changed to %s; showing all.";

    public static final String MESSAGE_INVALID_SYSTEM_TYPE = "Invalid system type specified.";

    private final DisplaySystemType type;

    public DisplayCommand(DisplaySystemType type) {
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.changeDisplaySystem(type);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_INVALID_SYSTEM_TYPE);
        }
        String message = String.format(MESSAGE_SUCCESS, type);
        return new CommandResult(message, false, false, true);
    }
}
