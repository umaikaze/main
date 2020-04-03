package w154.helper.logic.commands.general;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.time.LocalDateTime;

import w154.helper.logic.commands.general.exceptions.CommandException;
import w154.helper.model.Model;
import w154.helper.storage.Storage;
import w154.helper.ui.DisplaySystemType;
import w154.helper.commons.util.DateTimeUtil;
import w154.helper.logic.LogicManager;

/**
 * Saves the current state of the pet tracker in a separate JSON file.
 */
public class BackUpCommand extends Command {

    public static final String COMMAND_WORD = "backup";

    public static final String MESSAGE_SUCCESS = "Current Pet Tracker information has been backed up to %s";
    private final Storage storage;
    private final LocalDateTime now;

    public BackUpCommand(Storage storage) {
        this.storage = storage;
        now = java.time.LocalDateTime.now();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            storage.savePetTracker(model.getPetTracker(), now);
        } catch (IOException ioe) {
            throw new CommandException(LogicManager.FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, now.format(DateTimeUtil.BACK_UP_FORMAT) + ".json"),
                false, false, DisplaySystemType.NO_CHANGE, false);
    }
}
