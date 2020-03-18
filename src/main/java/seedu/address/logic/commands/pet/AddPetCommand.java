package seedu.address.logic.commands.pet;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.pet.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.pet.CliSyntax.PREFIX_FOODLIST;
import static seedu.address.logic.parser.pet.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.pet.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.pet.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.pet.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.general.Command;
import seedu.address.logic.commands.general.CommandResult;
import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pet.Pet;

/**
 * Adds a pet to the pet tracker.
 */
public class AddPetCommand extends Command {

    public static final String COMMAND_WORD = "addpet";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a pet to the pet store helper. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_GENDER + "GENDER "
            + PREFIX_DOB + "DATE OF BIRTH "
            + PREFIX_SPECIES + "SPECIES "
            + PREFIX_FOODLIST + "FOOD NAME: AMOUNT "
            + "[" + PREFIX_FOODLIST + "FOOD NAME: AMOUNT]"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Teddy "
            + PREFIX_GENDER + "male "
            + PREFIX_DOB + "01-01-2019 "
            + PREFIX_SPECIES + "dog "
            + PREFIX_FOODLIST + "brand A: 15 "
            + PREFIX_FOODLIST + "brand B: 20 "
            + PREFIX_TAG + "small "
            + PREFIX_TAG + "lazy ";

    public static final String MESSAGE_SUCCESS = "New pet added: %1$s";
    public static final String MESSAGE_DUPLICATE_PET = "This pet already exists in the pet store helper";

    private final Pet toAdd;

    /**
     * Creates an AddPetCommand to add the specified {@code Pet}
     */
    public AddPetCommand(Pet pet) {
        requireNonNull(pet);
        toAdd = pet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasPet(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PET);
        }
        model.addPet(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPetCommand // instanceof handles nulls
                && toAdd.equals(((AddPetCommand) other).toAdd));
    }
}
