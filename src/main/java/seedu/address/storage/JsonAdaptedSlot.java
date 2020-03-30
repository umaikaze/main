package seedu.address.storage;

import java.time.Duration;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.PetTracker;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;

/**
 * Jackson-friendly version of {@link Slot}.
 */
public class JsonAdaptedSlot {
    private final String pet;
    private final LocalDateTime dateTime;
    private final Duration duration;

    /**
     * Constructs a {@code JsonAdaptedSlot} with the given {@code pet}, {@code dateTime} and {@code duration}.
     */
    @JsonCreator
    public JsonAdaptedSlot(@JsonProperty("pet") String pet, @JsonProperty("dateTime") LocalDateTime dateTime,
                           @JsonProperty("duration") Duration duration) {
        this.pet = pet;
        this.dateTime = dateTime;
        this.duration = duration;
    }

    /**
     * Converts a given {@code Slot} into this class for Jackson use.
     */
    public JsonAdaptedSlot(Slot source) {
        pet = source.getPet().getName().fullName;
        dateTime = source.getDateTime();
        duration = source.getDuration();
    }

    /**
     * Converts this Jackson-friendly adapted slot object into the model's {@code Slot} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Slot toModelType(PetTracker petTracker) throws IllegalValueException {
        Name petName = new Name(pet);
        return new Slot(petTracker.getPet(petName), dateTime, duration);
    }
}
