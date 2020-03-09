package seedu.address.logic.petCommands;

import seedu.address.logic.generalCommands.Command;
import seedu.address.logic.generalCommands.CommandResult;
import seedu.address.model.PshModel;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.PshModel.PREDICATE_SHOW_ALL_PETS;

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
