package seedu.address.logic.parser.slot;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_SLOT_NOT_IN_ONE_DAY;
import static seedu.address.logic.parser.general.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.general.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.general.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.general.CliSyntax.PREFIX_NAME;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.slot.AddSlotCommand;
import seedu.address.logic.parser.general.ArgumentMultimap;
import seedu.address.logic.parser.general.ArgumentTokenizer;
import seedu.address.logic.parser.general.Parser;
import seedu.address.logic.parser.general.Prefix;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;

/**
 * Parses input arguments and creates a new AddSlotCommand object
 */
public class AddSlotParser implements Parser<AddSlotCommand> {

    private final Model model;

    public AddSlotParser(Model model) {
        this.model = model;
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
        LocalDateTime dateTime = SlotParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
        Duration duration = SlotParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get());

        Slot slot = new Slot(pet, dateTime, duration);

        if (!slot.isWithinOneDay()) {
            throw new ParseException(MESSAGE_SLOT_NOT_IN_ONE_DAY);
        }

        String warningMessage = "";
        if (argMultimap.getAllValues(PREFIX_NAME).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_NAME;
        }
        if (argMultimap.getAllValues(PREFIX_DATETIME).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_TIME;
        }
        if (argMultimap.getAllValues(PREFIX_DURATION).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_DURATION;
        }
        if (dateTime.toLocalDate().isBefore(LocalDate.EPOCH)) {
            warningMessage += Messages.WARNING_MESSAGE_DATE_TOO_EARLY;
        }

        return new AddSlotCommand(slot, warningMessage);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
