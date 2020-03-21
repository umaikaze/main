package seedu.address.logic.parser.slot;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_COCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COCO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.slot.FindSlotCommand;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.model.slot.SlotDatePredicate;
import seedu.address.model.slot.SlotPetNamePredicate;
import seedu.address.model.slot.SlotPredicate;

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

        List<SlotPredicate> predicates = new ArrayList<>();
        predicates.add(new SlotPetNamePredicate(VALID_NAME_COCO));
        predicates.add(new SlotDatePredicate(SlotParserUtil.parseDateTime(VALID_DATETIME_COCO)));
        FindSlotCommand expectedFindCommand = new FindSlotCommand(predicates.stream()
                        .reduce((pred1, pred2) -> (SlotPredicate) pred1.and(pred2))
                        .get());
        //TODO REMOVE COMMENTED OUT CODE
        /*
        FindSlotCommand expectedFindCommand = new FindSlotCommand((SlotPredicate) (new SlotPetNamePredicate(VALID_NAME_COCO))
                .and(new SlotDatePredicate(SlotParserUtil.parseDateTime(VALID_DATETIME_COCO))));
         */
        assertParseSuccess(parser, VALID_NAME_COCO + " " + VALID_DATETIME_COCO, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n" + VALID_NAME_COCO + "\n \t" + VALID_DATETIME_COCO + "\t", expectedFindCommand);
    }
}