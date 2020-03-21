package seedu.address.model.slot;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.model.pet.Pet;
import seedu.address.ui.DisplayItem;
import seedu.address.ui.DisplaySystemType;

/**
 * Represents a Slot in the pet shop helper schedule system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Slot implements Comparable<Slot>, DisplayItem {

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

    public Duration getDuration() {
        return duration;
    }

    /**
     * Returns the date on which the slot falls on, regardless of time of day.
     */
    public LocalDate getDate() {
        return getDateTime().toLocalDate();
    }

    /**
     * Returns the ending datetime of the slot, based on its starting datetime and duration.
     */

    public LocalDateTime getEndDateTime() {
        return getDateTime().plus(duration);
    }

    /**
     * Returns true if both slots fall on the same date, regardless of time of day.
     */
    public boolean isSameDate(Slot othersSlot) {
        return getDate().equals(othersSlot.getDate());
    }

    /**
     * Returns true if this slot starts and ends on the same date.
     */
    public boolean isWithinOneDay() {
        LocalDate endDate = getEndDateTime().toLocalDate();
        return getDate().equals(endDate);
    }

    /**
     * Returns true if this slot is in conflict with the given other slot.
     */
    public boolean isInConflictWith(Slot otherSlot) {
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

    /**
     * Compares this slot to another slot.
     *
     * The comparison is first done by their datetimes, then by their durations,
     * then by the names of the pets occupying the slots.
     */
    @Override
    public int compareTo(Slot other) {
        if (dateTime.compareTo(other.dateTime) != 0) {
            return dateTime.compareTo(other.dateTime);
        }
        if (duration.compareTo(other.duration) != 0) {
            return duration.compareTo(other.duration);
        }
        return pet.getName().compareTo(other.pet.getName());
    }

    @Override
    public DisplaySystemType getDisplaySystemType() {
        return DisplaySystemType.SCHEDULE;
    }

    /**
     * Returns true if all fields of both slots are the same.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Slot)) {
            return false;
        }

        Slot otherSlot = (Slot) other;
        return otherSlot.getPet().equals(getPet())
                && otherSlot.getDateTime().equals(getDateTime())
                && otherSlot.getDuration().equals(getDuration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(pet, dateTime, duration);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)",
                getDateTime(), getEndDateTime(), getPet().getName());
    }
}
