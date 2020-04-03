package w154.helper.logic.parser.general;

import w154.helper.logic.commands.general.DisplayCommand;
import w154.helper.logic.parser.general.exceptions.ParseException;
import w154.helper.commons.core.Messages;

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
            return new DisplayCommand(ParserUtil.parseDisplaySystemType(args));
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE), pe);
        }
    }
}
