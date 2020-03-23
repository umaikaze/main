package seedu.address.logic.parser.slot;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_COCO;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_COCO;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_COCO;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_COCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_COCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COCO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.pet.TypicalPets.getTypicalModelManager;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.slot.AddSlotCommand;
import seedu.address.model.Model;
import seedu.address.model.pet.Name;
import seedu.address.model.slot.Slot;
import seedu.address.testutil.slot.SlotBuilder;

public class AddSlotParserTest {

    private static final Model model = getTypicalModelManager();
    private static final AddSlotParser parser = new AddSlotParser(model);

    @Test
    public void parse_allFieldsPresent_success() {
        Slot expectedSlot = new SlotBuilder(model).withPet(model.getPet(new Name(VALID_NAME_COCO)))
                .withDateTime(VALID_DATETIME_COCO).withDuration(VALID_DURATION_COCO).build();

        // Whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_COCO + DATETIME_DESC_COCO
                + DURATION_DESC_COCO, new AddSlotCommand(expectedSlot));

        // Multiple pets - last pet accepted
        assertParseSuccess(parser, NAME_DESC_GARFIELD + NAME_DESC_COCO + DATETIME_DESC_COCO
                + DURATION_DESC_COCO, new AddSlotCommand(expectedSlot));

        // Multiple dateTime - last dateTime accepted
        assertParseSuccess(parser, NAME_DESC_COCO + DATETIME_DESC_GARFIELD + DATETIME_DESC_COCO
                + DURATION_DESC_COCO, new AddSlotCommand(expectedSlot));

        // Multiple durations - last duration accepted
        assertParseSuccess(parser, NAME_DESC_COCO + DATETIME_DESC_COCO + DURATION_DESC_GARFIELD
                + DURATION_DESC_COCO, new AddSlotCommand(expectedSlot));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSlotCommand.MESSAGE_USAGE);

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
