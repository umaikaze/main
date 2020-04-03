package clzzz.helper.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import clzzz.helper.commons.exceptions.IllegalValueException;
import clzzz.helper.model.PetTracker;
import clzzz.helper.model.ReadOnlyPetTracker;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.slot.Slot;

/**
 * An Immutable PetTracker that is serializable to JSON format.
 */
@JsonRootName(value = "pettracker")
class JsonSerializablePetTracker {

    public static final String MESSAGE_DUPLICATE_PET = "Pets list contains duplicate pet(s).";

    private final List<JsonAdaptedPet> pets = new ArrayList<>();
    private final List<JsonAdaptedSlot> slots = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePetTracker} with the given pets.
     */
    @JsonCreator
    public JsonSerializablePetTracker(@JsonProperty("pets") List<JsonAdaptedPet> pets,
                                      @JsonProperty("slots") List<JsonAdaptedSlot> slots) {
        this.pets.addAll(pets);
        this.slots.addAll(slots);
    }

    /**
     * Converts a given {@code ReadOnlyPetTracker} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePetTracker}.
     */
    public JsonSerializablePetTracker(ReadOnlyPetTracker source) {
        pets.addAll(source.getPetList().stream().map(JsonAdaptedPet::new).collect(Collectors.toList()));
        slots.addAll(source.getSlotList().stream().map(JsonAdaptedSlot::new).collect(Collectors.toList()));
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
        for (JsonAdaptedSlot jsonAdaptedSlot: slots) {
            Slot slot = jsonAdaptedSlot.toModelType(petTracker);
            petTracker.addSlot(slot);
        }
        return petTracker;
    }
}
