package w154.helper.logic.parser.slot;

import static w154.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static w154.helper.commons.core.Messages.MESSAGE_SLOT_NOT_IN_ONE_DAY;
import static w154.helper.logic.parser.general.CliSyntax.PREFIX_DATETIME;
import static w154.helper.logic.parser.general.CliSyntax.PREFIX_DURATION;
import static w154.helper.logic.parser.general.CliSyntax.PREFIX_INDEX;
import static w154.helper.logic.parser.general.CliSyntax.PREFIX_NAME;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import w154.helper.commons.core.Messages;
import w154.helper.logic.commands.slot.AddSlotCommand;
import w154.helper.logic.parser.general.ArgumentMultimap;
import w154.helper.logic.parser.general.ArgumentTokenizer;
import w154.helper.logic.parser.general.Parser;
import w154.helper.logic.parser.general.Prefix;
import w154.helper.logic.parser.general.exceptions.ParseException;
import w154.helper.model.Model;
import w154.helper.model.pet.Pet;
import w154.helper.model.slot.Slot;

/**
 * Parses input arguments and creates a new AddSlotCommand object
 */
public class AddSlotCommandParser implements Parser<AddSlotCommand> {

    private final Model model;

    public AddSlotCommandParser(Model model) {
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
