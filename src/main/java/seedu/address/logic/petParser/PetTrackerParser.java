package seedu.address.logic.petParser;

import seedu.address.logic.generalCommands.*;
import seedu.address.logic.petCommands.*;
import seedu.address.logic.generalParser.exceptions.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.petCore.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.petCore.Messages.MESSAGE_UNKNOWN_COMMAND;

public class PetTrackerParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddPetCommand.COMMAND_WORD:
            return new AddPetParser().parse(arguments);

        case EditPetCommand.COMMAND_WORD:
            return new EditPetParser().parse(arguments);

        case DeletePetCommand.COMMAND_WORD:
            return new DeletePetParser().parse(arguments);

        case FindPetCommand.COMMAND_WORD:
            return new FindPetParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
