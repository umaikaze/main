package seedu.address.storage;

import java.time.Duration;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.slot.Slot;

/**
 * Jackson-friendly version of {@link Slot}.
 */
public class JsonAdaptedSlot {
    private final JsonAdaptedPet pet;
    private final LocalDateTime dateTime;
    private final Duration duration;

    /**
     * Constructs a {@code JsonAdaptedSlot} with the given {@code pet}, {@code dateTime} and {@code duration}.
     */
    @JsonCreator
    public JsonAdaptedSlot(@JsonProperty("pet") JsonAdaptedPet pet, @JsonProperty("dateTime") LocalDateTime dateTime,
                           @JsonProperty("duration") Duration duration) {
        this.pet = pet;
        this.dateTime = dateTime;
        this.duration = duration;
    }

    /**
     * Converts a given {@code Slot} into this class for Jackson use.
     */
    public JsonAdaptedSlot(Slot source) {
        pet = new JsonAdaptedPet(source.getPet());
        dateTime = source.getDateTime();
        duration = source.getDuration();
    }

    /**
     * Converts this Jackson-friendly adapted slot object into the model's {@code Slot} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Slot toModelType() throws IllegalValueException {
        return new Slot(pet.toModelType(), dateTime, duration);
    }
}
