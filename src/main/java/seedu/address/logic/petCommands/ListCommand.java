package seedu.address.logic.petcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.PshModel.PREDICATE_SHOW_ALL_PETS;

import seedu.address.logic.generalcommands.Command;
import seedu.address.logic.generalcommands.CommandResult;
import seedu.address.model.PshModel;

/**
 * Lists all pets in the pet tracker to the user.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all pets.";


    @Override
    public CommandResult execute(PshModel model) {
        requireNonNull(model);
        model.updateFilteredPetList(PREDICATE_SHOW_ALL_PETS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
