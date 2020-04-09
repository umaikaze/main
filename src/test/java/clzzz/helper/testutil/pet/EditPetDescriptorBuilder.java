package clzzz.helper.testutil.pet;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import clzzz.helper.logic.commands.pet.EditPetCommand.EditPetDescriptor;
import clzzz.helper.model.pet.DateOfBirth;
import clzzz.helper.model.pet.Gender;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.pet.Species;
import clzzz.helper.model.pet.Tag;

/**
 * A utility class to help with building EditPetDescriptor objects.
 */
public class EditPetDescriptorBuilder {

    private EditPetDescriptor descriptor;

    public EditPetDescriptorBuilder() {
        descriptor = new EditPetDescriptor();
    }

    public EditPetDescriptorBuilder(EditPetDescriptor descriptor) {
        this.descriptor = new EditPetDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPetDescriptor} with fields containing {@code pet}'s details
     */
    public EditPetDescriptorBuilder(Pet pet) {
        descriptor = new EditPetDescriptor();
        descriptor.setName(pet.getName());
        descriptor.setGender(pet.getGender());
        descriptor.setDateOfBirth(pet.getDateOfBirth());
        descriptor.setSpecies(pet.getSpecies());
        descriptor.setFoodList(pet.getFoodList());
        descriptor.setTags(pet.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPetDescriptor} that we are building.
     */
    public EditPetDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code EditPetDescriptor} that we are building.
     */
    public EditPetDescriptorBuilder withGender(String gender) {
        descriptor.setGender(Gender.valueOf(gender.toUpperCase()));
        return this;
    }

    /**
     * Sets the {@code DateOfBirth} of the {@code EditPetDescriptor} that we are building.
     */
    public EditPetDescriptorBuilder withDateOfBirth(String dateOfBirth) {
        descriptor.setDateOfBirth(new DateOfBirth(dateOfBirth));
        return this;
    }

    /**
     * Sets the {@code Species} of the {@code EditPetDescriptor} that we are building.
     */
    public EditPetDescriptorBuilder withSpecies(String species) {
        descriptor.setSpecies(new Species(species));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPetDescriptor}
     * that we are building.
     */
    public EditPetDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPetDescriptor build() {
        return descriptor;
    }
}
