package seedu.address.logic.parser.general;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.general.DisplayCommand;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.ui.DisplaySystemType;

/**
 * Parses input arguments and creates a new DisplayCommand Object.
 */
public class DisplayParser {
    /**
     * Parses the given {@code String} of arguments in the context of the DisplayCommand
     * and returns an DisplayCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayCommand parse(String args) throws ParseException {
        String trimmedArgs = requireNonNull(args.trim());
        try {
            DisplaySystemType type = DisplaySystemType.fromCliArg(trimmedArgs);
            return new DisplayCommand(type);
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    DisplayCommand.MESSAGE_USAGE));
        }
    }
}
