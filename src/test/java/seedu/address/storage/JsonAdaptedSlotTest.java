package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.util.DateTimeUtil.DATETIME_FORMAT;
import static seedu.address.storage.JsonAdaptedSlot.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.pet.TypicalPets.getTypicalPetTrackerWithSlots;
import static seedu.address.testutil.slot.TypicalSlots.GARFIELD_SLOT;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.slot.SlotParserUtil;
import seedu.address.model.PetTracker;
import seedu.address.model.pet.Name;

public class JsonAdaptedSlotTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DATE_TIME = "01 Mar 2010";
    private static final String INVALID_DURATION = "0";

    private static final String VALID_NAME = GARFIELD_SLOT.getPet().getName().fullName;
    private static final String VALID_DATE_TIME = GARFIELD_SLOT.getDateTime().format(DATETIME_FORMAT);
    private static final String VALID_DURATION = GARFIELD_SLOT.getDuration().toString();

    private PetTracker typicalPetTracker = getTypicalPetTrackerWithSlots();

    @Test
    public void toModelType_validSlotDetails_returnsSlot() throws Exception {
        JsonAdaptedSlot slot = new JsonAdaptedSlot(GARFIELD_SLOT);
        assertEquals(GARFIELD_SLOT, slot.toModelType(typicalPetTracker));
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedSlot slot =
                new JsonAdaptedSlot(INVALID_NAME, VALID_DATE_TIME, VALID_DURATION);
        String expectedMessage = SlotParserUtil.MESSAGE_INVALID_PETNAME;
        assertThrows(IllegalValueException.class, expectedMessage, () -> slot.toModelType(typicalPetTracker));
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedSlot slot = new JsonAdaptedSlot(null, VALID_DATE_TIME, VALID_DURATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> slot.toModelType(typicalPetTracker));
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        JsonAdaptedSlot slot = new JsonAdaptedSlot(VALID_NAME, INVALID_DATE_TIME, VALID_DURATION);
        String expectedMessage = SlotParserUtil.MESSAGE_INVALID_DATETIME;
        assertThrows(IllegalValueException.class, expectedMessage, () -> slot.toModelType(typicalPetTracker));
    }

    @Test
    public void toModelType_nullDateTime_throwsIllegalValueException() {
        JsonAdaptedSlot slot = new JsonAdaptedSlot(VALID_NAME, null, VALID_DURATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> slot.toModelType(typicalPetTracker));
    }

    @Test
    public void toModelType_invalidDuration_throwsIllegalValueException() {
        JsonAdaptedSlot slot = new JsonAdaptedSlot(VALID_NAME, VALID_DATE_TIME, INVALID_DURATION);
        String expectedMessage = SlotParserUtil.MESSAGE_INVALID_DURATION;
        assertThrows(IllegalValueException.class, expectedMessage, () -> slot.toModelType(typicalPetTracker));
    }

    @Test
    public void toModelType_nullDuration_throwsIllegalValueException() {
        JsonAdaptedSlot slot = new JsonAdaptedSlot(VALID_NAME, VALID_DATE_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Duration.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> slot.toModelType(typicalPetTracker));
    }

}
