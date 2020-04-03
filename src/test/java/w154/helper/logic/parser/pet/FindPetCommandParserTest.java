package w154.helper.logic.parser.pet;

import static w154.helper.logic.parser.CommandParserTestUtil.assertParseFailure;
import static w154.helper.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import w154.helper.logic.commands.pet.FindPetCommand;
import w154.helper.model.pet.NameContainsKeywordsPredicate;
import w154.helper.commons.core.Messages;

public class FindPetCommandParserTest {

    private FindPetCommandParser parser = new FindPetCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
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
