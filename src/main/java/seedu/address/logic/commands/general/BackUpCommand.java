package seedu.address.logic.commands.general;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.DateTimeUtil.BACK_UP_FORMAT;
import static seedu.address.logic.LogicManager.FILE_OPS_ERROR_MESSAGE;

import java.io.IOException;
import java.time.LocalDateTime;

import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.Storage;
import seedu.address.ui.DisplaySystemType;

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
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, now.format(BACK_UP_FORMAT) + ".json"), false, false, DisplaySystemType.NO_CHANGE, false);
    }
}
