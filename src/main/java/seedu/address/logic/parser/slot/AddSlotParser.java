package seedu.address.logic.parser.slot;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.slot.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.slot.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.slot.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.slot.CliSyntax.PREFIX_PETNAME;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Stream;

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
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_PETNAME, PREFIX_DATETIME, PREFIX_DURATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_PETNAME, PREFIX_DATETIME, PREFIX_DURATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSlotCommand.MESSAGE_USAGE));
        }

        Pet pet = SlotParserUtil.parsePet(argMultimap.getValue(PREFIX_PETNAME).get(), model);
        LocalDateTime dateTime = SlotParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
        Duration duration = SlotParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get());

        Slot slot = new Slot(pet, dateTime, duration);

        return new AddSlotCommand(slot);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
