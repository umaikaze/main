package clzzz.helper.storage;

import static clzzz.helper.commons.util.DateTimeUtil.DATETIME_FORMAT;
import static clzzz.helper.logic.parser.slot.SlotParserUtil.MESSAGE_INVALID_DATETIME;
import static clzzz.helper.logic.parser.slot.SlotParserUtil.MESSAGE_INVALID_DURATION;
import static clzzz.helper.logic.parser.slot.SlotParserUtil.MESSAGE_INVALID_PETNAME;
import static clzzz.helper.logic.parser.slot.SlotParserUtil.MESSAGE_PET_DOES_NOT_EXIST;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import clzzz.helper.commons.exceptions.IllegalValueException;
import clzzz.helper.logic.parser.general.exceptions.ParseException;
import clzzz.helper.model.PetTracker;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.pet.exceptions.PetNotFoundException;
import clzzz.helper.model.slot.Slot;

/**
 * Jackson-friendly version of {@link Slot}.
 */
public class JsonAdaptedSlot {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Slot's %s field is missing!";

    private final String name;
    private final String dateTime;
    private final String duration;


    /**
     * Constructs a {@code JsonAdaptedSlot} with the given {@code pet}, {@code dateTime} and {@code duration}.
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
        dateTime = source.getDateTime().format(DATETIME_FORMAT);
        duration = String.valueOf(source.getDuration().toMinutes());
    }

    /**
     * Converts this Jackson-friendly adapted slot object into the model's {@code Slot} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted slot.
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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDateTime.class.getSimpleName()));
        }
        final LocalDateTime modelDateTime;
        try {
            modelDateTime = LocalDateTime.parse(dateTime, DATETIME_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATETIME);
        }

        if (duration == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Duration.class.getSimpleName()));
        }
        final Duration modelDuration;
        try {
            modelDuration = Duration.ofMinutes(Long.parseLong(duration));
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_DURATION);
        }
        if (modelDuration.isNegative() || modelDuration.isZero()) {
            throw new ParseException(MESSAGE_INVALID_DURATION);
        }

        return new Slot(modelPet, modelDateTime, modelDuration);
    }
}
