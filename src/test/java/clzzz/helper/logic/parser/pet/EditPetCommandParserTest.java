package clzzz.helper.logic.parser.pet;

import static clzzz.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static clzzz.helper.commons.core.Messages.WARNING_MESSAGE_DOB;
import static clzzz.helper.commons.core.Messages.WARNING_MESSAGE_GENDER;
import static clzzz.helper.commons.core.Messages.WARNING_MESSAGE_SPECIES;
import static clzzz.helper.logic.commands.CommandTestUtil.DOB_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.DOB_DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.GENDER_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.GENDER_DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.INVALID_DOB_DESC;
import static clzzz.helper.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static clzzz.helper.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static clzzz.helper.logic.commands.CommandTestUtil.INVALID_SPECIES_DESC;
import static clzzz.helper.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static clzzz.helper.logic.commands.CommandTestUtil.NAME_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.SPECIES_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.SPECIES_DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.TAG_DESC_FAT;
import static clzzz.helper.logic.commands.CommandTestUtil.TAG_DESC_LAZY;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DOB_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DOB_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_GENDER_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_GENDER_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_NAME_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_SPECIES_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_SPECIES_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_TAG_FAT;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_TAG_LAZY;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_TAG;
import static clzzz.helper.logic.parser.CommandParserTestUtil.assertParseFailure;
import static clzzz.helper.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static clzzz.helper.testutil.TypicalIndexes.INDEX_FIRST_PET;
import static clzzz.helper.testutil.TypicalIndexes.INDEX_SECOND_PET;
import static clzzz.helper.testutil.TypicalIndexes.INDEX_THIRD_PET;

import org.junit.jupiter.api.Test;

import clzzz.helper.commons.core.index.Index;
import clzzz.helper.logic.commands.pet.EditPetCommand;
import clzzz.helper.logic.commands.pet.EditPetCommand.EditPetDescriptor;
import clzzz.helper.model.pet.DateOfBirth;
import clzzz.helper.model.pet.Gender;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Species;
import clzzz.helper.model.pet.Tag;
import clzzz.helper.testutil.pet.EditPetDescriptorBuilder;

public class EditPetCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPetCommand.MESSAGE_USAGE);

    private EditPetCommandParser parser = new EditPetCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_COCO, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditPetCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_COCO, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_COCO, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_GENDER_DESC, Gender.MESSAGE_CONSTRAINTS); // invalid gender
        assertParseFailure(parser, "1" + INVALID_DOB_DESC, DateOfBirth.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_SPECIES_DESC, Species.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid gender followed by valid email
        assertParseFailure(parser, "1" + INVALID_GENDER_DESC + DOB_DESC_COCO,
                Gender.MESSAGE_CONSTRAINTS);

        // valid gender followed by invalid gender. The test case for invalid gender followed by valid gender
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + GENDER_DESC_GARFIELD + INVALID_GENDER_DESC, Gender.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Pet} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FAT + TAG_DESC_LAZY + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FAT + TAG_EMPTY + TAG_DESC_LAZY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FAT + TAG_DESC_LAZY, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_DOB_DESC + VALID_SPECIES_COCO
                        + VALID_GENDER_COCO.toString(),
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PET;
        String userInput = targetIndex.getOneBased() + GENDER_DESC_GARFIELD + TAG_DESC_LAZY
                + DOB_DESC_COCO + SPECIES_DESC_COCO
                + NAME_DESC_COCO + TAG_DESC_FAT;

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(VALID_NAME_COCO)
                .withGender(VALID_GENDER_GARFIELD).withDateOfBirth(VALID_DOB_COCO)
                .withSpecies(VALID_SPECIES_COCO)
                .withTags(VALID_TAG_LAZY, VALID_TAG_FAT).build();
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor, "");

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PET;
        String userInput = targetIndex.getOneBased() + GENDER_DESC_GARFIELD + DOB_DESC_COCO;

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withGender(VALID_GENDER_GARFIELD)
                .withDateOfBirth(VALID_DOB_COCO).build();
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor, "");

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PET;
        String userInput = targetIndex.getOneBased() + NAME_DESC_COCO;
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(VALID_NAME_COCO).build();
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor, "");
        assertParseSuccess(parser, userInput, expectedCommand);

        // gender
        userInput = targetIndex.getOneBased() + GENDER_DESC_COCO;
        descriptor = new EditPetDescriptorBuilder().withGender(VALID_GENDER_COCO.toString()).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor, "");
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + DOB_DESC_COCO;
        descriptor = new EditPetDescriptorBuilder().withDateOfBirth(VALID_DOB_COCO).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor, "");
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + SPECIES_DESC_COCO;
        descriptor = new EditPetDescriptorBuilder().withSpecies(VALID_SPECIES_COCO).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor, "");
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FAT;
        descriptor = new EditPetDescriptorBuilder().withTags(VALID_TAG_FAT).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor, "");
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PET;
        String userInput = targetIndex.getOneBased() + GENDER_DESC_COCO
                + SPECIES_DESC_COCO + DOB_DESC_COCO + TAG_DESC_FAT
                + GENDER_DESC_COCO + SPECIES_DESC_COCO
                + DOB_DESC_COCO + TAG_DESC_FAT + GENDER_DESC_GARFIELD + SPECIES_DESC_GARFIELD
                + DOB_DESC_GARFIELD + TAG_DESC_LAZY;

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withGender(VALID_GENDER_GARFIELD)
                .withDateOfBirth(VALID_DOB_GARFIELD).withSpecies(VALID_SPECIES_GARFIELD)
                .withTags(VALID_TAG_FAT, VALID_TAG_LAZY).build();
        String warningMessage = WARNING_MESSAGE_GENDER + WARNING_MESSAGE_SPECIES + WARNING_MESSAGE_DOB;
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor, warningMessage);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PET;
        String userInput = targetIndex.getOneBased() + INVALID_GENDER_DESC + GENDER_DESC_GARFIELD;
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withGender(VALID_GENDER_GARFIELD)
                .build();
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor, WARNING_MESSAGE_GENDER);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DOB_DESC_GARFIELD + INVALID_GENDER_DESC + SPECIES_DESC_GARFIELD
                + GENDER_DESC_GARFIELD;
        descriptor = new EditPetDescriptorBuilder().withGender(VALID_GENDER_GARFIELD)
                .withDateOfBirth(VALID_DOB_GARFIELD)
                .withSpecies(VALID_SPECIES_GARFIELD).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor, WARNING_MESSAGE_GENDER);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PET;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withTags().build();
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor, "");

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
