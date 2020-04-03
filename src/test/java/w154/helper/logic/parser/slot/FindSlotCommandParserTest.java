package w154.helper.logic.parser.slot;

import static w154.helper.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import w154.helper.logic.commands.slot.FindSlotCommand;
import w154.helper.commons.core.Messages;

class FindSlotCommandParserTest {

    private FindSlotCommandParser parser = new FindSlotCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FindSlotCommand.MESSAGE_USAGE));
    }
}
