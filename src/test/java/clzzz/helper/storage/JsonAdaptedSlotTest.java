package clzzz.helper.storage;

import static clzzz.helper.storage.JsonAdaptedSlot.MISSING_FIELD_MESSAGE_FORMAT;
import static clzzz.helper.testutil.Assert.assertThrows;
import static clzzz.helper.testutil.pet.TypicalPets.getTypicalPetTrackerWithSlots;
import static clzzz.helper.testutil.slot.TypicalSlots.GARFIELD_SLOT;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import clzzz.helper.commons.exceptions.IllegalValueException;
import clzzz.helper.logic.parser.slot.SlotParserUtil;
import clzzz.helper.model.PetTracker;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.slot.DateTime;
import clzzz.helper.model.slot.SlotDuration;

public class JsonAdaptedSlotTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DATE_TIME = "01 Mar 2010";
    private static final String INVALID_DURATION = "0";

    private static final String VALID_NAME = GARFIELD_SLOT.getPet().getName().fullName;
    private static final String VALID_DATE_TIME = GARFIELD_SLOT.getDateTime().toString();
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
        String expectedMessage = DateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> slot.toModelType(typicalPetTracker));
    }

    @Test
    public void toModelType_nullDateTime_throwsIllegalValueException() {
        JsonAdaptedSlot slot = new JsonAdaptedSlot(VALID_NAME, null, VALID_DURATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> slot.toModelType(typicalPetTracker));
    }

    @Test
    public void toModelType_invalidDuration_throwsIllegalValueException() {
        JsonAdaptedSlot slot = new JsonAdaptedSlot(VALID_NAME, VALID_DATE_TIME, INVALID_DURATION);
        String expectedMessage = SlotDuration.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> slot.toModelType(typicalPetTracker));
    }

    @Test
    public void toModelType_nullDuration_throwsIllegalValueException() {
        JsonAdaptedSlot slot = new JsonAdaptedSlot(VALID_NAME, VALID_DATE_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, SlotDuration.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> slot.toModelType(typicalPetTracker));
    }

}
