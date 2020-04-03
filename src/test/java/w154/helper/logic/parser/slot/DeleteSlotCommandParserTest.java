package w154.helper.logic.parser.slot;

import static w154.helper.logic.parser.CommandParserTestUtil.assertParseFailure;
import static w154.helper.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static w154.helper.testutil.TypicalIndexes.INDEX_FIRST_PET;

import org.junit.jupiter.api.Test;

import w154.helper.logic.commands.slot.DeleteSlotCommand;
import w154.helper.commons.core.Messages;

class DeleteSlotCommandParserTest {

    private DeleteSlotCommandParser parser = new DeleteSlotCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteSlotCommand(INDEX_FIRST_PET));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteSlotCommand.MESSAGE_USAGE));
    }
}
