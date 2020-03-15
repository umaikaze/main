package seedu.address.logic.parser.pet;

import static seedu.address.commons.core.PshMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.pet.PshCommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.pet.PshCommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.pet.FindPetCommand;
import seedu.address.model.pet.NameContainsKeywordsPredicate;

public class FindPetCommandParserTest {

    private FindPetParser parser = new FindPetParser();

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
