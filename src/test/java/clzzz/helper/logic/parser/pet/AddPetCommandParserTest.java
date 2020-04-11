package clzzz.helper.logic.parser.pet;

import static clzzz.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static clzzz.helper.commons.core.Messages.WARNING_MESSAGE_DOB;
import static clzzz.helper.commons.core.Messages.WARNING_MESSAGE_GENDER;
import static clzzz.helper.commons.core.Messages.WARNING_MESSAGE_NAME;
import static clzzz.helper.commons.core.Messages.WARNING_MESSAGE_SPECIES;
import static clzzz.helper.logic.commands.CommandTestUtil.DOB_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.DOB_DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.FOOD_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.FOOD_DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.GENDER_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.GENDER_DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.INVALID_DOB_DESC;
import static clzzz.helper.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static clzzz.helper.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static clzzz.helper.logic.commands.CommandTestUtil.INVALID_SPECIES_DESC;
import static clzzz.helper.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static clzzz.helper.logic.commands.CommandTestUtil.NAME_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.NAME_DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static clzzz.helper.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static clzzz.helper.logic.commands.CommandTestUtil.SPECIES_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.SPECIES_DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.TAG_DESC_FAT;
import static clzzz.helper.logic.commands.CommandTestUtil.TAG_DESC_LAZY;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DOB_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_GENDER_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_NAME_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_SPECIES_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_TAG_FAT;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_TAG_LAZY;
import static clzzz.helper.logic.parser.CommandParserTestUtil.assertParseFailure;
import static clzzz.helper.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static clzzz.helper.testutil.pet.TypicalPets.COCO;
import static clzzz.helper.testutil.pet.TypicalPets.GARFIELD;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.commands.pet.AddPetCommand;
import clzzz.helper.model.pet.DateOfBirth;
import clzzz.helper.model.pet.Gender;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.pet.Species;
import clzzz.helper.model.pet.Tag;
import clzzz.helper.testutil.pet.PetBuilder;

public class AddPetCommandParserTest {
    private AddPetCommandParser parser = new AddPetCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Pet expectedPet = new PetBuilder(GARFIELD).withTags(VALID_TAG_FAT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_GARFIELD + GENDER_DESC_GARFIELD + DOB_DESC_GARFIELD
                + SPECIES_DESC_GARFIELD + FOOD_DESC_GARFIELD + TAG_DESC_FAT, new AddPetCommand(expectedPet, ""));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_COCO + NAME_DESC_GARFIELD + GENDER_DESC_GARFIELD + DOB_DESC_GARFIELD
                + SPECIES_DESC_GARFIELD + FOOD_DESC_GARFIELD + TAG_DESC_FAT,
                new AddPetCommand(expectedPet, WARNING_MESSAGE_NAME));

        // multiple genders - last gender accepted
        assertParseSuccess(parser, NAME_DESC_GARFIELD + GENDER_DESC_COCO + GENDER_DESC_GARFIELD + DOB_DESC_GARFIELD
                + SPECIES_DESC_GARFIELD + FOOD_DESC_GARFIELD + TAG_DESC_FAT,
                new AddPetCommand(expectedPet, WARNING_MESSAGE_GENDER));

        // multiple dates of birth - last date of birth accepted
        assertParseSuccess(parser, NAME_DESC_GARFIELD + GENDER_DESC_GARFIELD + DOB_DESC_COCO + DOB_DESC_GARFIELD
                + SPECIES_DESC_GARFIELD + FOOD_DESC_GARFIELD + TAG_DESC_FAT,
                new AddPetCommand(expectedPet, WARNING_MESSAGE_DOB));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_GARFIELD + GENDER_DESC_GARFIELD + DOB_DESC_GARFIELD + SPECIES_DESC_GARFIELD
                + SPECIES_DESC_GARFIELD + FOOD_DESC_GARFIELD + TAG_DESC_FAT,
                new AddPetCommand(expectedPet, WARNING_MESSAGE_SPECIES));

        // multiple tags - all accepted
        Pet expectedPetMultipleTags = new PetBuilder(GARFIELD).withTags(VALID_TAG_LAZY, VALID_TAG_FAT)
                .build();
        assertParseSuccess(parser, NAME_DESC_GARFIELD + GENDER_DESC_GARFIELD + DOB_DESC_GARFIELD + SPECIES_DESC_GARFIELD
                + FOOD_DESC_GARFIELD + TAG_DESC_LAZY + TAG_DESC_FAT, new AddPetCommand(expectedPetMultipleTags, ""));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Pet expectedPet = new PetBuilder(COCO).withTags().build();
        assertParseSuccess(parser, NAME_DESC_COCO + GENDER_DESC_COCO + DOB_DESC_COCO + SPECIES_DESC_COCO
                + FOOD_DESC_COCO, new AddPetCommand(expectedPet, ""));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPetCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_GARFIELD + GENDER_DESC_GARFIELD + DOB_DESC_GARFIELD
                + SPECIES_DESC_GARFIELD, expectedMessage);

        // missing gender prefix
        assertParseFailure(parser, NAME_DESC_GARFIELD + VALID_GENDER_GARFIELD + DOB_DESC_GARFIELD
                + SPECIES_DESC_GARFIELD, expectedMessage);

        // missing date of birth prefix
        assertParseFailure(parser, NAME_DESC_GARFIELD + GENDER_DESC_GARFIELD + VALID_DOB_GARFIELD
                + SPECIES_DESC_GARFIELD, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_GARFIELD + GENDER_DESC_GARFIELD + DOB_DESC_GARFIELD
                + VALID_SPECIES_GARFIELD, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, NAME_DESC_GARFIELD + VALID_GENDER_GARFIELD + VALID_DOB_GARFIELD
                + VALID_SPECIES_GARFIELD, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + GENDER_DESC_GARFIELD + DOB_DESC_GARFIELD
                + SPECIES_DESC_GARFIELD + FOOD_DESC_GARFIELD + TAG_DESC_LAZY + TAG_DESC_FAT, Name.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser, NAME_DESC_GARFIELD + INVALID_GENDER_DESC + DOB_DESC_GARFIELD
                + SPECIES_DESC_GARFIELD + FOOD_DESC_GARFIELD + TAG_DESC_LAZY + TAG_DESC_FAT,
                Gender.MESSAGE_CONSTRAINTS);

        // invalid date of birth
        assertParseFailure(parser, NAME_DESC_GARFIELD + GENDER_DESC_GARFIELD + INVALID_DOB_DESC
                + SPECIES_DESC_GARFIELD + FOOD_DESC_GARFIELD + TAG_DESC_LAZY + TAG_DESC_FAT,
                DateOfBirth.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_GARFIELD + GENDER_DESC_GARFIELD + DOB_DESC_GARFIELD + INVALID_SPECIES_DESC
                + FOOD_DESC_GARFIELD + TAG_DESC_LAZY + TAG_DESC_FAT, Species.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_GARFIELD + GENDER_DESC_GARFIELD + DOB_DESC_GARFIELD
                + SPECIES_DESC_GARFIELD + FOOD_DESC_GARFIELD + INVALID_TAG_DESC + TAG_DESC_FAT,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + GENDER_DESC_GARFIELD + DOB_DESC_GARFIELD + INVALID_SPECIES_DESC
                        + FOOD_DESC_GARFIELD, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_GARFIELD + GENDER_DESC_GARFIELD + DOB_DESC_GARFIELD
                        + SPECIES_DESC_GARFIELD + FOOD_DESC_GARFIELD + TAG_DESC_LAZY + TAG_DESC_FAT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPetCommand.MESSAGE_USAGE));
    }
}
