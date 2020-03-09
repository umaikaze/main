package seedu.address.logic.petparser;

import seedu.address.commons.core.PshMessages;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.generalparser.ParserUtil;
import seedu.address.logic.generalparser.exceptions.ParseException;
import seedu.address.logic.petcommands.DeletePetCommand;

/**
 * Parses input arguments and creates a new DeletePetCommand object
 */
public class DeletePetParser {

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
                    String.format(PshMessages.MESSAGE_INVALID_COMMAND_FORMAT, DeletePetCommand.MESSAGE_USAGE), pe);
        }
    }
}
