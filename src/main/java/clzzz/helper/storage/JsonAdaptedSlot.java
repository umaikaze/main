package clzzz.helper.storage;

import static clzzz.helper.logic.parser.slot.SlotParserUtil.MESSAGE_INVALID_PETNAME;
import static clzzz.helper.logic.parser.slot.SlotParserUtil.MESSAGE_PET_DOES_NOT_EXIST;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import clzzz.helper.commons.exceptions.IllegalValueException;
import clzzz.helper.logic.parser.exceptions.ParseException;
import clzzz.helper.model.PetTracker;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.pet.exceptions.PetNotFoundException;
import clzzz.helper.model.slot.DateTime;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.model.slot.SlotDuration;

/**
 * Jackson-friendly version of {@link Slot}.
 */
public class JsonAdaptedSlot {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Slot's %s field is missing!";

    private final String name;
    private final String dateTime;
    private final String duration;

    /**
     * Constructs a {@code JsonAdaptedSlot} with the given {@code pet},
     * {@code dateTime} and {@code duration}.
     */
    @JsonCreator
    public JsonAdaptedSlot(@JsonProperty("name") String name, @JsonProperty("dateTime") String dateTime,
            @JsonProperty("duration") String duration) {
        this.name = name;
        this.dateTime = dateTime;
        this.duration = duration;
    }

    /**
     * Converts a given {@code Slot} into this class for Jackson use.
     */
    public JsonAdaptedSlot(Slot source) {
        name = source.getPet().getName().fullName;
        dateTime = source.getDateTime().toString();
        duration = source.getDuration().toString();
    }

    /**
     * Converts this Jackson-friendly adapted slot object into the model's
     * {@code Slot} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted slot.
     */
    public Slot toModelType(PetTracker petTracker) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(MESSAGE_INVALID_PETNAME);
        }
        final Pet modelPet;
        try {
            modelPet = petTracker.getPet(new Name(name));
        } catch (PetNotFoundException e) {
            throw new ParseException(MESSAGE_PET_DOES_NOT_EXIST);
        }

        if (dateTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        final DateTime modelDateTime = new DateTime(dateTime);

        if (duration == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, SlotDuration.class.getSimpleName()));
        }
        if (!SlotDuration.isValidDuration(duration)) {
            throw new IllegalValueException(
                String.format(SlotDuration.MESSAGE_CONSTRAINTS));
        }
        final SlotDuration modelDuration = new SlotDuration(duration);

        return new Slot(modelPet, modelDateTime, modelDuration);
    }
}
