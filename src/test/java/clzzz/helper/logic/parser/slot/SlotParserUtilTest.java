package clzzz.helper.logic.parser.slot;

import static clzzz.helper.logic.parser.slot.SlotParserUtil.MESSAGE_INVALID_INDEX;
import static clzzz.helper.testutil.Assert.assertThrows;
import static clzzz.helper.testutil.pet.TypicalPets.COCO;
import static clzzz.helper.testutil.pet.TypicalPets.getTypicalPetTrackerWithSlots;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import clzzz.helper.commons.util.DateTimeUtil;
import clzzz.helper.logic.parser.exceptions.ParseException;
import clzzz.helper.model.Model;
import clzzz.helper.model.ModelManager;
import clzzz.helper.model.UserPrefs;
import clzzz.helper.model.slot.DateTime;
import clzzz.helper.model.slot.SlotDuration;

class SlotParserUtilTest {

    private static final String NON_EXIST_PET = "Kyaru";
    private static final String INVALID_DATETIME_FORMAT = "1-3-2020 12:00";
    private static final String INVALID_DATETIME_1 = "30/2/2019 1200";
    private static final String INVALID_DATETIME_2 = "12/2/2019 2400";
    private static final String INVALID_DATETIME_3 = "30/2/2019 2400";
    private static final String INVALID_DATE_FORMAT = "1-3-2020";
    private static final String INVALID_DATE = "29/2/2019";
    private static final String INVALID_DURATION = "14.5";
    private static final String INVALID_INDEX = "1a";
    private static final String INVALID_PETNAME = "Bob*";

    private static final String EXIST_PET = "Coco";
    private static final String VALID_DATE = "1/3/2020";
    private static final String VALID_DATETIME = "1/3/2020 1200";
    private static final String VALID_DURATION = "20";

    private static final String WHITESPACE = " \t\r\n";

    private static final DateTime DATETIME = new DateTime(VALID_DATETIME);
    private static final LocalDate DATE = DateTimeUtil.parseLocalDate(VALID_DATE);
    private static final SlotDuration DURATION = new SlotDuration(VALID_DURATION);
    private Model model = new ModelManager(getTypicalPetTrackerWithSlots(), new UserPrefs());

    @Test
    void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> SlotParserUtil.parseIndex(INVALID_INDEX));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> SlotParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parsePet_invalidInput_throwsParseException() {
        // Bad pet name
        assertThrows(ParseException.class, () -> SlotParserUtil.parsePet(INVALID_PETNAME, model));

        // Pet doesn't exist
        assertThrows(ParseException.class, () -> SlotParserUtil.parsePet(NON_EXIST_PET, model));
    }

    @Test
    public void parsePet_null_throwsNullPointerException() {
        // Both null
        assertThrows(NullPointerException.class, () -> SlotParserUtil.parsePet(null, null));

        // model null
        assertThrows(NullPointerException.class, () -> SlotParserUtil.parsePet(EXIST_PET, null));

        // nameStr null
        assertThrows(NullPointerException.class, () -> SlotParserUtil.parsePet(null, model));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(COCO, SlotParserUtil.parsePet(EXIST_PET, model));

        // Leading and trailing whitespaces
        assertEquals(COCO, SlotParserUtil.parsePet(WHITESPACE + EXIST_PET + WHITESPACE, model));
    }

    @Test
    public void parseDateTime_invalidInputFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> SlotParserUtil.parseDateTime(INVALID_DATETIME_FORMAT));
    }

    @Test
    public void parseDateTime_invalidInputDate_throwsParseException() {
        assertThrows(ParseException.class, () -> SlotParserUtil.parseDateTime(INVALID_DATETIME_1));
    }

    @Test
    public void parseDateTime_invalidInputTime_throwsParseException() {
        assertThrows(ParseException.class, () -> SlotParserUtil.parseDateTime(INVALID_DATETIME_2));
    }

    @Test
    public void parseDateTime_invalidInputDateAndTime_throwsParseException() {
        assertThrows(ParseException.class, () -> SlotParserUtil.parseDateTime(INVALID_DATETIME_3));
    }

    @Test
    public void parseDateTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SlotParserUtil.parseDateTime(null));
    }

    @Test
    public void parseDateTime_validValueWithoutWhitespace_returnsDate() throws Exception {
        assertEquals(DATETIME, SlotParserUtil.parseDateTime(VALID_DATETIME));
    }

    @Test
    public void parseDateTime_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        assertEquals(DATETIME, SlotParserUtil.parseDateTime(WHITESPACE + VALID_DATETIME + WHITESPACE));
    }

    @Test
    public void parseDate_invalidInputFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> SlotParserUtil.parseDate(INVALID_DATE_FORMAT));
    }

    @Test
    public void parseDate_invalidInputDate_throwsParseException() {
        assertThrows(ParseException.class, () -> SlotParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SlotParserUtil.parseDate(null));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        assertEquals(DATE, SlotParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        assertEquals(DATE, SlotParserUtil.parseDate(WHITESPACE + VALID_DATE + WHITESPACE));
    }

    @Test
    public void parseDuration_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> SlotParserUtil.parseSlotDuration(INVALID_DURATION));
    }

    @Test
    public void parseDuration_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SlotParserUtil.parseSlotDuration(null));
    }

    @Test
    public void parseDuration_validValueWithoutWhitespace_returnsDate() throws Exception {
        assertEquals(DURATION, SlotParserUtil.parseSlotDuration(VALID_DURATION));
    }

    @Test
    public void parseDuration_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        assertEquals(DURATION, SlotParserUtil.parseSlotDuration(WHITESPACE + VALID_DURATION + WHITESPACE));
    }
}
