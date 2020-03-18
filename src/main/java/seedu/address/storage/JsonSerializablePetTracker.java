package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.PetTracker;
import seedu.address.model.ReadOnlyPetTracker;
import seedu.address.model.pet.Pet;

/**
 * An Immutable PetTracker that is serializable to JSON format.
 */
@JsonRootName(value = "pettracker")
class JsonSerializablePetTracker {

    public static final String MESSAGE_DUPLICATE_PET = "Pets list contains duplicate pet(s).";

    private final List<JsonAdaptedPet> pets = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePetTracker} with the given pets.
     */
    @JsonCreator
    public JsonSerializablePetTracker(@JsonProperty("pets") List<JsonAdaptedPet> pets) {
        this.pets.addAll(pets);
    }

    /**
     * Converts a given {@code ReadOnlyPetTracker} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePetTracker}.
     */
    public JsonSerializablePetTracker(ReadOnlyPetTracker source) {
        pets.addAll(source.getPetList().stream().map(JsonAdaptedPet::new).collect(Collectors.toList()));
        System.out.println(pets); //TODO: remove after debug
    }

    /**
     * Converts this pet tracker into the model's {@code PetTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PetTracker toModelType() throws IllegalValueException {
        PetTracker petTracker = new PetTracker();
        for (JsonAdaptedPet jsonAdaptedPet : pets) {
            Pet pet = jsonAdaptedPet.toModelType();
            if (petTracker.hasPet(pet)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PET);
            }
            petTracker.addPet(pet);
        }
        return petTracker;
    }

}
