package clzzz.helper.logic.parser.slot;

import static clzzz.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static clzzz.helper.commons.core.Messages.MESSAGE_SLOT_NOT_IN_ONE_DAY;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_DATETIME;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_DURATION;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_INDEX;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDate;
import java.util.stream.Stream;

import clzzz.helper.commons.core.Messages;
import clzzz.helper.logic.commands.slot.AddSlotCommand;
import clzzz.helper.logic.parser.ArgumentMultimap;
import clzzz.helper.logic.parser.ArgumentTokenizer;
import clzzz.helper.logic.parser.Parser;
import clzzz.helper.logic.parser.Prefix;
import clzzz.helper.logic.parser.exceptions.ParseException;
import clzzz.helper.model.Model;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.slot.DateTime;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.model.slot.SlotDuration;

/**
 * Parses input arguments and creates a new AddSlotCommand object
 */
public class AddSlotCommandParser implements Parser<AddSlotCommand> {

    private final Model model;

    public AddSlotCommandParser(Model model) {
        this.model = model;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSlotCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_NAME, PREFIX_DATETIME, PREFIX_DURATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DATETIME, PREFIX_DURATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSlotCommand.MESSAGE_USAGE));
        }

        Pet pet = SlotParserUtil.parsePet(argMultimap.getValue(PREFIX_NAME).get(), model);
        DateTime dateTime = SlotParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
        SlotDuration duration = SlotParserUtil.parseSlotDuration(argMultimap.getValue(PREFIX_DURATION).get());

        Slot slot = new Slot(pet, dateTime, duration);

        if (!slot.isWithinOneDay()) {
            throw new ParseException(MESSAGE_SLOT_NOT_IN_ONE_DAY);
        }

        String warningMessage = "";
        if (argMultimap.getAllValues(PREFIX_NAME).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_NAME;
        }
        if (argMultimap.getAllValues(PREFIX_DATETIME).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_DATETIME;
        }
        if (argMultimap.getAllValues(PREFIX_DURATION).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_DURATION;
        }
        if (dateTime.toLocalDate().isBefore(LocalDate.EPOCH)) {
            warningMessage += Messages.WARNING_MESSAGE_DATE_TOO_EARLY;
        } else if (dateTime.toLocalDate().isAfter(LocalDate.now().plusYears(5))) {
            warningMessage += Messages.WARNING_MESSAGE_DATE_TOO_LATE;
        }

        return new AddSlotCommand(slot, warningMessage);
    }

}
