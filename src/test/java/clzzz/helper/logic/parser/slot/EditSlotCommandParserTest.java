package clzzz.helper.logic.parser.slot;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.commands.CommandTestUtil;
import clzzz.helper.logic.parser.CommandParserTestUtil;
import clzzz.helper.testutil.TypicalIndexes;
import clzzz.helper.testutil.pet.TypicalPets;
import clzzz.helper.testutil.slot.EditSlotDescriptorBuilder;
import clzzz.helper.commons.core.index.Index;
import clzzz.helper.logic.commands.slot.EditSlotCommand;
import clzzz.helper.logic.commands.slot.EditSlotCommand.EditSlotDescriptor;
import clzzz.helper.logic.parser.general.exceptions.ParseException;
import clzzz.helper.model.Model;
import clzzz.helper.model.ModelManager;
import clzzz.helper.model.UserPrefs;
import clzzz.helper.commons.core.Messages;

class EditSlotCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditSlotCommand.MESSAGE_USAGE);

    private Model model = new ModelManager(TypicalPets.getTypicalPetTrackerWithSlots(), new UserPrefs());
    private EditSlotCommandParser parser = new EditSlotCommandParser(model);

    @Test
    public void parse_missingParts_failure() {
        // No index
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_COCO, MESSAGE_INVALID_FORMAT);

        // No field
        CommandParserTestUtil.assertParseFailure(parser, "1", EditSlotCommand.MESSAGE_NOT_EDITED);

        // No index or field
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    // Invalid values use static functions in SlotParserUtil, so there is no need to test them here

    // TODO Problem: Somehow they don't think the 2 predicates are the same
    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_SLOT;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.DATETIME_DESC_COCO
                + CommandTestUtil.DURATION_DESC_COCO + CommandTestUtil.NAME_DESC_COCO;

        EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder()
                .withPet(CommandTestUtil.VALID_NAME_COCO)
                .withDateTime(CommandTestUtil.VALID_DATETIME_COCO).withDuration(CommandTestUtil.VALID_DURATION_COCO).build();
        EditSlotCommand expectedCommand = new EditSlotCommand(targetIndex, descriptor, "");

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_SLOT;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.DATETIME_DESC_GARFIELD + CommandTestUtil.DURATION_DESC_COCO;

        EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder().withDateTime(CommandTestUtil.VALID_DATETIME_GARFIELD)
                .withDuration(CommandTestUtil.VALID_DURATION_COCO).build();
        EditSlotCommand expectedCommand = new EditSlotCommand(targetIndex, descriptor, "");

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() throws ParseException {

        // pet
        Index targetIndex = TypicalIndexes.INDEX_THIRD_SLOT;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.NAME_DESC_COCO;
        EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder()
                .withPet(CommandTestUtil.VALID_NAME_COCO).build();
        EditSlotCommand expectedCommand = new EditSlotCommand(targetIndex, descriptor, "");
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // dateTime
        userInput = targetIndex.getOneBased() + CommandTestUtil.DATETIME_DESC_COCO;
        descriptor = new EditSlotDescriptorBuilder().withDateTime(CommandTestUtil.VALID_DATETIME_COCO).build();
        expectedCommand = new EditSlotCommand(targetIndex, descriptor, "");
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // duration
        userInput = targetIndex.getOneBased() + CommandTestUtil.DURATION_DESC_COCO;
        descriptor = new EditSlotDescriptorBuilder().withDuration(CommandTestUtil.VALID_DURATION_COCO).build();
        expectedCommand = new EditSlotCommand(targetIndex, descriptor, "");
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {

        Index targetIndex = TypicalIndexes.INDEX_FIRST_SLOT;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.DATETIME_DESC_COCO + CommandTestUtil.DURATION_DESC_COCO + CommandTestUtil.DURATION_DESC_COCO
                + CommandTestUtil.DATETIME_DESC_COCO + CommandTestUtil.DURATION_DESC_COCO + CommandTestUtil.DURATION_DESC_COCO
                + CommandTestUtil.DATETIME_DESC_GARFIELD + CommandTestUtil.DURATION_DESC_GARFIELD + CommandTestUtil.DURATION_DESC_GARFIELD;

        EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder().withDateTime(CommandTestUtil.VALID_DATETIME_GARFIELD)
                .withDuration(CommandTestUtil.VALID_DURATION_GARFIELD).build();
        EditSlotCommand expectedCommand = new EditSlotCommand(targetIndex, descriptor,
                Messages.WARNING_MESSAGE_TIME + Messages.WARNING_MESSAGE_DURATION);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {

        // no other valid values specified
        Index targetIndex = TypicalIndexes.INDEX_FIRST_SLOT;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.INVALID_DATETIME_DESC + CommandTestUtil.DATETIME_DESC_GARFIELD;
        EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder().withDateTime(CommandTestUtil.VALID_DATETIME_GARFIELD).build();
        EditSlotCommand expectedCommand = new EditSlotCommand(targetIndex, descriptor, Messages.WARNING_MESSAGE_TIME);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + CommandTestUtil.DURATION_DESC_GARFIELD + CommandTestUtil.INVALID_DATETIME_DESC + CommandTestUtil.DURATION_DESC_GARFIELD
                + CommandTestUtil.DATETIME_DESC_GARFIELD;
        descriptor = new EditSlotDescriptorBuilder().withDateTime(CommandTestUtil.VALID_DATETIME_GARFIELD)
                .withDuration(CommandTestUtil.VALID_DURATION_GARFIELD).build();
        expectedCommand = new EditSlotCommand(targetIndex, descriptor, Messages.WARNING_MESSAGE_TIME + Messages.WARNING_MESSAGE_DURATION);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }
}
