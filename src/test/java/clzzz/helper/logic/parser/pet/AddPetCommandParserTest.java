package clzzz.helper.logic.parser.pet;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.commands.CommandTestUtil;
import clzzz.helper.logic.parser.CommandParserTestUtil;
import clzzz.helper.testutil.pet.PetBuilder;
import clzzz.helper.testutil.pet.TypicalPets;
import clzzz.helper.logic.commands.pet.AddPetCommand;
import clzzz.helper.model.pet.DateOfBirth;
import clzzz.helper.model.pet.Gender;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.pet.Species;
import clzzz.helper.model.tag.Tag;
import clzzz.helper.commons.core.Messages;

public class AddPetCommandParserTest {
    private AddPetCommandParser parser = new AddPetCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Pet expectedPet = new PetBuilder(TypicalPets.GARFIELD).withTags(CommandTestUtil.VALID_TAG_FAT).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + CommandTestUtil.NAME_DESC_GARFIELD + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.DOB_DESC_GARFIELD
                + CommandTestUtil.SPECIES_DESC_GARFIELD + CommandTestUtil.FOOD_DESC_GARFIELD + CommandTestUtil.TAG_DESC_FAT, new AddPetCommand(expectedPet, ""));

        // multiple names - last name accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_COCO + CommandTestUtil.NAME_DESC_GARFIELD + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.DOB_DESC_GARFIELD
                + CommandTestUtil.SPECIES_DESC_GARFIELD + CommandTestUtil.FOOD_DESC_GARFIELD + CommandTestUtil.TAG_DESC_FAT,
                new AddPetCommand(expectedPet, Messages.WARNING_MESSAGE_NAME));

        // multiple genders - last gender accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_GARFIELD + CommandTestUtil.GENDER_DESC_COCO + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.DOB_DESC_GARFIELD
                + CommandTestUtil.SPECIES_DESC_GARFIELD + CommandTestUtil.FOOD_DESC_GARFIELD + CommandTestUtil.TAG_DESC_FAT,
                new AddPetCommand(expectedPet, Messages.WARNING_MESSAGE_GENDER));

        // multiple dates of birth - last date of birth accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_GARFIELD + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.DOB_DESC_COCO + CommandTestUtil.DOB_DESC_GARFIELD
                + CommandTestUtil.SPECIES_DESC_GARFIELD + CommandTestUtil.FOOD_DESC_GARFIELD + CommandTestUtil.TAG_DESC_FAT,
                new AddPetCommand(expectedPet, Messages.WARNING_MESSAGE_DOB));

        // multiple addresses - last address accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_GARFIELD + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.DOB_DESC_GARFIELD + CommandTestUtil.SPECIES_DESC_GARFIELD
                + CommandTestUtil.SPECIES_DESC_GARFIELD + CommandTestUtil.FOOD_DESC_GARFIELD + CommandTestUtil.TAG_DESC_FAT,
                new AddPetCommand(expectedPet, Messages.WARNING_MESSAGE_SPECIES));

        // multiple tags - all accepted
        Pet expectedPetMultipleTags = new PetBuilder(TypicalPets.GARFIELD).withTags(CommandTestUtil.VALID_TAG_LAZY, CommandTestUtil.VALID_TAG_FAT)
                .build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_GARFIELD + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.DOB_DESC_GARFIELD + CommandTestUtil.SPECIES_DESC_GARFIELD
                + CommandTestUtil.FOOD_DESC_GARFIELD + CommandTestUtil.TAG_DESC_LAZY + CommandTestUtil.TAG_DESC_FAT, new AddPetCommand(expectedPetMultipleTags, ""));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Pet expectedPet = new PetBuilder(TypicalPets.COCO).withTags().build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_COCO + CommandTestUtil.GENDER_DESC_COCO + CommandTestUtil.DOB_DESC_COCO + CommandTestUtil.SPECIES_DESC_COCO
                + CommandTestUtil.FOOD_DESC_COCO, new AddPetCommand(expectedPet, ""));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddPetCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_GARFIELD + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.DOB_DESC_GARFIELD
                + CommandTestUtil.SPECIES_DESC_GARFIELD, expectedMessage);

        // missing gender prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_GARFIELD + CommandTestUtil.VALID_GENDER_GARFIELD + CommandTestUtil.DOB_DESC_GARFIELD
                + CommandTestUtil.SPECIES_DESC_GARFIELD, expectedMessage);

        // missing date of birth prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_GARFIELD + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.VALID_DOB_GARFIELD
                + CommandTestUtil.SPECIES_DESC_GARFIELD, expectedMessage);

        // missing address prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_GARFIELD + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.DOB_DESC_GARFIELD
                + CommandTestUtil.VALID_SPECIES_GARFIELD, expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_GARFIELD + CommandTestUtil.VALID_GENDER_GARFIELD + CommandTestUtil.VALID_DOB_GARFIELD
                + CommandTestUtil.VALID_SPECIES_GARFIELD, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.DOB_DESC_GARFIELD
                + CommandTestUtil.SPECIES_DESC_GARFIELD + CommandTestUtil.FOOD_DESC_GARFIELD + CommandTestUtil.TAG_DESC_LAZY + CommandTestUtil.TAG_DESC_FAT, Name.MESSAGE_CONSTRAINTS);

        // invalid gender
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_GARFIELD + CommandTestUtil.INVALID_GENDER_DESC + CommandTestUtil.DOB_DESC_GARFIELD
                + CommandTestUtil.SPECIES_DESC_GARFIELD + CommandTestUtil.FOOD_DESC_GARFIELD + CommandTestUtil.TAG_DESC_LAZY + CommandTestUtil.TAG_DESC_FAT,
                Gender.MESSAGE_CONSTRAINTS);

        // invalid date of birth
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_GARFIELD + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.INVALID_DOB_DESC
                + CommandTestUtil.SPECIES_DESC_GARFIELD + CommandTestUtil.FOOD_DESC_GARFIELD + CommandTestUtil.TAG_DESC_LAZY + CommandTestUtil.TAG_DESC_FAT,
                DateOfBirth.MESSAGE_CONSTRAINTS);

        // invalid address
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_GARFIELD + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.DOB_DESC_GARFIELD + CommandTestUtil.INVALID_SPECIES_DESC
                + CommandTestUtil.FOOD_DESC_GARFIELD + CommandTestUtil.TAG_DESC_LAZY + CommandTestUtil.TAG_DESC_FAT, Species.MESSAGE_CONSTRAINTS);

        // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_GARFIELD + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.DOB_DESC_GARFIELD
                + CommandTestUtil.SPECIES_DESC_GARFIELD + CommandTestUtil.FOOD_DESC_GARFIELD + CommandTestUtil.INVALID_TAG_DESC + CommandTestUtil.TAG_DESC_FAT,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.DOB_DESC_GARFIELD + CommandTestUtil.INVALID_SPECIES_DESC
                        + CommandTestUtil.FOOD_DESC_GARFIELD, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY + CommandTestUtil.NAME_DESC_GARFIELD + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.DOB_DESC_GARFIELD
                        + CommandTestUtil.SPECIES_DESC_GARFIELD + CommandTestUtil.FOOD_DESC_GARFIELD + CommandTestUtil.TAG_DESC_LAZY + CommandTestUtil.TAG_DESC_FAT,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddPetCommand.MESSAGE_USAGE));
    }
}
