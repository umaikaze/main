package seedu.address.logic.commands.general;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PETS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SLOTS;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.DisplaySystemType;

/**
 * Displays the specified system.
 */
public class DisplayCommand extends Command {

    public static final String COMMAND_WORD = "display";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the display board to show the specified system. Parameters: p|s|i";

    public static final String MESSAGE_SUCCESS_PET = "Listed all pets";
    public static final String MESSAGE_SUCCESS_SCHEDULE = "Listed all slots";

    public static final String MESSAGE_INVALID_SYSTEM_TYPE = "Invalid system type specified.";

    private final DisplaySystemType type;

    public DisplayCommand(DisplaySystemType type) {
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.changeDisplaySystem(type);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_INVALID_SYSTEM_TYPE);
        }
        switch (type) {
        case PET:
            model.updateFilteredPetList(PREDICATE_SHOW_ALL_PETS);
            return new CommandResult(MESSAGE_SUCCESS_PET, false, false, type);
        case SCHEDULE:
            model.updateFilteredSlotList(PREDICATE_SHOW_ALL_SLOTS);
            return new CommandResult(MESSAGE_SUCCESS_SCHEDULE, false, false, type);
        default:
            throw new CommandException(MESSAGE_INVALID_SYSTEM_TYPE);
        }
    }
}
