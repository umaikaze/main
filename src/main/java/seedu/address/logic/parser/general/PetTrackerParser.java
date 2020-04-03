package seedu.address.logic.parser.general;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.general.BackUpCommand;
import seedu.address.logic.commands.general.Command;
import seedu.address.logic.commands.general.DisplayCommand;
import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.commands.general.LoadCommand;
import seedu.address.logic.commands.general.StatsCommand;
import seedu.address.logic.commands.pet.AddPetCommand;
import seedu.address.logic.commands.pet.DeletePetCommand;
import seedu.address.logic.commands.pet.EditPetCommand;
import seedu.address.logic.commands.pet.FindPetCommand;
import seedu.address.logic.commands.slot.AddSlotCommand;
import seedu.address.logic.commands.slot.ConflictCommand;
import seedu.address.logic.commands.slot.DeleteSlotCommand;
import seedu.address.logic.commands.slot.EditSlotCommand;
import seedu.address.logic.commands.slot.FindSlotCommand;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.logic.parser.pet.AddPetCommandParser;
import seedu.address.logic.parser.pet.DeletePetCommandParser;
import seedu.address.logic.parser.pet.EditPetCommandParser;
import seedu.address.logic.parser.pet.FindPetCommandParser;
import seedu.address.logic.parser.slot.AddSlotCommandParser;
import seedu.address.logic.parser.slot.DeleteSlotCommandParser;
import seedu.address.logic.parser.slot.EditSlotCommandParser;
import seedu.address.logic.parser.slot.FindSlotCommandParser;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

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
