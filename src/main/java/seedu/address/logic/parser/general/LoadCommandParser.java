package seedu.address.logic.parser.general;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.general.LoadCommand;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.storage.Storage;

/**
 * Parses input arguments and creates a new LoadCommand object
 */
public class LoadCommandParser implements Parser<LoadCommand> {
    private Storage storage;

    public LoadCommandParser(Storage storage) {
        this.storage = storage;
    }
    /**
     * Parses the given {@code String} of arguments in the context of the LoadCommand
     * and returns a LoadCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoadCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
        }

        return new LoadCommand(storage, storage.getPetTrackerFilePath().resolveSibling(trimmedArgs + ".json"));
    }
}
