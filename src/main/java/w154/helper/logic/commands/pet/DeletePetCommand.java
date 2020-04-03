package w154.helper.logic.commands.pet;

import static java.util.Objects.requireNonNull;

import java.util.List;

import w154.helper.commons.core.Messages;
import w154.helper.commons.core.index.Index;
import w154.helper.logic.commands.general.Command;
import w154.helper.logic.commands.general.CommandResult;
import w154.helper.logic.commands.general.exceptions.CommandException;
import w154.helper.model.Model;
import w154.helper.model.pet.Pet;

/**
 * Deletes a pet identified using it's displayed index from the pet tracker.
 */
public class DeletePetCommand extends Command {

    public static final String COMMAND_WORD = "deletepet";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the pet identified by the index number used in the displayed pet list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PET_SUCCESS = "Deleted Pet: %1$s";

    private final Index targetIndex;

    public DeletePetCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Pet> lastShownList = model.getFilteredPetList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
        }

        Pet petToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePet(petToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PET_SUCCESS, petToDelete));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePetCommand // instanceof handles nulls
                && targetIndex.equals(((DeletePetCommand) other).targetIndex)); // state check
    }
}
