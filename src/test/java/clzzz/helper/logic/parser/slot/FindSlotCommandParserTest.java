package clzzz.helper.logic.parser.slot;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.parser.CommandParserTestUtil;
import clzzz.helper.logic.commands.slot.FindSlotCommand;
import clzzz.helper.commons.core.Messages;

class FindSlotCommandParserTest {

    private FindSlotCommandParser parser = new FindSlotCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FindSlotCommand.MESSAGE_USAGE));
    }
}
