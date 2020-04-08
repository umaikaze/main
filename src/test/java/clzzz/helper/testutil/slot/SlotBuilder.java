package clzzz.helper.testutil.slot;

import static clzzz.helper.testutil.pet.TypicalPets.getTypicalPetTracker;

import clzzz.helper.logic.commands.CommandTestUtil;
import clzzz.helper.model.PetTracker;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.slot.DateTime;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.model.slot.SlotDuration;

/**
 * A utility class to help with building Slot objects.
 */
public class SlotBuilder {
    public static final String DEFAULT_NAME = CommandTestUtil.VALID_NAME_COCO;
    public static final String DEFAULT_DATETIME = "1/3/2020 1200";
    public static final String DEFAULT_DURATION = "20";
    private PetTracker typicalPetTracker = getTypicalPetTracker();
    private Pet pet;
    private DateTime dateTime;
    private SlotDuration duration;

    public SlotBuilder() {
        pet = typicalPetTracker.getPet(new Name(DEFAULT_NAME));
        dateTime = new DateTime(DEFAULT_DATETIME);
        duration = new SlotDuration(DEFAULT_DURATION);
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
     * Sets the {@code DateTime} of the {@code Slot} that we are building.
     */
    public SlotBuilder withDateTime(String dateTime) {
        this.dateTime = new DateTime(dateTime);
        return this;
    }

    /**
     * Sets the {@code SlotDuration} of the {@code Slot} that we are building.
     */
    public SlotBuilder withDuration(String duration) {
        this.duration = new SlotDuration(duration);
        return this;
    }

    public Slot build() {
        return new Slot(pet, dateTime, duration);
    }
}
