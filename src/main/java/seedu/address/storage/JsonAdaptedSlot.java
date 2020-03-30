package seedu.address.storage;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;
import seedu.address.model.tag.Tag;

public class JsonAdaptedSlot {
    private final JsonAdaptedPet pet;
    private final LocalDateTime dateTime;
    private final Duration duration;


    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Slot's %s field is missing!";

    /**
     * Constructs a {@code JsonAdaptedSlot} with the given {@code pet}, {@code dateTime} and {@code duration}.
     */
    @JsonCreator
    public JsonAdaptedSlot(@JsonProperty("pet") JsonAdaptedPet pet, @JsonProperty("dateTime")LocalDateTime dateTime,
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
