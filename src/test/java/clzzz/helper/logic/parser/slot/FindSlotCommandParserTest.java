package clzzz.helper.logic.parser.slot;

import static clzzz.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.commands.slot.FindSlotCommand;
import clzzz.helper.logic.parser.CommandParserTestUtil;

class FindSlotCommandParserTest {

    private FindSlotCommandParser parser = new FindSlotCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindSlotCommand.MESSAGE_USAGE));
    }
}
