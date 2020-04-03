package w154.helper.testutil.slot;

import static w154.helper.testutil.pet.TypicalPets.getTypicalPetTracker;

import java.time.Duration;
import java.time.LocalDateTime;

import w154.helper.logic.commands.slot.EditSlotCommand.EditSlotDescriptor;
import w154.helper.model.PetTracker;
import w154.helper.model.pet.Name;
import w154.helper.model.slot.Slot;
import w154.helper.commons.util.DateTimeUtil;

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
     * Sets the {@code LocalDateTime} of the {@code EditSlotDescriptor} that we are building.
     */
    public EditSlotDescriptorBuilder withDateTime(String dateTime) {
        descriptor.setDateTime(LocalDateTime.parse(dateTime, DateTimeUtil.DATETIME_FORMAT));
        return this;
    }

    /**
     * Sets the {@code Duration} of the {@code EditSlotDescriptor} that we are building.
     */
    public EditSlotDescriptorBuilder withDuration(String dateOfBirth) {
        descriptor.setDuration(Duration.ofMinutes(Long.parseLong(dateOfBirth)));
        return this;
    }

    public EditSlotDescriptor build() {
        return descriptor;
    }
}
