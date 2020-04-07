package clzzz.helper.logic.commands.pet;

import static java.util.Objects.requireNonNull;

import clzzz.helper.commons.core.Messages;
import clzzz.helper.commons.exceptions.IllegalValueException;
import clzzz.helper.logic.commands.Command;
import clzzz.helper.logic.commands.CommandResult;
import clzzz.helper.model.Model;
import clzzz.helper.model.pet.NameContainsKeywordsPredicate;
import clzzz.helper.ui.DisplaySystemType;

/**
 * Finds and lists all pets in pet tracker whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPetCommand extends Command {

    public static final String COMMAND_WORD = "findpets";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all pets whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindPetCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws IllegalValueException {
        requireNonNull(model);
        model.updateFilteredPetList(predicate);
        model.changeDisplaySystem(DisplaySystemType.PETS);
        return new CommandResult(
                String.format(Messages.MESSAGE_PETS_LISTED_OVERVIEW, model.getFilteredPetList().size()),
                false, false, DisplaySystemType.PETS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPetCommand // instanceof handles nulls
                && predicate.equals(((FindPetCommand) other).predicate)); // state check
    }
}
