package clzzz.helper.logic.parser.pet;

import static clzzz.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static clzzz.helper.logic.parser.CommandParserTestUtil.assertParseFailure;
import static clzzz.helper.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.commands.pet.FindPetCommand;
import clzzz.helper.model.pet.NameContainsKeywordsPredicate;

public class FindPetCommandParserTest {

    private FindPetCommandParser parser = new FindPetCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindPetCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindPetCommand() {
        // no leading and trailing whitespaces
        FindPetCommand expectedFindPetCommand =
                new FindPetCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindPetCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindPetCommand);
    }

}
