package clzzz.helper.logic.parser.slot;

import static clzzz.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static clzzz.helper.commons.core.Messages.WARNING_MESSAGE_DATETIME;
import static clzzz.helper.commons.core.Messages.WARNING_MESSAGE_DURATION;
import static clzzz.helper.commons.core.Messages.WARNING_MESSAGE_NAME;
import static clzzz.helper.logic.commands.CommandTestUtil.DATETIME_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.DATETIME_DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.DURATION_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.DURATION_DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.NAME_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.NAME_DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DATETIME_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DURATION_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_NAME_COCO;
import static clzzz.helper.testutil.pet.TypicalPets.getTypicalPetTrackerWithSlots;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.commands.slot.AddSlotCommand;
import clzzz.helper.logic.parser.CommandParserTestUtil;
import clzzz.helper.model.Model;
import clzzz.helper.model.ModelManager;
import clzzz.helper.model.UserPrefs;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.testutil.slot.SlotBuilder;

public class AddSlotCommandParserTest {

    private Model model = new ModelManager(getTypicalPetTrackerWithSlots(), new UserPrefs());
    private AddSlotCommandParser parser = new AddSlotCommandParser(model);

    @Test
    public void parse_allFieldsPresent_success() {
        Slot expectedSlot = new SlotBuilder().withPet(VALID_NAME_COCO)
                .withDateTime(VALID_DATETIME_COCO).withDuration(VALID_DURATION_COCO).build();

        // Whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_COCO + DATETIME_DESC_COCO
                + DURATION_DESC_COCO, new AddSlotCommand(expectedSlot, ""));

        // Multiple pets - last pet accepted
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_GARFIELD + NAME_DESC_COCO + DATETIME_DESC_COCO
                + DURATION_DESC_COCO, new AddSlotCommand(expectedSlot, WARNING_MESSAGE_NAME));

        // Multiple dateTime - last dateTime accepted
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_COCO + DATETIME_DESC_GARFIELD + DATETIME_DESC_COCO
                + DURATION_DESC_COCO, new AddSlotCommand(expectedSlot, WARNING_MESSAGE_DATETIME));

        // Multiple durations - last duration accepted
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_COCO + DATETIME_DESC_COCO + DURATION_DESC_GARFIELD
                + DURATION_DESC_COCO, new AddSlotCommand(expectedSlot, WARNING_MESSAGE_DURATION));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSlotCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_COCO + DATETIME_DESC_COCO + DURATION_DESC_COCO,
                expectedMessage);

        // missing phone prefix
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_COCO + VALID_DATETIME_COCO + DURATION_DESC_COCO,
                expectedMessage);

        // missing email prefix
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_COCO + DATETIME_DESC_COCO + VALID_DURATION_COCO,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_COCO + VALID_DATETIME_COCO + VALID_DURATION_COCO,
                expectedMessage);
    }
}
