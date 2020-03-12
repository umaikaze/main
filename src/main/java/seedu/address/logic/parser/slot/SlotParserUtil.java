package seedu.address.logic.parser.slot;

import static java.util.Objects.requireNonNull;

import java.time.Duration;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.pet.Pet;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class SlotParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DURATION = "Duration is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_PETNAME = "Pet name does not match any pet in record.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String petName} into a {@code Pet}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code petName} is invalid.
     */
    public static Pet parsePet(String petName) throws ParseException {
        requireNonNull(petName);
        String trimmedPetName = petName.trim();
        Pet pet = SomeHowFindPetWithNameWithoutViolatingSOLID(trimmedPetName);
        if (pet == null) {
            throw new ParseException(MESSAGE_INVALID_PETNAME);
        }
        return pet;
    }

    /**
     * Parses a {@code String dateTime} into a {@code DateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static DateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        if (!DateTime.isValidDateTime(trimmedDateTime)) {
            throw new ParseException(DateTime.MESSAGE_CONSTRAINTS);
        }
        return new DateTime(trimmedDateTime);
    }

    /**
     * Parses a {@code String duration} into an {@code Duration}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code duration} is invalid.
     */
    public static Duration parseDuration(String duration) throws ParseException {
        requireNonNull(duration);
        String trimmedDuration = duration.trim();
        Duration newDuration;
        try {
            newDuration = Duration.ofMinutes(Long.parseLong(trimmedDuration));
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_DURATION);
        }
        return newDuration;
    }
}
