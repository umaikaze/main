package clzzz.helper.logic.commands;

import static clzzz.helper.model.Model.PREDICATE_SHOW_ALL_PETS;
import static clzzz.helper.model.Model.PREDICATE_SHOW_ALL_SLOTS;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import clzzz.helper.commons.exceptions.DataConversionException;
import clzzz.helper.logic.commands.exceptions.CommandException;
import clzzz.helper.model.Model;
import clzzz.helper.model.ReadOnlyPetTracker;
import clzzz.helper.storage.Storage;

/**
 * Loads a pet tracker from a file.
 */
public class LoadCommand extends Command {

    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Loads the given backup file.\n"
            + "Parameters: FILE_NAME (must be a valid file name)\n"
            + "Example: " + COMMAND_WORD + " 20200402_21_54_52";
    public static final String MESSAGE_SUCCESS = "Pet tracker loaded from %s.";
    public static final String MESSAGE_FILE_NOT_FOUND = "Data file not found";
    public static final String MESSAGE_WRONG_FORMAT = "Data file not in the correct format";
    public static final String MESSAGE_FILE_OPS_ERROR = "Problem while reading from the file";
    private final Path filePath;
    private final Storage storage;

    /**
     * Creates a LoadCommand to load the specified {@code Path}
     */
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
                throw new CommandException(MESSAGE_FILE_NOT_FOUND);
            }
            model.setPetTracker(petTrackerOptional.get());
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_WRONG_FORMAT);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_OPS_ERROR);
        }


        model.updateFilteredPetList(PREDICATE_SHOW_ALL_PETS);
        model.updateFilteredSlotList(PREDICATE_SHOW_ALL_SLOTS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath.getFileName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoadCommand // instanceof handles nulls
                && filePath.equals(((LoadCommand) other).filePath)); // state check
    }
}
