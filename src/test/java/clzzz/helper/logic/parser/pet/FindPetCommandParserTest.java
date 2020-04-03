package clzzz.helper.logic.parser.pet;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.parser.CommandParserTestUtil;
import clzzz.helper.logic.commands.pet.FindPetCommand;
import clzzz.helper.model.pet.NameContainsKeywordsPredicate;
import clzzz.helper.commons.core.Messages;

public class FindPetCommandParserTest {

    private FindPetCommandParser parser = new FindPetCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FindPetCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindPetCommand() {
        // no leading and trailing whitespaces
        FindPetCommand expectedFindPetCommand =
                new FindPetCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        CommandParserTestUtil.assertParseSuccess(parser, "Alice Bob", expectedFindPetCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindPetCommand);
    }

}
