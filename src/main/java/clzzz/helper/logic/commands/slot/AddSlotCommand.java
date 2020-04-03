package clzzz.helper.logic.commands.slot;

import static java.util.Objects.requireNonNull;

import clzzz.helper.logic.parser.general.CliSyntax;
import clzzz.helper.model.Model;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.logic.commands.general.Command;
import clzzz.helper.logic.commands.general.CommandResult;

/**
 * Adds a slot to the schedule.
 */
public class AddSlotCommand extends Command {

    public static final String COMMAND_WORD = "addslot";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a pet to the pet store helper. "
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "PETNAME "
            + CliSyntax.PREFIX_DATETIME + "DATETIME "
            + CliSyntax.PREFIX_DURATION + "DURATION\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "Coco "
            + CliSyntax.PREFIX_DATETIME + "16/11/2020 1300 "
            + CliSyntax.PREFIX_DURATION + "90 ";

    public static final String MESSAGE_SUCCESS = "New slot added: %1$s\n";

    private final Slot slotToAdd;
    private final String warningMessage;

    /**
     * Creates an AddSlotCommand to add the specified {@code slot}
     */
    public AddSlotCommand(Slot slot, String warningMessage) {
        requireNonNull(slot);
        slotToAdd = slot;
        this.warningMessage = warningMessage;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.addSlot(slotToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, slotToAdd) + warningMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddSlotCommand // instanceof handles nulls
                && slotToAdd.equals(((AddSlotCommand) other).slotToAdd))
                && warningMessage.equals(((AddSlotCommand) other).warningMessage);
    }
}
