package seedu.address.logic.parser.slot;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.general.Command;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.commands.slot.AddSlotCommand;
import seedu.address.logic.commands.slot.DeleteSlotCommand;
import seedu.address.logic.commands.slot.EditSlotCommand;
import seedu.address.logic.commands.slot.FindSlotCommand;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Parses user input.
 */
public class ScheduleParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Used to add pets reference from the ModelManager to the slots
     */
    private final Model model;

    /**
     * @param model Used to add pets reference from the ModelManager to the slots
     */
    public ScheduleParser(Model model) {
        this.model = model;
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
        switch (commandWord) {

        case AddSlotCommand.COMMAND_WORD:
            return new AddSlotParser(model).parse(arguments);

        case EditSlotCommand.COMMAND_WORD:
            return new EditSlotParser().parse(arguments);

        case DeleteSlotCommand.COMMAND_WORD:
            return new DeleteSlotParser().parse(arguments);

        case FindSlotCommand.COMMAND_WORD:
            return new FindSlotParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
