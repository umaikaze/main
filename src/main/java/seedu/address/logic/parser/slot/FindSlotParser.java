package seedu.address.logic.parser.slot;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.slot.FindSlotCommand;
import seedu.address.logic.parser.general.PshParser;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.model.slot.SlotDatePredicate;
import seedu.address.model.slot.SlotPetNamePredicate;

/**
 * Parses input arguments and creates a new FindSlotCommand object
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
        if (!trimmedArgs.contains("/")) {
            return new FindSlotCommand(new SlotPetNamePredicate(trimmedArgs));
        } else {
            return new FindSlotCommand(new SlotDatePredicate(SlotParserUtil.parseDateTime(trimmedArgs)));
        }
    }

}
