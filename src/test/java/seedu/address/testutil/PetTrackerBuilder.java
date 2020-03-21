package seedu.address.testutil;

import seedu.address.model.PetTracker;
import seedu.address.model.pet.Pet;

/**
 * A utility class to help with building PetTracker objects.
 * Example usage: <br>
 *     {@code PetTracker pt = new PetTrackerBuilder().withPet(COCO).build();}
 */
public class PetTrackerBuilder {

    private PetTracker petTracker;

    public PetTrackerBuilder() {
        petTracker = new PetTracker();
    }

    public PetTrackerBuilder(PetTracker petTracker) {
        this.petTracker = petTracker;
    }

    /**
     * Adds a new {@code Pet} to the {@code PetTracker} that we are building.
     */
    public PetTrackerBuilder withPet(Pet person) {
        petTracker.addPet(person);
        return this;
    }

    public PetTracker build() {
        return petTracker;
    }
}
