package seedu.address.model.slot;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import seedu.address.model.pet.Pet;

/**
 * Represents a Slot in the pet shop helper schedule system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Slot implements Comparable<Slot> {

    private final Pet pet;
    private final LocalDateTime dateTime;
    private final Duration duration;

    /**
     * Every field must be present and not null.
     */
    public Slot(Pet pet, LocalDateTime dateTime, Duration duration) {
        requireAllNonNull(pet, dateTime, duration);
        this.pet = pet;
        this.dateTime = dateTime;
        this.duration = duration;
    }

    public Pet getPet() {
        return pet;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public Duration getDuration() {
        return duration;
    }

    /**
     * Returns the ending datetime of the slot, based on its starting datetime and duration.
     */

    public LocalDateTime getEndDateTime() {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    /**
     * Returns true if both slots fall on the same date, regardless of time of day.
     */
    public boolean isSameDate(Slot othersSlot) {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    /**
     * Returns true if this slot starts and ends on the same date.
     */
    public boolean isWithinOneDay() {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    /**
     * Returns true if this slot is in conflict with one or more other slots.
     */
    public boolean hasConflict() {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public int compareTo(Slot other) {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    /**
     * Returns true if all fields of both slots are the same.
     */
    @Override
    public boolean equals(Object other) {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public int hashCode() {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public String toString() {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}
