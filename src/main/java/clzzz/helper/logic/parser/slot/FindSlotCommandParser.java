package clzzz.helper.logic.parser.slot;

import static clzzz.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static clzzz.helper.commons.core.Messages.WARNING_MESSAGE_DATETIME;
import static clzzz.helper.commons.core.Messages.WARNING_MESSAGE_NAME;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_DATETIME;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import clzzz.helper.logic.commands.slot.FindSlotCommand;
import clzzz.helper.logic.parser.ArgumentMultimap;
import clzzz.helper.logic.parser.ArgumentTokenizer;
import clzzz.helper.logic.parser.Parser;
import clzzz.helper.logic.parser.exceptions.ParseException;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.model.slot.SlotDatePredicate;
import clzzz.helper.model.slot.SlotPetNamePredicate;

/**
 * Parses input arguments and creates a new FindSlotCommand object
 */
public class FindSlotCommandParser implements Parser<FindSlotCommand> {

    public static Predicate<Slot> getPredicates(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATETIME);

        if (argMultimap.getValue(PREFIX_NAME).isEmpty()
                && argMultimap.getValue(PREFIX_DATETIME).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSlotCommand.MESSAGE_USAGE));
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
            warningMessage += WARNING_MESSAGE_NAME;
        }
        if (argMultimap.getAllValues(PREFIX_DATETIME).size() > 1) {
            warningMessage += WARNING_MESSAGE_DATETIME;
        }

        return new FindSlotCommand(predicates, warningMessage);
    }
}
