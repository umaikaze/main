package seedu.address.logic.parser.slot;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.slot.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.slot.CliSyntax.PREFIX_PETNAME;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.slot.FindSlotCommand;
import seedu.address.logic.parser.general.ArgumentMultimap;
import seedu.address.logic.parser.general.ArgumentTokenizer;
import seedu.address.logic.parser.general.Parser;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.model.slot.SlotDatePredicate;
import seedu.address.model.slot.SlotPetNamePredicate;
import seedu.address.model.slot.SlotPredicate;

/**
 * Parses input arguments and creates a new FindSlotCommand object
 */
public class FindSlotParser implements Parser<FindSlotCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindSlotCommand
     * and returns a FindSlotCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindSlotCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PETNAME, PREFIX_DATETIME);

        if (argMultimap.getValue(PREFIX_PETNAME).isEmpty()
                && argMultimap.getValue(PREFIX_DATETIME).isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        List<SlotPredicate> predicates = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_PETNAME).isPresent()) {
            predicates.add(new SlotPetNamePredicate(argMultimap.getValue(PREFIX_PETNAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            predicates.add(new SlotDatePredicate(
                    SlotParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get())));
        }
        assert !(predicates.isEmpty()) : "No predicates for finding slots!";

        return new FindSlotCommand(predicates.stream()
                .reduce((pred1, pred2) -> (SlotPredicate) pred1.and(pred2))
                .get());
    }
}
