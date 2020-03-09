package seedu.address.logic.petparser;

import java.util.Arrays;

import seedu.address.commons.core.PshMessages;
import seedu.address.logic.generalparser.Parser;
import seedu.address.logic.generalparser.exceptions.ParseException;
import seedu.address.logic.petcommands.FindPetCommand;
import seedu.address.model.pet.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPetCommand object
 */
public class FindPetParser implements Parser<FindPetCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPetCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(PshMessages.MESSAGE_INVALID_COMMAND_FORMAT, FindPetCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindPetCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
