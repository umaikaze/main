package seedu.address.logic.parser.slot;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_COCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COCO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.slot.FindSlotCommand;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.model.slot.Slot;
import seedu.address.model.slot.SlotDatePredicate;
import seedu.address.model.slot.SlotPetNamePredicate;

class FindSlotParserTest {

    private FindSlotParser parser = new FindSlotParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindSlotCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws ParseException {

        // no leading and trailing whitespaces
        List<Predicate<Slot>> predicates = new ArrayList<>();
        predicates.add(new SlotPetNamePredicate(VALID_NAME_COCO));
        predicates.add(new SlotDatePredicate(SlotParserUtil.parseDateTime(VALID_DATETIME_COCO)));
        FindSlotCommand expectedFindCommand = new FindSlotCommand(predicates.stream()
                        .reduce(Predicate::and)
                        .get());
        assertParseSuccess(parser, VALID_NAME_COCO + " " + VALID_DATETIME_COCO, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n" + VALID_NAME_COCO + "\n \t" + VALID_DATETIME_COCO + "\t", expectedFindCommand);
    }
}