package seedu.address.model.pet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * Represents a Pet's date of birth.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfBirth(String)}
 */
public class DateOfBirth {

    public static final String MESSAGE_CONSTRAINTS = "Date of Birth must follow the format of yyyy-mm-dd.";
    public static final String DATE_OF_BIRTH_FORMAT = "yyyy-mm-dd";


    public final LocalDate value;

    /**
     * Constructs an {@code Email}.
     *
     * @param dateOfBirth A valid date of birth.
     */
    public DateOfBirth(String dateOfBirth) {
        requireNonNull(dateOfBirth);
        checkArgument(isValidDateOfBirth(dateOfBirth), MESSAGE_CONSTRAINTS);
        this.value = LocalDate.parse(dateOfBirth);
    }

    /**
     * Returns if a given string is a valid format of date of birth.
     */
    public static boolean isValidDateOfBirth(String test) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_OF_BIRTH_FORMAT);
        formatter.setLenient(false);
        try {
            formatter.parse(test);
            return true;
        } catch (ParseException p) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value.toString();
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
