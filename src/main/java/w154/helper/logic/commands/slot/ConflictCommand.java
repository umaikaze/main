package w154.helper.logic.commands.slot;

import static java.util.Objects.requireNonNull;

import w154.helper.commons.core.Messages;
import w154.helper.logic.commands.general.Command;
import w154.helper.logic.commands.general.CommandResult;
import w154.helper.model.Model;
import w154.helper.model.slot.SlotConflictPredicate;
import w154.helper.ui.DisplaySystemType;

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
        model.updateFilteredSlotList(Model.PREDICATE_SHOW_ALL_SLOTS);
        model.updateFilteredSlotList(new SlotConflictPredicate(model.getFilteredSlotList()));
        return new CommandResult(
                String.format(Messages.MESSAGE_SLOTS_LISTED_OVERVIEW, model.getFilteredSlotList().size()),
                false, false, DisplaySystemType.SCHEDULE, false);
    }
}
