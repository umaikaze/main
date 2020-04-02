package seedu.address.logic.commands.general;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.LogicManager.FILE_OPS_ERROR_MESSAGE;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.Storage;
import seedu.address.ui.DisplaySystemType;

/**
 * Saves the current state of the pet tracker in a separate JSON file.
 */
public class BackUpCommand extends Command {

    public static final String COMMAND_WORD = "backup";

    public static final String MESSAGE_SUCCESS = "Current Pet Tracker information has been backed up.";
    Storage storage;

    public BackUpCommand(Storage storage) {
        this.storage = storage;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            storage.savePetTracker(model.getPetTracker(), java.time.LocalDateTime.now());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
        return new CommandResult(MESSAGE_SUCCESS, false, false, DisplaySystemType.NO_CHANGE, false);
    }
}
