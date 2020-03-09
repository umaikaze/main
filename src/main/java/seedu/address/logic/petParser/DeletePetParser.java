package seedu.address.logic.petParser;

import seedu.address.commons.petCore.Messages;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.generalParser.ParserUtil;
import seedu.address.logic.petCommands.DeletePetCommand;
import seedu.address.logic.generalParser.exceptions.ParseException;

public class DeletePetParser {
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
