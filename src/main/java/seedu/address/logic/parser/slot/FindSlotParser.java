package seedu.address.logic.parser.slot;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.slot.FindSlotCommand;
import seedu.address.logic.parser.general.PshParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.slot.SlotContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindSlotParser implements PshParser<FindSlotCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindSlotCommand
     * and returns a FindSlotCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindSlotCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSlotCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindSlotCommand(new SlotContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
