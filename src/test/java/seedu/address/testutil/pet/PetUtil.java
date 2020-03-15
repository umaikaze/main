package seedu.address.testutil.pet;

import static seedu.address.logic.parser.pet.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.pet.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.pet.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.pet.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.pet.AddPetCommand;
import seedu.address.logic.commands.pet.EditPetCommand.EditPetDescriptor;
import seedu.address.model.pet.Pet;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Pet.
 */
public class PetUtil {

    /**
     * Returns an add command string for adding the {@code pet}.
     */
    public static String getAddPetCommand(Pet pet) {
        return AddPetCommand.COMMAND_WORD + " " + getPetDetails(pet);
    }

    /**
     * Returns the part of command string for the given {@code pet}'s details.
     */
    public static String getPetDetails(Pet pet) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + pet.getName().fullName + " ");
        sb.append(PREFIX_GENDER + pet.getName().toString() + " ");
        sb.append(PREFIX_DOB+ pet.getGender().toString() + " ");
        sb.append(PREFIX_SPECIES + pet.getSpecies().toString() + " ");
        pet.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPetDescriptor}'s details.
     */
    public static String getEditPetDescriptorDetails(EditPetDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getDateOfBirth().ifPresent(dateOfBirth -> sb.append(PREFIX_DOB).append(dateOfBirth.value).append(" "));
        descriptor.getGender().ifPresent(gender -> sb.append(PREFIX_GENDER).append(gender.toString()).append(" "));
        descriptor.getSpecies().ifPresent(species -> sb.append(PREFIX_SPECIES).append(species.toString()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
