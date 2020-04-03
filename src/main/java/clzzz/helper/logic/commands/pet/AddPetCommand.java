package clzzz.helper.logic.commands.pet;

import static clzzz.helper.logic.parser.CliSyntax.PREFIX_DOB;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_FOODLIST;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_GENDER;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_NAME;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_SPECIES;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import clzzz.helper.logic.commands.Command;
import clzzz.helper.logic.commands.CommandResult;
import clzzz.helper.logic.commands.exceptions.CommandException;
import clzzz.helper.model.Model;
import clzzz.helper.model.pet.Pet;

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
            + PREFIX_DOB + "01/01/2019 "
            + PREFIX_SPECIES + "dog "
            + PREFIX_FOODLIST + "brand A: 15 "
            + PREFIX_FOODLIST + "brand B: 20 "
            + PREFIX_TAG + "small "
            + PREFIX_TAG + "lazy ";

    public static final String MESSAGE_SUCCESS = "New pet added: %1$s\n";
    public static final String MESSAGE_DUPLICATE_PET = "This pet already exists in the pet store helper";

    private final Pet toAdd;
    private final String warningMessage;

    /**
     * Creates an AddPetCommand to add the specified {@code Pet}
     */
    public AddPetCommand(Pet pet, String warningMessage) {
        requireNonNull(pet);
        toAdd = pet;
        this.warningMessage = warningMessage;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasPet(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PET);
        }
        model.addPet(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd) + warningMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPetCommand // instanceof handles nulls
                && toAdd.equals(((AddPetCommand) other).toAdd))
                && warningMessage.equals(((AddPetCommand) other).warningMessage);
    }
}
