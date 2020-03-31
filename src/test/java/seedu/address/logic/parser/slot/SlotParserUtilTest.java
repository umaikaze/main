package seedu.address.logic.parser.slot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.util.DateTimeUtil.DATETIME_FORMAT;
import static seedu.address.logic.parser.slot.SlotParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.pet.TypicalPets.COCO;
import static seedu.address.testutil.pet.TypicalPets.getTypicalPetTracker;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class SlotParserUtilTest {

    private static final String NON_EXIST_PET = "Kyaru";
    private static final String INVALID_DATETIME = "1-3-2020 12:00";
    private static final String INVALID_DURATION = "14.5";
    private static final String INVALID_INDEX = "1a";
    private static final String INVALID_PETNAME = "Bob*";

    private static final String EXIST_PET = "Coco";
    private static final String VALID_DATE = "1/3/2020 1200";
    private static final String VALID_DURATION = "20";

    private static final String WHITESPACE = " \t\r\n";

    private static final LocalDateTime DATETIME = LocalDateTime.parse(VALID_DATE, DATETIME_FORMAT);
    private static final Duration DURATION = Duration.ofMinutes(Long.parseLong(VALID_DURATION));
    private Model model = new ModelManager(getTypicalPetTracker(), new UserPrefs());

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
    public void parseDateTime_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> SlotParserUtil.parseDateTime(INVALID_DATETIME));
    }

    @Test
    public void parseDateTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SlotParserUtil.parseDateTime(null));
    }

    @Test
    public void parseDateTime_validValueWithoutWhitespace_returnsDate() throws Exception {
        assertEquals(DATETIME, SlotParserUtil.parseDateTime(VALID_DATE));
    }

    @Test
    public void parseDateTime_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        assertEquals(DATETIME, SlotParserUtil.parseDateTime(WHITESPACE + VALID_DATE + WHITESPACE));
    }

    @Test
    public void parseDuration_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> SlotParserUtil.parseDuration(INVALID_DURATION));
    }

    @Test
    public void parseDuration_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SlotParserUtil.parseDuration(null));
    }

    @Test
    public void parseDuration_validValueWithoutWhitespace_returnsDate() throws Exception {
        assertEquals(DURATION, SlotParserUtil.parseDuration(VALID_DURATION));
    }

    @Test
    public void parseDuration_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        assertEquals(DURATION, SlotParserUtil.parseDuration(WHITESPACE + VALID_DURATION + WHITESPACE));
    }
}
