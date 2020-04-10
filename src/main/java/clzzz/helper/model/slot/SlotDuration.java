package clzzz.helper.model.slot;

import static clzzz.helper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.List;

/**
 * Represents the duration of a slot. Guarantees: immutable; is valid as
 * declared in {@link #isValidDuration(String)}
 */
public class SlotDuration implements Comparable<SlotDuration>, TemporalAmount {

    public static final String MESSAGE_CONSTRAINTS =
            "Duration must be positive, non-zero and shorter than a day.";

    private final Duration value;

    public SlotDuration(String duration) {
        requireNonNull(duration);
        checkArgument(isValidDuration(duration), MESSAGE_CONSTRAINTS);
        this.value = Duration.ofMinutes(Long.parseLong(duration));
    }

    private SlotDuration(Duration duration) {
        requireNonNull(duration);
        checkArgument(isValidDuration(duration), MESSAGE_CONSTRAINTS);
        this.value = duration;
    }

    /**
     * Returns if a given string is a valid representation of number of minutes,
     * and when parsed, the duration is positive, non-zero and shorter than a day.
     */
    public static boolean isValidDuration(String test) {
        try {
            long minutes = Long.parseLong(test);
            Duration mightBeValid = Duration.ofMinutes(minutes);
            return isValidDuration(mightBeValid);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidDuration(Duration test) {
        return !test.isNegative() // positive
                && !test.isZero() // non-zero
                && test.compareTo(Duration.ofDays(1)) < 0; // shorter than a day
    }

    public static SlotDuration between(Temporal startInclusive, Temporal endExclusive) {
        return new SlotDuration(Duration.between(startInclusive, endExclusive));
    }

    public long toMinutes() {
        return value.toMinutes();
    }

    @Override
    public long get(TemporalUnit unit) {
        return value.get(unit);
    }

    @Override
    public List<TemporalUnit> getUnits() {
        return value.getUnits();
    }

    @Override
    public Temporal addTo(Temporal temporal) {
        return value.addTo(temporal);
    }

    @Override
    public Temporal subtractFrom(Temporal temporal) {
        return value.subtractFrom(temporal);
    }

    @Override
    public int compareTo(SlotDuration other) {
        return value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value.toMinutes());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SlotDuration // instanceof handles nulls
                && value.equals(((SlotDuration) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
