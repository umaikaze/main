package w154.helper.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static w154.helper.testutil.Assert.assertThrows;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import w154.helper.commons.exceptions.IllegalValueException;
import w154.helper.logic.parser.slot.SlotParserUtil;
import w154.helper.model.PetTracker;
import w154.helper.model.pet.Name;
import w154.helper.commons.util.DateTimeUtil;
import w154.helper.testutil.Assert;
import w154.helper.testutil.pet.TypicalPets;
import w154.helper.testutil.slot.TypicalSlots;

public class JsonAdaptedSlotTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DATE_TIME = "01 Mar 2010";
    private static final String INVALID_DURATION = "0";

    private static final String VALID_NAME = TypicalSlots.GARFIELD_SLOT.getPet().getName().fullName;
    private static final String VALID_DATE_TIME = TypicalSlots.GARFIELD_SLOT.getDateTime().format(DateTimeUtil.DATETIME_FORMAT);
    private static final String VALID_DURATION = TypicalSlots.GARFIELD_SLOT.getDuration().toString();

    private PetTracker typicalPetTracker = TypicalPets.getTypicalPetTrackerWithSlots();

    @Test
    public void toModelType_validSlotDetails_returnsSlot() throws Exception {
        JsonAdaptedSlot slot = new JsonAdaptedSlot(TypicalSlots.GARFIELD_SLOT);
        Assertions.assertEquals(TypicalSlots.GARFIELD_SLOT, slot.toModelType(typicalPetTracker));
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedSlot slot =
                new JsonAdaptedSlot(INVALID_NAME, VALID_DATE_TIME, VALID_DURATION);
        String expectedMessage = SlotParserUtil.MESSAGE_INVALID_PETNAME;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> slot.toModelType(typicalPetTracker));
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedSlot slot = new JsonAdaptedSlot(null, VALID_DATE_TIME, VALID_DURATION);
        String expectedMessage = String.format(JsonAdaptedSlot.MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> slot.toModelType(typicalPetTracker));
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        JsonAdaptedSlot slot = new JsonAdaptedSlot(VALID_NAME, INVALID_DATE_TIME, VALID_DURATION);
        String expectedMessage = SlotParserUtil.MESSAGE_INVALID_DATETIME;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> slot.toModelType(typicalPetTracker));
    }

    @Test
    public void toModelType_nullDateTime_throwsIllegalValueException() {
        JsonAdaptedSlot slot = new JsonAdaptedSlot(VALID_NAME, null, VALID_DURATION);
        String expectedMessage = String.format(JsonAdaptedSlot.MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> slot.toModelType(typicalPetTracker));
    }

    @Test
    public void toModelType_invalidDuration_throwsIllegalValueException() {
        JsonAdaptedSlot slot = new JsonAdaptedSlot(VALID_NAME, VALID_DATE_TIME, INVALID_DURATION);
        String expectedMessage = SlotParserUtil.MESSAGE_INVALID_DURATION;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> slot.toModelType(typicalPetTracker));
    }

    @Test
    public void toModelType_nullDuration_throwsIllegalValueException() {
        JsonAdaptedSlot slot = new JsonAdaptedSlot(VALID_NAME, VALID_DATE_TIME, null);
        String expectedMessage = String.format(JsonAdaptedSlot.MISSING_FIELD_MESSAGE_FORMAT, Duration.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> slot.toModelType(typicalPetTracker));
    }

}
