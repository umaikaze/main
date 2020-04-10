package clzzz.helper.model.pet;

import static clzzz.helper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import clzzz.helper.commons.util.DateTimeUtil;

/**
 * Represents a Pet's date of birth.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DateOfBirth {

    public static final String MESSAGE_CONSTRAINTS =
            String.format("Date of birth must be valid, and follow the format of %s.",
            DateTimeUtil.DATE_PATTERN.replaceAll("u", "y"));

    public final LocalDate value;

    /**
     * Constructs an {@code Email}.
     *
     * @param dateOfBirth A valid date of birth.
     */
    public DateOfBirth(String dateOfBirth) {
        requireNonNull(dateOfBirth);
        checkArgument(isValidDateOfBirth(dateOfBirth), MESSAGE_CONSTRAINTS);
        this.value = LocalDate.parse(dateOfBirth, DateTimeUtil.DATE_FORMAT);
    }

    /**
     * Returns if a given string is a valid format of date of birth.
     */
    public static boolean isValidDateOfBirth(String test) {
        try {
            DateTimeUtil.parseLocalDate(test); // parsed date is never used
            return true;
        } catch (DateTimeParseException p) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value.format(DateTimeUtil.DATE_FORMAT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateOfBirth // instanceof handles nulls
                && value.equals(((DateOfBirth) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
