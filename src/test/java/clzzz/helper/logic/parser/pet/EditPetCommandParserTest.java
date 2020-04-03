package clzzz.helper.logic.parser.pet;

import static clzzz.helper.logic.parser.general.CliSyntax.PREFIX_TAG;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.commands.CommandTestUtil;
import clzzz.helper.logic.parser.CommandParserTestUtil;
import clzzz.helper.testutil.TypicalIndexes;
import clzzz.helper.testutil.pet.EditPetDescriptorBuilder;
import clzzz.helper.commons.core.index.Index;
import clzzz.helper.logic.commands.pet.EditPetCommand;
import clzzz.helper.logic.commands.pet.EditPetCommand.EditPetDescriptor;
import clzzz.helper.model.pet.DateOfBirth;
import clzzz.helper.model.pet.Gender;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Species;
import clzzz.helper.model.tag.Tag;
import clzzz.helper.commons.core.Messages;

public class EditPetCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditPetCommand.MESSAGE_USAGE);

    private EditPetCommandParser parser = new EditPetCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_COCO, MESSAGE_INVALID_FORMAT);

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, "1", EditPetCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        CommandParserTestUtil.assertParseFailure(parser, "-5" + CommandTestUtil.NAME_DESC_COCO, MESSAGE_INVALID_FORMAT);

        // zero index
        CommandParserTestUtil.assertParseFailure(parser, "0" + CommandTestUtil.NAME_DESC_COCO, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_GENDER_DESC, Gender.MESSAGE_CONSTRAINTS); // invalid gender
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_DOB_DESC, DateOfBirth.MESSAGE_CONSTRAINTS); // invalid email
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_SPECIES_DESC, Species.MESSAGE_CONSTRAINTS); // invalid address
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid gender followed by valid email
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_GENDER_DESC + CommandTestUtil.DOB_DESC_COCO,
                Gender.MESSAGE_CONSTRAINTS);

        // valid gender followed by invalid gender. The test case for invalid gender followed by valid gender
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.INVALID_GENDER_DESC, Gender.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Pet} being edited,
        // parsing it together with a valid tag results in error
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.TAG_DESC_FAT + CommandTestUtil.TAG_DESC_LAZY + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.TAG_DESC_FAT + TAG_EMPTY + CommandTestUtil.TAG_DESC_LAZY, Tag.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + TAG_EMPTY + CommandTestUtil.TAG_DESC_FAT + CommandTestUtil.TAG_DESC_LAZY, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_NAME_DESC + CommandTestUtil.INVALID_DOB_DESC + CommandTestUtil.VALID_SPECIES_COCO
                        + CommandTestUtil.VALID_GENDER_COCO.toString(),
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_PET;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.TAG_DESC_LAZY
                + CommandTestUtil.DOB_DESC_COCO + CommandTestUtil.SPECIES_DESC_COCO
                + CommandTestUtil.NAME_DESC_COCO + CommandTestUtil.TAG_DESC_FAT;

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_COCO)
                .withGender(CommandTestUtil.VALID_GENDER_GARFIELD).withDateOfBirth(CommandTestUtil.VALID_DOB_COCO)
                .withSpecies(CommandTestUtil.VALID_SPECIES_COCO)
                .withTags(CommandTestUtil.VALID_TAG_LAZY, CommandTestUtil.VALID_TAG_FAT).build();
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor, "");

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_PET;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.DOB_DESC_COCO;

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withGender(CommandTestUtil.VALID_GENDER_GARFIELD)
                .withDateOfBirth(CommandTestUtil.VALID_DOB_COCO).build();
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor, "");

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = TypicalIndexes.INDEX_THIRD_PET;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.NAME_DESC_COCO;
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_COCO).build();
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor, "");
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // gender
        userInput = targetIndex.getOneBased() + CommandTestUtil.GENDER_DESC_COCO;
        descriptor = new EditPetDescriptorBuilder().withGender(CommandTestUtil.VALID_GENDER_COCO.toString()).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor, "");
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + CommandTestUtil.DOB_DESC_COCO;
        descriptor = new EditPetDescriptorBuilder().withDateOfBirth(CommandTestUtil.VALID_DOB_COCO).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor, "");
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + CommandTestUtil.SPECIES_DESC_COCO;
        descriptor = new EditPetDescriptorBuilder().withSpecies(CommandTestUtil.VALID_SPECIES_COCO).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor, "");
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + CommandTestUtil.TAG_DESC_FAT;
        descriptor = new EditPetDescriptorBuilder().withTags(CommandTestUtil.VALID_TAG_FAT).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor, "");
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_PET;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.GENDER_DESC_COCO
                + CommandTestUtil.SPECIES_DESC_COCO + CommandTestUtil.DOB_DESC_COCO + CommandTestUtil.TAG_DESC_FAT
                + CommandTestUtil.GENDER_DESC_COCO + CommandTestUtil.SPECIES_DESC_COCO
                + CommandTestUtil.DOB_DESC_COCO + CommandTestUtil.TAG_DESC_FAT + CommandTestUtil.GENDER_DESC_GARFIELD + CommandTestUtil.SPECIES_DESC_GARFIELD
                + CommandTestUtil.DOB_DESC_GARFIELD + CommandTestUtil.TAG_DESC_LAZY;

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withGender(CommandTestUtil.VALID_GENDER_GARFIELD)
                .withDateOfBirth(CommandTestUtil.VALID_DOB_GARFIELD).withSpecies(CommandTestUtil.VALID_SPECIES_GARFIELD)
                .withTags(CommandTestUtil.VALID_TAG_FAT, CommandTestUtil.VALID_TAG_LAZY).build();
        String warningMessage = Messages.WARNING_MESSAGE_GENDER + Messages.WARNING_MESSAGE_SPECIES + Messages.WARNING_MESSAGE_DOB;
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor, warningMessage);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = TypicalIndexes.INDEX_FIRST_PET;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.INVALID_GENDER_DESC + CommandTestUtil.GENDER_DESC_GARFIELD;
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withGender(CommandTestUtil.VALID_GENDER_GARFIELD)
                .build();
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor, Messages.WARNING_MESSAGE_GENDER);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + CommandTestUtil.DOB_DESC_GARFIELD + CommandTestUtil.INVALID_GENDER_DESC + CommandTestUtil.SPECIES_DESC_GARFIELD
                + CommandTestUtil.GENDER_DESC_GARFIELD;
        descriptor = new EditPetDescriptorBuilder().withGender(CommandTestUtil.VALID_GENDER_GARFIELD)
                .withDateOfBirth(CommandTestUtil.VALID_DOB_GARFIELD)
                .withSpecies(CommandTestUtil.VALID_SPECIES_GARFIELD).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor, Messages.WARNING_MESSAGE_GENDER);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_PET;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withTags().build();
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor, "");

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }
}
