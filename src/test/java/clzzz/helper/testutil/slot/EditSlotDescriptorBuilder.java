package clzzz.helper.testutil.slot;

import static clzzz.helper.testutil.pet.TypicalPets.getTypicalPetTracker;

import clzzz.helper.logic.commands.slot.EditSlotCommand.EditSlotDescriptor;
import clzzz.helper.model.PetTracker;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.slot.DateTime;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.model.slot.SlotDuration;

/**
 * A utility class to help with building EditSlotDescriptor objects.
 */
public class EditSlotDescriptorBuilder {

    private PetTracker typicalPetTracker = getTypicalPetTracker();
    private EditSlotDescriptor descriptor;

    public EditSlotDescriptorBuilder() {
        descriptor = new EditSlotDescriptor();
    }

    public EditSlotDescriptorBuilder(EditSlotDescriptor descriptor) {
        this.descriptor = new EditSlotDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditSlotDescriptor} with fields containing {@code pet}'s details
     */
    public EditSlotDescriptorBuilder(Slot slot) {
        descriptor = new EditSlotDescriptor();
        descriptor.setPet(slot.getPet());
        descriptor.setDateTime(slot.getDateTime());
        descriptor.setDuration(slot.getDuration());
    }

    /**
     * Sets the {@code Pet} of the {@code EditSlotDescriptor} that we are building.
     */
    public EditSlotDescriptorBuilder withPet(String name) {
        descriptor.setPet(typicalPetTracker.getPet(new Name(name)));
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code EditSlotDescriptor} that we are building.
     */
    public EditSlotDescriptorBuilder withDateTime(String dateTime) {
        descriptor.setDateTime(new DateTime(dateTime));
        return this;
    }

    /**
     * Sets the {@code SlotDuration} of the {@code EditSlotDescriptor} that we are building.
     */
    public EditSlotDescriptorBuilder withDuration(String dateOfBirth) {
        descriptor.setDuration(new SlotDuration(dateOfBirth));
        return this;
    }

    public EditSlotDescriptor build() {
        return descriptor;
    }
}
