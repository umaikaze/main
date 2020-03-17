package seedu.address.logic.commands.pet;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PETS;

import seedu.address.logic.commands.general.Command;
import seedu.address.logic.commands.general.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all pets in the pet tracker to the user.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all pets.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPetList(PREDICATE_SHOW_ALL_PETS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
