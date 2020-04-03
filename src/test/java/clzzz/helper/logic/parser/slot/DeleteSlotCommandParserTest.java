package clzzz.helper.logic.parser.slot;

import static clzzz.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static clzzz.helper.testutil.TypicalIndexes.INDEX_FIRST_PET;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.commands.slot.DeleteSlotCommand;
import clzzz.helper.logic.parser.CommandParserTestUtil;

class DeleteSlotCommandParserTest {

    private DeleteSlotCommandParser parser = new DeleteSlotCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1", new DeleteSlotCommand(INDEX_FIRST_PET));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteSlotCommand.MESSAGE_USAGE));
    }
}
