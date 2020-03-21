package seedu.address.logic.parser.slot;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.general.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.general.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.slot.FindSlotCommand;
import seedu.address.logic.parser.general.ArgumentMultimap;
import seedu.address.logic.parser.general.ArgumentTokenizer;
import seedu.address.logic.parser.general.Parser;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.model.slot.Slot;
import seedu.address.model.slot.SlotDatePredicate;
import seedu.address.model.slot.SlotPetNamePredicate;

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

        return new FindSlotCommand(getPredicates(args));
    }

    public static Predicate<Slot> getPredicates(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATETIME);

        if (argMultimap.getValue(PREFIX_NAME).isEmpty()
                && argMultimap.getValue(PREFIX_DATETIME).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSlotCommand.MESSAGE_USAGE));
        }

        List<Predicate<Slot>> predicates = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            predicates.add(new SlotPetNamePredicate(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            predicates.add(new SlotDatePredicate(
                    SlotParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get())));
        }
        assert !(predicates.isEmpty()) : "No predicates for finding slots!";

        return predicates.stream()
                .reduce(Predicate::and)
                .get();
    }
}
