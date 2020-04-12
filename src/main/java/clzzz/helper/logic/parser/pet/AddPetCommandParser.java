package clzzz.helper.logic.parser.pet;

import static clzzz.helper.logic.parser.CliSyntax.PREFIX_DOB;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_FOODLIST;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_GENDER;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_NAME;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_SPECIES;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import clzzz.helper.commons.core.Messages;
import clzzz.helper.logic.commands.pet.AddPetCommand;
import clzzz.helper.logic.parser.ArgumentMultimap;
import clzzz.helper.logic.parser.ArgumentTokenizer;
import clzzz.helper.logic.parser.Parser;
import clzzz.helper.logic.parser.ParserUtil;
import clzzz.helper.logic.parser.Prefix;
import clzzz.helper.logic.parser.exceptions.ParseException;
import clzzz.helper.model.pet.DateOfBirth;
import clzzz.helper.model.pet.Food;
import clzzz.helper.model.pet.Gender;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.pet.Species;
import clzzz.helper.model.pet.Tag;

/**
 * Parses input arguments and creates a new AddPetCommand object.
 */
public class AddPetCommandParser implements Parser<AddPetCommand> {


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddPetCommand
     * and returns an AddPetCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER, PREFIX_DOB, PREFIX_SPECIES,
                        PREFIX_FOODLIST, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GENDER, PREFIX_DOB, PREFIX_FOODLIST, PREFIX_SPECIES)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPetCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
        DateOfBirth dateOfBirth = ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DOB).get());
        Species species = ParserUtil.parseSpecies(argMultimap.getValue(PREFIX_SPECIES).get());
        Set<Food> foodList = ParserUtil.parseFoodList(argMultimap.getAllValues(PREFIX_FOODLIST));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Pet pet = new Pet(name, gender, dateOfBirth, species, foodList, tagList);

        String warningMessage = "";
        if (argMultimap.getAllValues(PREFIX_NAME).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_NAME;
        }
        if (argMultimap.getAllValues(PREFIX_GENDER).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_GENDER;
        }
        if (argMultimap.getAllValues(PREFIX_SPECIES).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_SPECIES;
        }
        if (argMultimap.getAllValues(PREFIX_DOB).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_DOB;
        }
        if (dateOfBirth.value.isBefore(LocalDate.EPOCH)) {
            warningMessage += Messages.WARNING_MESSAGE_DATE_TOO_EARLY;
        } else if (dateOfBirth.value.isAfter(LocalDate.now())) {
            warningMessage += Messages.WARNING_MESSAGE_DATE_TOO_LATE;
        }
        return new AddPetCommand(pet, warningMessage);
    }

}
