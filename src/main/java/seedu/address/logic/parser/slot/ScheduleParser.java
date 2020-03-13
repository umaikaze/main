package seedu.address.logic.parser.slot;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.general.PshCommand;
import seedu.address.logic.commands.slot.AddSlotCommand;
import seedu.address.logic.commands.slot.DeleteSlotCommand;
import seedu.address.logic.commands.slot.EditSlotCommand;
import seedu.address.logic.commands.slot.FindSlotCommand;
import seedu.address.logic.commands.slot.ListSlotCommand;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.model.PshModel;

/**
 * Parses user input.
 */
public class ScheduleParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Used to add pets reference from the PshModelManager to the slots
     */
    private final PshModel model;

    /**
     * @param model Used to add pets reference from the PshModelManager to the slots
     */
    public ScheduleParser(PshModel model) {
        this.model = model;
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public PshCommand parseCommand(String userInput) throws ParseException {
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
            return new EditSlotParser(model).parse(arguments);

        case DeleteSlotCommand.COMMAND_WORD:
            return new DeleteSlotParser().parse(arguments);

        case FindSlotCommand.COMMAND_WORD:
            return new FindSlotParser().parse(arguments);

        case ListSlotCommand.COMMAND_WORD:
            return new ListSlotCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
