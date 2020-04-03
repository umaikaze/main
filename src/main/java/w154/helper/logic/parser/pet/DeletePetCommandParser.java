package w154.helper.logic.parser.pet;

import w154.helper.commons.core.Messages;
import w154.helper.commons.core.index.Index;
import w154.helper.logic.commands.pet.DeletePetCommand;
import w154.helper.logic.parser.general.Parser;
import w154.helper.logic.parser.general.ParserUtil;
import w154.helper.logic.parser.general.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeletePetCommand object
 */
public class DeletePetCommandParser implements Parser<DeletePetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePetCommand
     * and returns a DeletePetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePetCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeletePetCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeletePetCommand.MESSAGE_USAGE), pe);
        }
    }
}
