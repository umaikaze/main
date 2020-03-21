package seedu.address.testutil.slot;

import static seedu.address.commons.util.DateTimeUtil.DATETIME_FORMAT;

import java.time.Duration;
import java.time.LocalDateTime;

import seedu.address.logic.commands.slot.EditSlotCommand.EditSlotDescriptor;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;

/**
 * A utility class to help with building EditSlotDescriptor objects.
 */
public class EditSlotDescriptorBuilder {

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
     * Sets the {@code Name} of the {@code EditSlotDescriptor} that we are building.
     */
    public EditSlotDescriptorBuilder withPet(Pet pet) {
        descriptor.setPet(pet);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code EditSlotDescriptor} that we are building.
     */
    public EditSlotDescriptorBuilder withDateTime(String dateTime) {
        descriptor.setDateTime(LocalDateTime.parse(dateTime, DATETIME_FORMAT));
        return this;
    }

    /**
     * Sets the {@code DateOfBirth} of the {@code EditSlotDescriptor} that we are building.
     */
    public EditSlotDescriptorBuilder withDuration(String dateOfBirth) {
        descriptor.setDuration(Duration.ofMinutes(Long.parseLong(dateOfBirth)));
        return this;
    }

    public EditSlotDescriptor build() {
        return descriptor;
    }
}
