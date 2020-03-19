package seedu.address.logic.parser.general;

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
        switch (args.strip()) {
        case "p":
            return new DisplayCommand(DisplaySystemType.PET);
        case "s":
            return new DisplayCommand(DisplaySystemType.SCHEDULE);
        case "i":
            throw new ParseException("`display i` will be supported soon!");
        default:
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    DisplayCommand.MESSAGE_USAGE));
        }
    }
}
