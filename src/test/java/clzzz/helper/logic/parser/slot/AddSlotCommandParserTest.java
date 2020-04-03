package clzzz.helper.logic.parser.slot;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.commands.CommandTestUtil;
import clzzz.helper.logic.parser.CommandParserTestUtil;
import clzzz.helper.testutil.pet.TypicalPets;
import clzzz.helper.testutil.slot.SlotBuilder;
import clzzz.helper.logic.commands.slot.AddSlotCommand;
import clzzz.helper.model.Model;
import clzzz.helper.model.ModelManager;
import clzzz.helper.model.UserPrefs;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.commons.core.Messages;

public class AddSlotCommandParserTest {

    private Model model = new ModelManager(TypicalPets.getTypicalPetTrackerWithSlots(), new UserPrefs());
    private AddSlotCommandParser parser = new AddSlotCommandParser(model);

    @Test
    public void parse_allFieldsPresent_success() {
        Slot expectedSlot = new SlotBuilder().withPet(CommandTestUtil.VALID_NAME_COCO)
                .withDateTime(CommandTestUtil.VALID_DATETIME_COCO).withDuration(CommandTestUtil.VALID_DURATION_COCO).build();

        // Whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + CommandTestUtil.NAME_DESC_COCO + CommandTestUtil.DATETIME_DESC_COCO
                + CommandTestUtil.DURATION_DESC_COCO, new AddSlotCommand(expectedSlot, ""));

        // Multiple pets - last pet accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_GARFIELD + CommandTestUtil.NAME_DESC_COCO + CommandTestUtil.DATETIME_DESC_COCO
                + CommandTestUtil.DURATION_DESC_COCO, new AddSlotCommand(expectedSlot, Messages.WARNING_MESSAGE_NAME));

        // Multiple dateTime - last dateTime accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_COCO + CommandTestUtil.DATETIME_DESC_GARFIELD + CommandTestUtil.DATETIME_DESC_COCO
                + CommandTestUtil.DURATION_DESC_COCO, new AddSlotCommand(expectedSlot, Messages.WARNING_MESSAGE_TIME));

        // Multiple durations - last duration accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_COCO + CommandTestUtil.DATETIME_DESC_COCO + CommandTestUtil.DURATION_DESC_GARFIELD
                + CommandTestUtil.DURATION_DESC_COCO, new AddSlotCommand(expectedSlot, Messages.WARNING_MESSAGE_DURATION));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddSlotCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_COCO + CommandTestUtil.DATETIME_DESC_COCO + CommandTestUtil.DURATION_DESC_COCO,
                expectedMessage);

        // missing phone prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_COCO + CommandTestUtil.VALID_DATETIME_COCO + CommandTestUtil.DURATION_DESC_COCO,
                expectedMessage);

        // missing email prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_COCO + CommandTestUtil.DATETIME_DESC_COCO + CommandTestUtil.VALID_DURATION_COCO,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_COCO + CommandTestUtil.VALID_DATETIME_COCO + CommandTestUtil.VALID_DURATION_COCO,
                expectedMessage);
    }
}
