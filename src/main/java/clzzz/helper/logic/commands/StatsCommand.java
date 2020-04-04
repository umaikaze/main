package clzzz.helper.logic.commands;

import static java.util.Objects.requireNonNull;

import clzzz.helper.model.Model;
import clzzz.helper.ui.DisplaySystemType;

/**
 * Shows the overall statistics.
 */
public class StatsCommand extends Command {
    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_SUCCESS = "Overall statistics for pets, recent schedule, and list of food.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateAll();
        model.setCurrentDisplaySystemType(DisplaySystemType.STATISTICS);
        return new CommandResult(MESSAGE_SUCCESS, false, false, DisplaySystemType.STATISTICS);
    }

}
