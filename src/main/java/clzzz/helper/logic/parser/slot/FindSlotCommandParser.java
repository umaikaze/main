package clzzz.helper.logic.parser.slot;

import static clzzz.helper.logic.parser.general.CliSyntax.PREFIX_DATETIME;
import static clzzz.helper.logic.parser.general.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import clzzz.helper.commons.core.Messages;
import clzzz.helper.logic.parser.general.ArgumentMultimap;
import clzzz.helper.logic.parser.general.Parser;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.model.slot.SlotDatePredicate;
import clzzz.helper.model.slot.SlotPetNamePredicate;
import clzzz.helper.logic.commands.slot.FindSlotCommand;
import clzzz.helper.logic.parser.general.ArgumentTokenizer;
import clzzz.helper.logic.parser.general.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindSlotCommand object
 */
public class FindSlotCommandParser implements Parser<FindSlotCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindSlotCommand
     * and returns a FindSlotCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindSlotCommand parse(String args) throws ParseException {
        Predicate<Slot> predicates = getPredicates(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATETIME);
        String warningMessage = "";
        if (argMultimap.getAllValues(PREFIX_NAME).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_NAME;
        }
        if (argMultimap.getAllValues(PREFIX_DATETIME).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_TIME;
        }

        return new FindSlotCommand(predicates, warningMessage);
    }

    public static Predicate<Slot> getPredicates(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATETIME);

        if (argMultimap.getValue(PREFIX_NAME).isEmpty()
                && argMultimap.getValue(PREFIX_DATETIME).isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindSlotCommand.MESSAGE_USAGE));
        }

        List<Predicate<Slot>> predicates = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            if (argMultimap.getValue(PREFIX_NAME).get().trim().equals("")) {
                throw new ParseException(FindSlotCommand.MESSAGE_EMPTY_NAME_FIELD);
            }
            predicates.add(new SlotPetNamePredicate(Arrays.asList(
                    argMultimap.getValue(PREFIX_NAME).get().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            if (argMultimap.getValue(PREFIX_DATETIME).get().trim().equals("")) {
                throw new ParseException(FindSlotCommand.MESSAGE_EMPTY_DATETIME_FIELD);
            }
            predicates.add(new SlotDatePredicate(
                    SlotParserUtil.parseDates(argMultimap.getValue(PREFIX_DATETIME).get())));
        }
        assert !(predicates.isEmpty()) : "No predicates for finding slots!";

        return predicates.stream()
                .reduce(Predicate::and)
                .get();
    }
}
