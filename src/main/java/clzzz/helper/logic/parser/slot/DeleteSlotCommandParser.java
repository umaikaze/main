package clzzz.helper.logic.parser.slot;

import static clzzz.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import clzzz.helper.commons.core.index.Index;
import clzzz.helper.logic.commands.slot.DeleteSlotCommand;
import clzzz.helper.logic.parser.Parser;
import clzzz.helper.logic.parser.ParserUtil;
import clzzz.helper.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteSlotCommand object
 */
public class DeleteSlotCommandParser implements Parser<DeleteSlotCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteSlotCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteSlotCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSlotCommand.MESSAGE_USAGE), pe);
        }
    }

}
