package clzzz.helper.logic.parser.slot;

import static clzzz.helper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_DATETIME;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_DURATION;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_INDEX;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Optional;

import clzzz.helper.commons.core.Messages;
import clzzz.helper.commons.core.index.Index;
import clzzz.helper.logic.commands.slot.EditSlotCommand;
import clzzz.helper.logic.commands.slot.EditSlotCommand.EditSlotDescriptor;
import clzzz.helper.logic.parser.ArgumentMultimap;
import clzzz.helper.logic.parser.ArgumentTokenizer;
import clzzz.helper.logic.parser.Parser;
import clzzz.helper.logic.parser.exceptions.ParseException;
import clzzz.helper.model.Model;
import clzzz.helper.model.slot.DateTime;

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

        String warningMessage = "";
        if (argMultimap.getAllValues(PREFIX_NAME).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_NAME;
        }
        if (argMultimap.getAllValues(PREFIX_DATETIME).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_DATETIME;
        }
        if (argMultimap.getAllValues(PREFIX_DURATION).size() > 1) {
            warningMessage += Messages.WARNING_MESSAGE_DURATION;
        }

        Optional<String> newPetNameString = argMultimap.getValue(PREFIX_NAME);
        if (newPetNameString.isPresent()) {
            editSlotDescriptor.setPet(SlotParserUtil.parsePet(newPetNameString.get(), model));
        }

        Optional<String> newDateTimeString = argMultimap.getValue(PREFIX_DATETIME);
        if (newDateTimeString.isPresent()) {
            DateTime newDateTime = SlotParserUtil.parseDateTime(newDateTimeString.get());
            editSlotDescriptor.setDateTime(newDateTime);
            if (newDateTime.toLocalDate().isBefore(LocalDate.EPOCH)) {
                warningMessage += Messages.WARNING_MESSAGE_DATE_TOO_EARLY;
            } else if (newDateTime.toLocalDate().isAfter(LocalDate.now().plusYears(5))) {
                warningMessage += Messages.WARNING_MESSAGE_DATE_TOO_LATE;
            }
        }

        Optional<String> newSlotDurationString = argMultimap.getValue(PREFIX_DURATION);
        if (newSlotDurationString.isPresent()) {
            editSlotDescriptor.setDuration(SlotParserUtil.parseSlotDuration(newSlotDurationString.get()));
        }

        if (!editSlotDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditSlotCommand.MESSAGE_NOT_EDITED);
        }

        return new EditSlotCommand(index, editSlotDescriptor, warningMessage);
    }
}
