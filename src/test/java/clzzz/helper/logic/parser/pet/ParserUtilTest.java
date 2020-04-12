package clzzz.helper.logic.parser.pet;

import static clzzz.helper.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static clzzz.helper.logic.parser.ParserUtil.parseDateOfBirth;
import static clzzz.helper.logic.parser.ParserUtil.parseGender;
import static clzzz.helper.logic.parser.ParserUtil.parseIndex;
import static clzzz.helper.logic.parser.ParserUtil.parseName;
import static clzzz.helper.logic.parser.ParserUtil.parseSpecies;
import static clzzz.helper.logic.parser.ParserUtil.parseTag;
import static clzzz.helper.logic.parser.ParserUtil.parseTags;
import static clzzz.helper.testutil.Assert.assertThrows;
import static clzzz.helper.testutil.TypicalIndexes.INDEX_FIRST_PET;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import clzzz.helper.logic.parser.exceptions.ParseException;
import clzzz.helper.model.pet.DateOfBirth;
import clzzz.helper.model.pet.Gender;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Species;
import clzzz.helper.model.pet.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_GENDER = "mali";
    private static final String INVALID_DATEOFBIRTH = "";
    private static final String INVALID_SPECIES = "!fruitcake";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel";
    private static final String VALID_GENDER = "FEMALE";
    private static final String VALID_DATEOFBIRTH = "2/12/2019";
    private static final String VALID_SPECIES = "Raccoon";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbor";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()->
            parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PET, parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PET, parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, parseName(nameWithWhitespace));
    }

    @Test
    public void parseGender_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parseGender((String) null));
    }

    @Test
    public void parseGender_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> parseGender(INVALID_GENDER));
    }

    @Test
    public void parseGender_validValueWithoutWhitespace_returnsGender() throws Exception {
        Gender expectedGender = Gender.valueOf(VALID_GENDER);
        assertEquals(expectedGender, parseGender(VALID_GENDER));
    }

    @Test
    public void parseGender_validValueWithWhitespace_returnsTrimmedGender() throws Exception {
        String genderWithWhitespace = WHITESPACE + VALID_GENDER + WHITESPACE;
        Gender expectedGender = Gender.valueOf(VALID_GENDER);
        assertEquals(expectedGender, parseGender(genderWithWhitespace));
    }

    @Test
    public void parseDateOfBirth_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parseDateOfBirth((String) null));
    }

    @Test
    public void parseDateOfBirth_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> parseDateOfBirth(INVALID_DATEOFBIRTH));
    }

    @Test
    public void parseDateOfBirth_validValueWithoutWhitespace_returnsDateOfBirth() throws Exception {
        DateOfBirth expectedDateOfBirth = new DateOfBirth(VALID_DATEOFBIRTH);
        assertEquals(expectedDateOfBirth, parseDateOfBirth(VALID_DATEOFBIRTH));
    }

    @Test
    public void parseDateOfBirth_validValueWithWhitespace_returnsTrimmedDateOfBirth() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_DATEOFBIRTH + WHITESPACE;
        DateOfBirth expectedDateOfBirth = new DateOfBirth(VALID_DATEOFBIRTH);
        assertEquals(expectedDateOfBirth, parseDateOfBirth(addressWithWhitespace));
    }

    @Test
    public void parseSpecies_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parseSpecies((String) null));
    }

    @Test
    public void parseSpecies_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> parseSpecies(INVALID_SPECIES));
    }

    @Test
    public void parseSpecies_validValueWithoutWhitespace_returnsSpecies() throws Exception {
        Species expectedSpecies = new Species(VALID_SPECIES);
        assertEquals(expectedSpecies, parseSpecies(VALID_SPECIES));
    }

    @Test
    public void parseSpecies_validValueWithWhitespace_returnsTrimmedSpecies() throws Exception {
        String speciesWithWhitespace = WHITESPACE + VALID_SPECIES + WHITESPACE;
        Species expectedSpecies = new Species(VALID_SPECIES);
        assertEquals(expectedSpecies, parseSpecies(speciesWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
