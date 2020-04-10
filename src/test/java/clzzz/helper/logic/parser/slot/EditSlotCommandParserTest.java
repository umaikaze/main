package clzzz.helper.logic.parser.slot;

import static clzzz.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static clzzz.helper.commons.core.Messages.WARNING_MESSAGE_DATETIME;
import static clzzz.helper.commons.core.Messages.WARNING_MESSAGE_DURATION;
import static clzzz.helper.logic.commands.CommandTestUtil.DATETIME_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.DATETIME_DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.DURATION_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.DURATION_DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static clzzz.helper.logic.commands.CommandTestUtil.NAME_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DATETIME_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DATETIME_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DURATION_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DURATION_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_NAME_COCO;
import static clzzz.helper.logic.parser.CommandParserTestUtil.assertParseFailure;
import static clzzz.helper.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static clzzz.helper.testutil.TypicalIndexes.INDEX_FIRST_SLOT;
import static clzzz.helper.testutil.TypicalIndexes.INDEX_SECOND_SLOT;
import static clzzz.helper.testutil.TypicalIndexes.INDEX_THIRD_SLOT;
import static clzzz.helper.testutil.pet.TypicalPets.getTypicalPetTrackerWithSlots;

import org.junit.jupiter.api.Test;

import clzzz.helper.commons.core.index.Index;
import clzzz.helper.logic.commands.slot.EditSlotCommand;
import clzzz.helper.logic.commands.slot.EditSlotCommand.EditSlotDescriptor;
import clzzz.helper.logic.parser.exceptions.ParseException;
import clzzz.helper.model.Model;
import clzzz.helper.model.ModelManager;
import clzzz.helper.model.UserPrefs;
import clzzz.helper.testutil.slot.EditSlotDescriptorBuilder;

class EditSlotCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSlotCommand.MESSAGE_USAGE);

    private Model model = new ModelManager(getTypicalPetTrackerWithSlots(), new UserPrefs());
    private EditSlotCommandParser parser = new EditSlotCommandParser(model);

    @Test
    public void parse_missingParts_failure() {
        // No index
        assertParseFailure(parser, VALID_NAME_COCO, MESSAGE_INVALID_FORMAT);

        // No field
        assertParseFailure(parser, "1", EditSlotCommand.MESSAGE_NOT_EDITED);

        // No index or field
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    // Invalid values use static functions in SlotParserUtil, so there is no need to test them here

    // TODO Problem: Somehow they don't think the 2 predicates are the same
    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_SLOT;
        String userInput = targetIndex.getOneBased() + DATETIME_DESC_COCO
                + DURATION_DESC_COCO + NAME_DESC_COCO;

        EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder()
                .withPet(VALID_NAME_COCO)
                .withDateTime(VALID_DATETIME_COCO).withDuration(VALID_DURATION_COCO).build();
        EditSlotCommand expectedCommand = new EditSlotCommand(targetIndex, descriptor, "");

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_SLOT;
        String userInput = targetIndex.getOneBased() + DATETIME_DESC_GARFIELD + DURATION_DESC_COCO;

        EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder().withDateTime(VALID_DATETIME_GARFIELD)
                .withDuration(VALID_DURATION_COCO).build();
        EditSlotCommand expectedCommand = new EditSlotCommand(targetIndex, descriptor, "");

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() throws ParseException {

        // pet
        Index targetIndex = INDEX_THIRD_SLOT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_COCO;
        EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder()
                .withPet(VALID_NAME_COCO).build();
        EditSlotCommand expectedCommand = new EditSlotCommand(targetIndex, descriptor, "");
        assertParseSuccess(parser, userInput, expectedCommand);

        // dateTime
        userInput = targetIndex.getOneBased() + DATETIME_DESC_COCO;
        descriptor = new EditSlotDescriptorBuilder().withDateTime(VALID_DATETIME_COCO).build();
        expectedCommand = new EditSlotCommand(targetIndex, descriptor, "");
        assertParseSuccess(parser, userInput, expectedCommand);

        // duration
        userInput = targetIndex.getOneBased() + DURATION_DESC_COCO;
        descriptor = new EditSlotDescriptorBuilder().withDuration(VALID_DURATION_COCO).build();
        expectedCommand = new EditSlotCommand(targetIndex, descriptor, "");
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {

        Index targetIndex = INDEX_FIRST_SLOT;
        String userInput = targetIndex.getOneBased() + DATETIME_DESC_COCO + DURATION_DESC_COCO + DURATION_DESC_COCO
                + DATETIME_DESC_COCO + DURATION_DESC_COCO + DURATION_DESC_COCO
                + DATETIME_DESC_GARFIELD + DURATION_DESC_GARFIELD + DURATION_DESC_GARFIELD;

        EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder().withDateTime(VALID_DATETIME_GARFIELD)
                .withDuration(VALID_DURATION_GARFIELD).build();
        EditSlotCommand expectedCommand = new EditSlotCommand(targetIndex, descriptor,
                WARNING_MESSAGE_DATETIME + WARNING_MESSAGE_DURATION);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {

        // no other valid values specified
        Index targetIndex = INDEX_FIRST_SLOT;
        String userInput = targetIndex.getOneBased() + INVALID_DATETIME_DESC + DATETIME_DESC_GARFIELD;
        EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder().withDateTime(VALID_DATETIME_GARFIELD).build();
        EditSlotCommand expectedCommand = new EditSlotCommand(targetIndex, descriptor, WARNING_MESSAGE_DATETIME);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DURATION_DESC_GARFIELD + INVALID_DATETIME_DESC + DURATION_DESC_GARFIELD
                + DATETIME_DESC_GARFIELD;
        descriptor = new EditSlotDescriptorBuilder().withDateTime(VALID_DATETIME_GARFIELD)
                .withDuration(VALID_DURATION_GARFIELD).build();
        expectedCommand = new EditSlotCommand(targetIndex, descriptor,
                WARNING_MESSAGE_DATETIME + WARNING_MESSAGE_DURATION);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
