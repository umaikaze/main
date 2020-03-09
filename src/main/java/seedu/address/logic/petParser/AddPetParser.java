package seedu.address.logic.petparser;

import static seedu.address.logic.petparser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.petparser.CliSyntax.PREFIX_FOODLIST;
import static seedu.address.logic.petparser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.petparser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.petparser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.petparser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.PshMessages;
import seedu.address.logic.generalparser.ArgumentMultimap;
import seedu.address.logic.generalparser.ArgumentTokenizer;
import seedu.address.logic.generalparser.Parser;
import seedu.address.logic.generalparser.ParserUtil;
import seedu.address.logic.generalparser.Prefix;
import seedu.address.logic.generalparser.exceptions.ParseException;
import seedu.address.logic.petcommands.AddPetCommand;
import seedu.address.model.pet.DateOfBirth;
import seedu.address.model.pet.Food;
import seedu.address.model.pet.Gender;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.Species;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddPetCommand object.
 */
public class AddPetParser implements Parser<AddPetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPetCommand
     * and returns an AddPetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER, PREFIX_DOB, PREFIX_SPECIES,
                        PREFIX_FOODLIST, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GENDER, PREFIX_DOB, PREFIX_SPECIES, PREFIX_FOODLIST)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(PshMessages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPetCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
        DateOfBirth dateOfBirth = ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DOB).get());
        Species species = ParserUtil.parseSpecies(argMultimap.getValue(PREFIX_SPECIES).get());
        Set<Food> foodList = ParserUtil.parseFoodList(argMultimap.getAllValues(CliSyntax.PREFIX_FOODLIST));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(CliSyntax.PREFIX_TAG));

        Pet pet = new Pet(name, gender, dateOfBirth, species, foodList, tagList);

        return new AddPetCommand(pet);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
