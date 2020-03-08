package seedu.address.logic.petParser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.petParser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.petParser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.petParser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.petParser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.petParser.CliSyntax.PREFIX_FOODLIST;
import static seedu.address.logic.petParser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.generalParser.ArgumentMultimap;
import seedu.address.logic.generalParser.ArgumentTokenizer;
import seedu.address.logic.generalParser.Parser;
import seedu.address.logic.generalParser.exceptions.ParseException;
import seedu.address.logic.petCommands.EditPetCommand;
import seedu.address.model.pet.Food;
import seedu.address.model.tag.Tag;

public class EditPetParser implements Parser<EditPetCommand> {

    public EditPetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER, PREFIX_DOB, PREFIX_SPECIES, PREFIX_FOODLIST, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
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

        return new EditPetCommand(index, editPetDescriptor);
    }

    private Optional<Set<Food>> parseFoodListForEdit(Collection<String> foodList) throws ParseException {
        assert foodList != null;

        if (foodList.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> foodSet = foodList.size() == 1 && foodList.contains("") ? Collections.emptySet() : foodList;
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
