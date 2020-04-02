package seedu.address.logic.commands.general;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PetTracker;
import seedu.address.model.ReadOnlyPetTracker;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.Storage;
import seedu.address.ui.DisplaySystemType;

public class LoadCommand extends Command {

    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Loads the given backup file.\n"
            + "Parameters: FILE_NAME (must be a valid file name)\n"
            + "Example: " + COMMAND_WORD + " 20200402_21_54_52";
    public static final String MESSAGE_SUCCESS = "Pet tracker loaded from %s.";
    Path filePath;
    Storage storage;

    public LoadCommand(Storage storage, Path filePath) {
        this.storage = storage;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Optional<ReadOnlyPetTracker> petTrackerOptional;
        try {
            petTrackerOptional = storage.readPetTracker(filePath);
            if (!petTrackerOptional.isPresent()) {
                throw new CommandException("Data file not found. Starting with a sample pet store helper");
            }
            model.setPetTracker(petTrackerOptional.get());
        } catch (DataConversionException e) {
            throw new CommandException("Data file not in the correct format. Starting with an empty pet store helper");
        } catch (IOException e) {
            throw new CommandException("Problem while reading from the file. Starting with an empty pet store helper");
        }

        return new CommandResult(MESSAGE_SUCCESS, false, false, DisplaySystemType.NO_CHANGE, false);
    }
}
