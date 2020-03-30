package seedu.address.storage;

import static seedu.address.logic.parser.slot.SlotParserUtil.MESSAGE_INVALID_DATETIME;
import static seedu.address.logic.parser.slot.SlotParserUtil.MESSAGE_INVALID_DURATION;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.model.PetTracker;
import seedu.address.model.pet.Name;
import seedu.address.model.slot.Slot;

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
        dateTime = source.getDateTime().format(DateTimeUtil.DATETIME_FORMAT);
        duration = String.valueOf(source.getDuration().toMinutes());
    }

    /**
     * Converts this Jackson-friendly adapted slot object into the model's {@code Slot} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Slot toModelType(PetTracker petTracker) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        final LocalDateTime modelDateTime;
        try {
            modelDateTime = LocalDateTime.parse(dateTime, DateTimeUtil.DATETIME_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATETIME);
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

        return new Slot(petTracker.getPet(modelName), modelDateTime, modelDuration);
    }
}
