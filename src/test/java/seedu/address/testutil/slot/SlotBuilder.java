package seedu.address.testutil.slot;

import static seedu.address.commons.util.DateTimeUtil.DATETIME_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COCO;
import static seedu.address.testutil.pet.TypicalPets.getTypicalPetTracker;

import java.time.Duration;
import java.time.LocalDateTime;

import seedu.address.model.PetTracker;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;

/**
 * A utility class to help with building Slot objects.
 */
public class SlotBuilder {
    public static final String DEFAULT_NAME = VALID_NAME_COCO;
    public static final String DEFAULT_DATETIME = "1/3/2020 1200";
    public static final String DEFAULT_DURATION = "20";
    private PetTracker typicalPetTracker = getTypicalPetTracker();
    private Pet pet;
    private LocalDateTime dateTime;
    private Duration duration;

    public SlotBuilder() {
        pet = typicalPetTracker.getPet(new Name(DEFAULT_NAME));
        dateTime = LocalDateTime.parse(DEFAULT_DATETIME, DATETIME_FORMAT);
        duration = Duration.ofMinutes(Long.parseLong(DEFAULT_DURATION));
    }

    /**
     * Initializes the SlotBuilder with the data of {@code slotToCopy}.
     */
    public SlotBuilder(Slot slotToCopy) {
        pet = slotToCopy.getPet();
        dateTime = slotToCopy.getDateTime();
        duration = slotToCopy.getDuration();
    }

    /**
     * Sets the {@code Pet} of the {@code Slot} that we are building.
     */
    public SlotBuilder withPet(String name) {
        this.pet = typicalPetTracker.getPet(new Name(name));;
        return this;
    }

    /**
     * Sets the {@code LocalDateTime} of the {@code Slot} that we are building.
     */
    public SlotBuilder withDateTime(String dateTime) {
        this.dateTime = LocalDateTime.parse(dateTime, DATETIME_FORMAT);
        return this;
    }

    /**
     * Sets the {@code LocalDateTime} of the {@code Slot} that we are building.
     */
    public SlotBuilder withDuration(String duration) {
        this.duration = Duration.ofMinutes(Long.parseLong(duration));
        return this;
    }

    public Slot build() {
        return new Slot(pet, dateTime, duration);
    }
}
