package seedu.address.logic.commands.general;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Whether or not help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * Whether or not the application should exit.
     */
    private final boolean exit;

    private final boolean showStats;
    /**
     * Whether or not the system to be displayed has changed.
     */
    private final boolean displayChanged;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */

    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean displayChanged, boolean showStats) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.displayChanged = displayChanged;
        this.showStats = showStats;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields,
     * while setting {@code changeDisplay} to its default value.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowStats() {
        return showStats;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean hasDisplayChanged() {
        return displayChanged;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showStats == otherCommandResult.showStats
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && displayChanged == otherCommandResult.displayChanged;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, displayChanged, showStats);
    }

}
