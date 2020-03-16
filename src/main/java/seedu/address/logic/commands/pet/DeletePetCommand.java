package seedu.address.logic.commands.pet;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.general.Command;
import seedu.address.logic.commands.general.CommandResult;
import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pet.Pet;

/**
 * Deletes a person identified using it's displayed index from the address book.
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
