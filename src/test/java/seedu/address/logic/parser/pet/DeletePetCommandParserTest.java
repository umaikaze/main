package seedu.address.logic.parser.pet;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PET;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.pet.DeletePetCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeletePetCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeletePetCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeletePetCommandParserTest {

    private DeletePetParser parser = new DeletePetParser();

    @Test
    public void parse_validArgs_returnsDeletePetCommand() {
        assertParseSuccess(parser, "1", new DeletePetCommand(INDEX_FIRST_PET));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePetCommand.MESSAGE_USAGE));
    }
}
