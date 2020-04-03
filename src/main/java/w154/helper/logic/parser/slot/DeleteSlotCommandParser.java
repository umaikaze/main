package w154.helper.logic.parser.slot;

import static w154.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import w154.helper.commons.core.index.Index;
import w154.helper.logic.commands.slot.DeleteSlotCommand;
import w154.helper.logic.parser.general.Parser;
import w154.helper.logic.parser.general.ParserUtil;
import w154.helper.logic.parser.general.exceptions.ParseException;

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
