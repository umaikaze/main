package w154.helper.logic.parser.slot;

import static w154.helper.logic.commands.CommandTestUtil.DATETIME_DESC_COCO;
import static w154.helper.logic.commands.CommandTestUtil.DATETIME_DESC_GARFIELD;
import static w154.helper.logic.commands.CommandTestUtil.DURATION_DESC_COCO;
import static w154.helper.logic.commands.CommandTestUtil.DURATION_DESC_GARFIELD;
import static w154.helper.logic.commands.CommandTestUtil.NAME_DESC_COCO;
import static w154.helper.logic.commands.CommandTestUtil.NAME_DESC_GARFIELD;
import static w154.helper.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static w154.helper.logic.commands.CommandTestUtil.VALID_DATETIME_COCO;
import static w154.helper.logic.commands.CommandTestUtil.VALID_DURATION_COCO;
import static w154.helper.logic.commands.CommandTestUtil.VALID_NAME_COCO;
import static w154.helper.logic.parser.CommandParserTestUtil.assertParseFailure;
import static w154.helper.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static w154.helper.testutil.pet.TypicalPets.getTypicalPetTrackerWithSlots;

import org.junit.jupiter.api.Test;

import w154.helper.logic.commands.slot.AddSlotCommand;
import w154.helper.model.Model;
import w154.helper.model.ModelManager;
import w154.helper.model.UserPrefs;
import w154.helper.model.slot.Slot;
import w154.helper.testutil.slot.SlotBuilder;
import w154.helper.commons.core.Messages;

public class AddSlotCommandParserTest {

    private Model model = new ModelManager(getTypicalPetTrackerWithSlots(), new UserPrefs());
    private AddSlotCommandParser parser = new AddSlotCommandParser(model);

    @Test
    public void parse_allFieldsPresent_success() {
        Slot expectedSlot = new SlotBuilder().withPet(VALID_NAME_COCO)
                .withDateTime(VALID_DATETIME_COCO).withDuration(VALID_DURATION_COCO).build();

        // Whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_COCO + DATETIME_DESC_COCO
                + DURATION_DESC_COCO, new AddSlotCommand(expectedSlot, ""));

        // Multiple pets - last pet accepted
        assertParseSuccess(parser, NAME_DESC_GARFIELD + NAME_DESC_COCO + DATETIME_DESC_COCO
                + DURATION_DESC_COCO, new AddSlotCommand(expectedSlot, Messages.WARNING_MESSAGE_NAME));

        // Multiple dateTime - last dateTime accepted
        assertParseSuccess(parser, NAME_DESC_COCO + DATETIME_DESC_GARFIELD + DATETIME_DESC_COCO
                + DURATION_DESC_COCO, new AddSlotCommand(expectedSlot, Messages.WARNING_MESSAGE_TIME));

        // Multiple durations - last duration accepted
        assertParseSuccess(parser, NAME_DESC_COCO + DATETIME_DESC_COCO + DURATION_DESC_GARFIELD
                + DURATION_DESC_COCO, new AddSlotCommand(expectedSlot, Messages.WARNING_MESSAGE_DURATION));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddSlotCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_COCO + DATETIME_DESC_COCO + DURATION_DESC_COCO,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_COCO + VALID_DATETIME_COCO + DURATION_DESC_COCO,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_COCO + DATETIME_DESC_COCO + VALID_DURATION_COCO,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_COCO + VALID_DATETIME_COCO + VALID_DURATION_COCO,
                expectedMessage);
    }
}
