package seedu.address.logic.petParser;

import java.util.Arrays;

import seedu.address.commons.petCore.Messages;
import seedu.address.logic.generalParser.Parser;
import seedu.address.logic.generalParser.exceptions.ParseException;
import seedu.address.logic.petCommands.FindPetCommand;
import seedu.address.model.pet.NameContainsKeywordsPredicate;

public class FindPetParser implements Parser<FindPetCommand> {
    public FindPetCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindPetCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindPetCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
