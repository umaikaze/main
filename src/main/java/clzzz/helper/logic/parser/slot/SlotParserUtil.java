package clzzz.helper.logic.parser.slot;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import clzzz.helper.commons.core.Messages;
import clzzz.helper.commons.core.index.Index;
import clzzz.helper.commons.util.DateTimeUtil;
import clzzz.helper.commons.util.StringUtil;
import clzzz.helper.logic.parser.exceptions.ParseException;
import clzzz.helper.model.Model;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.pet.exceptions.PetNotFoundException;
import clzzz.helper.model.slot.Slot;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class SlotParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DATETIME =
            "Date and time must follow format d/M/yyyy HHmm.";
    public static final String MESSAGE_INVALID_DATE = "Date must follow format d/M/yyyy.";
    public static final String MESSAGE_INVALID_DURATION = "Duration is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_PETNAME = "Pet name is invalid.";
    public static final String MESSAGE_PET_DOES_NOT_EXIST = "Pet name does not match any pet in record.";

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
    public static Pet parsePet(String nameStr, Model model) throws ParseException {
        requireNonNull(nameStr);
        String trimmedPetName = nameStr.trim();
        if (!Name.isValidName(trimmedPetName)) {
            throw new ParseException(MESSAGE_INVALID_PETNAME);
        }
        Name petName = new Name(trimmedPetName);
        try {
            return model.getPet(petName);
        } catch (PetNotFoundException e) {
            throw new ParseException(MESSAGE_PET_DOES_NOT_EXIST);
        }
    }

    /**
     * Parses a {@code String dateTime} into a {@code DateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static LocalDateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        if (!Slot.isValidDateTimeFormat(trimmedDateTime)) {
            throw new ParseException(MESSAGE_INVALID_DATETIME);
        }
        if (!Slot.isValidDate(trimmedDateTime) && !Slot.isValidTime(trimmedDateTime)) {
            throw new ParseException(Messages.MESSAGE_INVALID_DATE_TIME);
        }
        if (!Slot.isValidDate(trimmedDateTime)) {
            throw new ParseException(Messages.MESSAGE_INVALID_DATE);
        }
        if (!Slot.isValidTime(trimmedDateTime)) {
            throw new ParseException(Messages.MESSAGE_INVALID_TIME);
        }
        return LocalDateTime.parse(trimmedDateTime, DateTimeUtil.DATETIME_FORMAT);
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Slot.isValidDateFormat(trimmedDate)) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
        if (!Slot.isValidDate(trimmedDate)) {
            throw new ParseException(Messages.MESSAGE_INVALID_DATE);
        }
        return LocalDate.parse(trimmedDate, DateTimeUtil.DATETIME_FORMAT);
    }

    /**
     * Parse a {@code String dates} into a {@code List<LocalDate>}
     * @throws ParseException if the dates is not in valid format.
     */
    public static List<LocalDate> parseDates(String dates) throws ParseException {
        requireNonNull(dates);
        String[] trimmedDates = dates.split("\\s+");
        ArrayList<LocalDate> parsedDates = new ArrayList<>();
        for (String date : trimmedDates) {
            parsedDates.add(parseDate(date));
        }
        return parsedDates;
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
        if (newDuration.isNegative() || newDuration.isZero()) {
            throw new ParseException(MESSAGE_INVALID_DURATION);
        }
        return newDuration;
    }
}
