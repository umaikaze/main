package seedu.address.logic.parser.slot;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;

import seedu.address.logic.commands.slot.FindSlotCommand;
import seedu.address.logic.parser.general.PshParser;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.model.slot.SlotDatePredicate;
import seedu.address.model.slot.SlotPetNamePredicate;
import seedu.address.model.slot.SlotPredicate;

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
        return new FindSlotCommand(getSlotPredicate(trimmedArgs));
    }

    private SlotPredicate[] getSlotPredicate(String trimmedArgs) throws ParseException {
        if (!trimmedArgs.contains("/")) {
            return new SlotPredicate[]{new SlotPetNamePredicate(trimmedArgs)}; // Shortcut if no date specified
        }
        if (!trimmedArgs.contains("\\s+")) {
            return new SlotPredicate[]{new SlotDatePredicate(SlotParserUtil
                    .parseDateTime(trimmedArgs))}; // If have slash but no space, assume date only
        }

        // To find separate strings for petName and date

        String[] keywords = trimmedArgs.split("\\s+");
        StringBuilder petName = new StringBuilder();
        LocalDateTime date = null;
        for (int i = 0; i < keywords.length; i++) {
            String trimmedWord = keywords[i].trim();
            if (trimmedWord.contains("/") && date == null) {
                date = SlotParserUtil.parseDateTime(trimmedWord);
            } else {
                petName.append(trimmedWord);
                if (i != keywords.length - 1) {
                    petName.append(" ");
                }
            }
        }
        return new SlotPredicate[]{new SlotPetNamePredicate(petName.toString()), new SlotDatePredicate(date)};
    }
}
