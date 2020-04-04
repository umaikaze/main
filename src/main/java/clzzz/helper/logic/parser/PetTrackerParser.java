package clzzz.helper.logic.parser;

import static clzzz.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static clzzz.helper.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import clzzz.helper.logic.commands.BackUpCommand;
import clzzz.helper.logic.commands.Command;
import clzzz.helper.logic.commands.DisplayCommand;
import clzzz.helper.logic.commands.ExitCommand;
import clzzz.helper.logic.commands.HelpCommand;
import clzzz.helper.logic.commands.LoadCommand;
import clzzz.helper.logic.commands.StatsCommand;
import clzzz.helper.logic.commands.pet.AddPetCommand;
import clzzz.helper.logic.commands.pet.DeletePetCommand;
import clzzz.helper.logic.commands.pet.EditPetCommand;
import clzzz.helper.logic.commands.pet.FindPetCommand;
import clzzz.helper.logic.commands.slot.AddSlotCommand;
import clzzz.helper.logic.commands.slot.ConflictCommand;
import clzzz.helper.logic.commands.slot.DeleteSlotCommand;
import clzzz.helper.logic.commands.slot.EditSlotCommand;
import clzzz.helper.logic.commands.slot.FindSlotCommand;
import clzzz.helper.logic.parser.exceptions.ParseException;
import clzzz.helper.logic.parser.pet.AddPetCommandParser;
import clzzz.helper.logic.parser.pet.DeletePetCommandParser;
import clzzz.helper.logic.parser.pet.EditPetCommandParser;
import clzzz.helper.logic.parser.pet.FindPetCommandParser;
import clzzz.helper.logic.parser.slot.AddSlotCommandParser;
import clzzz.helper.logic.parser.slot.DeleteSlotCommandParser;
import clzzz.helper.logic.parser.slot.EditSlotCommandParser;
import clzzz.helper.logic.parser.slot.FindSlotCommandParser;
import clzzz.helper.model.Model;
import clzzz.helper.storage.Storage;

/**
 * Parse user input.
 */
public class PetTrackerParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private final Model model;
    private final Storage storage;


    public PetTrackerParser(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
    }

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
        return parseCommand(commandWord, arguments);
    }

    /**
     * Parses user input into command for execution.
     *
     * @param commandWord the command name
     * @param arguments   the string of arguments to the command
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    private Command parseCommand(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {

        // general
        case DisplayCommand.COMMAND_WORD:
            return new DisplayCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case StatsCommand.COMMAND_WORD:
            return new StatsCommand();

        case BackUpCommand.COMMAND_WORD:
            return new BackUpCommand(storage);

        case LoadCommand.COMMAND_WORD:
            return new LoadCommandParser(storage).parse(arguments);

        // pet tracker
        case AddPetCommand.COMMAND_WORD:
            return new AddPetCommandParser().parse(arguments);

        case EditPetCommand.COMMAND_WORD:
            return new EditPetCommandParser().parse(arguments);

        case DeletePetCommand.COMMAND_WORD:
            return new DeletePetCommandParser().parse(arguments);

        case FindPetCommand.COMMAND_WORD:
            return new FindPetCommandParser().parse(arguments);

        // schedule
        case AddSlotCommand.COMMAND_WORD:
            return new AddSlotCommandParser(model).parse(arguments);

        case EditSlotCommand.COMMAND_WORD:
            return new EditSlotCommandParser(model).parse(arguments);

        case DeleteSlotCommand.COMMAND_WORD:
            return new DeleteSlotCommandParser().parse(arguments);

        case FindSlotCommand.COMMAND_WORD:
            return new FindSlotCommandParser().parse(arguments);

        case ConflictCommand.COMMAND_WORD:
            return new ConflictCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
