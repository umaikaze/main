package clzzz.helper.logic.parser;

import static clzzz.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import clzzz.helper.logic.commands.DisplayCommand;
import clzzz.helper.logic.parser.exceptions.ParseException;
import clzzz.helper.ui.DisplaySystemType;

/**
 * Parses input arguments and creates a new DisplayCommand Object.
 */
public class DisplayCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the DisplayCommand
     * and returns an DisplayCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayCommand parse(String args) throws ParseException {
        try {
            DisplaySystemType type = ParserUtil.parseDisplaySystemType(args);
            if (!type.equals(DisplaySystemType.PETS)
                    && !type.equals(DisplaySystemType.SCHEDULE)
                    && !type.equals(DisplaySystemType.INVENTORY)
                    && !type.equals(DisplaySystemType.CALENDAR)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE));
            }
            return new DisplayCommand(type);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE), pe);
        }
    }
}
