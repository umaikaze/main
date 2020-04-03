package clzzz.helper.logic.parser.slot;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.parser.CommandParserTestUtil;
import clzzz.helper.testutil.TypicalIndexes;
import clzzz.helper.logic.commands.slot.DeleteSlotCommand;
import clzzz.helper.commons.core.Messages;

class DeleteSlotCommandParserTest {

    private DeleteSlotCommandParser parser = new DeleteSlotCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1", new DeleteSlotCommand(TypicalIndexes.INDEX_FIRST_PET));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteSlotCommand.MESSAGE_USAGE));
    }
}
