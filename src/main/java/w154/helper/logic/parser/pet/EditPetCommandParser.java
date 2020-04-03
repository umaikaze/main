package w154.helper.logic.parser.pet;

import static java.util.Objects.requireNonNull;
import static w154.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static w154.helper.logic.parser.general.CliSyntax.PREFIX_DOB;
import static w154.helper.logic.parser.general.CliSyntax.PREFIX_FOODLIST;
import static w154.helper.logic.parser.general.CliSyntax.PREFIX_GENDER;
import static w154.helper.logic.parser.general.CliSyntax.PREFIX_NAME;
import static w154.helper.logic.parser.general.CliSyntax.PREFIX_SPECIES;
import static w154.helper.logic.parser.general.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import w154.helper.commons.core.Messages;
import w154.helper.commons.core.index.Index;
import w154.helper.logic.commands.pet.EditPetCommand;
import w154.helper.logic.parser.general.ArgumentMultimap;
import w154.helper.logic.parser.general.ArgumentTokenizer;
import w154.helper.logic.parser.general.Parser;
import w154.helper.logic.parser.general.ParserUtil;
import w154.helper.logic.parser.general.exceptions.ParseException;
import w154.helper.model.pet.Food;
import w154.helper.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditPetCommand object
 */
public class EditPetCommandParser implements Parser<EditPetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPetCommand
     * and returns an EditPetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER, PREFIX_DOB,
                        PREFIX_SPECIES, PREFIX_FOODLIST, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPetCommand.MESSAGE_USAGE), pe);
        }

        EditPetCommand.EditPetDescriptor editPetDescriptor = new EditPetCommand.EditPetDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPetDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            editPetDescriptor.setGender(ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get()));
        }
        if (argMultimap.getValue(PREFIX_DOB).isPresent()) {
            editPetDescriptor.setDateOfBirth(ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DOB).get()));
        }
        if (argMultimap.getValue(PREFIX_SPECIES).isPresent()) {
            editPetDescriptor.setSpecies(ParserUtil.parseSpecies(argMultimap.getValue(PREFIX_SPECIES).get()));
        }
        parseFoodListForEdit(argMultimap.getAllValues(PREFIX_FOODLIST)).ifPresent(editPetDescriptor::setFoodList);
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPetDescriptor::setTags);

        if (!editPetDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPetCommand.MESSAGE_NOT_EDITED);
        }

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

        return new EditPetCommand(index, editPetDescriptor, warningMessage);
    }

    /**
     * Parses {@code Collection<String> foodList} into a {@code Set<Food>} if {@code foodList} is non-empty.
     * If {@code foodList} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Food>} containing zero food.
     */
    private Optional<Set<Food>> parseFoodListForEdit(Collection<String> foodList) throws ParseException {
        assert foodList != null;

        if (foodList.isEmpty()) {
            return Optional.empty();
        }
        if (foodList.stream().allMatch(t -> t.equals(""))) {
            throw new ParseException(EditPetCommand.MESSAGE_EMPTY_FOODLIST);
        }
        Collection<String> foodSet = foodList;
        return Optional.of(ParserUtil.parseFoodList(foodSet));
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
