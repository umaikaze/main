package clzzz.helper.model.slot;

import static clzzz.helper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAmount;

import clzzz.helper.commons.util.DateTimeUtil;

/**
 * Represents the date-time of a slot. Guarantees: immutable; is valid as
 * declared in {@link #isValidDateTime(String)}
 */
public class DateTime implements Comparable<DateTime> {

    public static final String MESSAGE_CONSTRAINTS =
            String.format("Date and time must be valid, and follow the format of %s.",
            DateTimeUtil.DATETIME_PATTERN.replaceAll("u", "y"));

    private final LocalDateTime value;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param dateTime A valid date-time.
     */
    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS);
        this.value = DateTimeUtil.parseLocalDateTime(dateTime);
    }

    private DateTime(LocalDateTime dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS);
        this.value = dateTime;
    }

    /**
     * Returns the value as a {@code LocalDateTime}.
     */
    public LocalDateTime toLocalDateTime() {
        return value;
    }

    /**
     * Returns the date of this object  as a {@code LocalDate}.
     */
    public LocalDate toLocalDate() {
        return value.toLocalDate();
    }

    /**
     * Returns the time of this object as a {@code LocalTime}.
     */
    public LocalTime toLocalTime() {
        return value.toLocalTime();
    }

    /**
     * Returns if a given string is in the correct format for date-time,
     * and when parsed, the date-time comes after the Unix epoch.
     */
    public static boolean isValidDateTime(String test) {
        try {
            LocalDateTime mightBeValid = DateTimeUtil.parseLocalDateTime(test);
            return isValidDateTime(mightBeValid);
        } catch (DateTimeParseException p) {
            return false;
        }
    }

    private static boolean isValidDateTime(LocalDateTime test) {
        return test.isAfter(LocalDate.EPOCH.atStartOfDay());
    }

    /**
     * Checks if this date-time is before {@code other}.
     */
    public boolean isBefore(DateTime other) {
        return value.isBefore(other.value);
    }

    /**
     * Checks if this date-time is after {@code other}.
     */
    public boolean isAfter(DateTime other) {
        return value.isAfter(other.value);
    }

    /**
     * Returns a copy of this date-time with the specified amount added.
     */
    public DateTime plus(TemporalAmount amountToAdd) {
        return new DateTime(value.plus(amountToAdd));
    }

    @Override
    public int compareTo(DateTime other) {
        return value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return DateTimeUtil.formatLocalDateTime(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && value.equals(((DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
