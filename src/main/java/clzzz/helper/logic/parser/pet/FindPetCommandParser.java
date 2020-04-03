package clzzz.helper.logic.parser.pet;

import java.util.Arrays;

import clzzz.helper.commons.core.Messages;
import clzzz.helper.logic.commands.pet.FindPetCommand;
import clzzz.helper.logic.parser.general.Parser;
import clzzz.helper.logic.parser.general.exceptions.ParseException;
import clzzz.helper.model.pet.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPetCommand object
 */
public class FindPetCommandParser implements Parser<FindPetCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindPetCommand
     * and returns a FindPetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
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
