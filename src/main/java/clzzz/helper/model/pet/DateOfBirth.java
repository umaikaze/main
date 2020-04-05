package clzzz.helper.model.pet;

import static clzzz.helper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import clzzz.helper.commons.core.Messages;
import clzzz.helper.commons.util.DateTimeUtil;

/**
 * Represents a Pet's date of birth.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfBirth(String)}
 */
public class DateOfBirth {

    public static final String MESSAGE_CONSTRAINTS =
            "Date of birth must follow the format of " + DateTimeUtil.DATE_PATTERN + ".";

    public final LocalDate value;

    /**
     * Constructs an {@code Email}.
     *
     * @param dateOfBirth A valid date of birth.
     */
    public DateOfBirth(String dateOfBirth) {
        requireNonNull(dateOfBirth);
        checkArgument(isValidDateFormat(dateOfBirth), MESSAGE_CONSTRAINTS);
        checkArgument(isValidDate(dateOfBirth), Messages.MESSAGE_INVALID_DATE);
        this.value = LocalDate.parse(dateOfBirth, DateTimeUtil.DATE_FORMAT);
    }

    /**
     * Returns if a given string is a valid format of date of birth.
     */
    public static boolean isValidDateFormat(String test) {
        try {
            LocalDate mightBeValid = LocalDate.parse(test, DateTimeUtil.DATE_FORMAT);
            return true;
        } catch (DateTimeParseException p) {
            return false;
        }
    }

    /**
     * This method is used after checking the date is in correct format.
     * It return true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate mightBeValid = LocalDate.parse(test, DateTimeUtil.STRICT_DATE_FORMAT);
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
