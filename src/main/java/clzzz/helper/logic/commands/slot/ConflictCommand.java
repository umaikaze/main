package clzzz.helper.logic.commands.slot;

import static java.util.Objects.requireNonNull;

import clzzz.helper.commons.core.Messages;
import clzzz.helper.commons.exceptions.IllegalValueException;
import clzzz.helper.logic.commands.Command;
import clzzz.helper.logic.commands.CommandResult;
import clzzz.helper.model.Model;
import clzzz.helper.model.slot.SlotConflictPredicate;
import clzzz.helper.ui.DisplaySystemType;

/**
 * Finds and lists all slots in the schedule which have conflicts.
 */
public class ConflictCommand extends Command {
    public static final String COMMAND_WORD = "conflicts";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all slots that have conflicts.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) throws IllegalValueException {
        requireNonNull(model);
        model.updateFilteredSlotList(new SlotConflictPredicate(model.getFilteredSlotList()));
        model.changeDisplaySystem(DisplaySystemType.SCHEDULE);
        return new CommandResult(
                String.format(Messages.MESSAGE_SLOTS_LISTED_OVERVIEW, model.getFilteredSlotList().size()),
                false, false, DisplaySystemType.SCHEDULE);
    }
}
