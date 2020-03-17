package seedu.address.model.pet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import seedu.address.commons.util.DateTimeUtil;

/**
 * Represents a Pet's date of birth.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfBirth(String)}
 */
public class DateOfBirth {

    public static final String MESSAGE_CONSTRAINTS =
            "Date of Birth must follow the format of " + DateTimeUtil.DATE_PATTERN + ".";

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
            LocalDate mightBeValid = LocalDate.parse(test, DateTimeUtil.DATE_FORMAT);
            if (mightBeValid.isBefore(LocalDate.EPOCH)) {
                return false;
            }
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
