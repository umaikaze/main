package clzzz.helper.logic.commands.slot;

import static clzzz.helper.model.Model.PREDICATE_SHOW_ALL_SLOTS;
import static java.util.Objects.requireNonNull;

import clzzz.helper.commons.core.Messages;
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
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSlotList(PREDICATE_SHOW_ALL_SLOTS);
        model.updateFilteredSlotList(new SlotConflictPredicate(model.getFilteredSlotList()));
        return new CommandResult(
                String.format(Messages.MESSAGE_SLOTS_LISTED_OVERVIEW, model.getFilteredSlotList().size()),
                false, false, DisplaySystemType.SCHEDULE, false);
    }
}
