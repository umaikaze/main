package clzzz.helper.testutil;

import clzzz.helper.model.PetTracker;
import clzzz.helper.model.pet.Pet;

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
    public PetTrackerBuilder withPet(Pet pet) {
        petTracker.addPet(pet);
        return this;
    }

    public PetTracker build() {
        return petTracker;
    }
}
