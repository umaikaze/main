package seedu.address.logic.generalCommands;

import seedu.address.logic.generalCommands.CommandResult;
import seedu.address.model.PshModel;

public class ExitCommand extends Command{

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Pet Store Helper as requested ...";

    @Override
    public CommandResult execute(PshModel model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }
}
