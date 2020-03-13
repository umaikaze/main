package seedu.address.logic.commands.general;

import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.model.PshModel;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class PshCommand {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code PshModel} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(PshModel model) throws CommandException;
}
