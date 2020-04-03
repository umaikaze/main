package w154.helper.logic.parser.slot;

import static java.util.Objects.requireNonNull;
import static w154.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static w154.helper.logic.parser.general.CliSyntax.PREFIX_DATETIME;
import static w154.helper.logic.parser.general.CliSyntax.PREFIX_DURATION;
import static w154.helper.logic.parser.general.CliSyntax.PREFIX_INDEX;
import static w154.helper.logic.parser.general.CliSyntax.PREFIX_NAME;

import w154.helper.commons.core.Messages;
import w154.helper.commons.core.index.Index;
import w154.helper.logic.commands.slot.EditSlotCommand;
import w154.helper.logic.commands.slot.EditSlotCommand.EditSlotDescriptor;
import w154.helper.logic.parser.general.ArgumentMultimap;
import w154.helper.logic.parser.general.ArgumentTokenizer;
import w154.helper.logic.parser.general.Parser;
import w154.helper.logic.parser.general.exceptions.ParseException;
import w154.helper.model.Model;

/**
 * Parses input arguments and creates a new EditSlotCommand object
 */
public class EditSlotCommandParser implements Parser<EditSlotCommand> {

    private Model model;

    public EditSlotCommandParser(Model model) {
        this.model = model;
    }


    /**
     * Parses the given {@code String} of arguments in the context of the EditSlotCommand
     * and returns an EditSlotCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditSlotCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_DATETIME, PREFIX_DURATION, PREFIX_NAME);

        Index index;

        try {
            index = SlotParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSlotCommand.MESSAGE_USAGE), pe);
        }

        EditSlotDescriptor editSlotDescriptor = new EditSlotDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editSlotDescriptor.setPet(SlotParserUtil.parsePet(argMultimap.getValue(PREFIX_NAME).get(), model));
        }

        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            editSlotDescriptor.setDateTime(SlotParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get()));
        }
        if (argMultimap.getValue(PREFIX_DURATION).isPresent()) {
            editSlotDescriptor.setDuration(SlotParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get()));
        }

        if (!editSlotDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditSlotCommand.MESSAGE_NOT_EDITED);
        }

        String warningMessage = "";
        if (argMultimap.getAllValues(PREFIX_NAME).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_NAME;
        }
        if (argMultimap.getAllValues(PREFIX_DATETIME).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_TIME;
        }
        if (argMultimap.getAllValues(PREFIX_DURATION).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_DURATION;
        }

        return new EditSlotCommand(index, editSlotDescriptor, warningMessage);
    }
}
