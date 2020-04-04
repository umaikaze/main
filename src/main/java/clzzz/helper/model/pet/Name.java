package clzzz.helper.model.pet;

import static clzzz.helper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Pet's name in the pet store helper.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name implements Comparable<Name> {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = formatName(name);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Transfer name in to the format of "Xxx Xxx ...".
     * @param name The name passed in by user
     * @return The formatted name
     */
    public String formatName(String name) {
        String[] nameSubStrings = name.split(" ");
        String formattedName = "";
        for (String n : nameSubStrings) {
            n = n.substring(0, 1).toUpperCase() + n.substring(1).toLowerCase();
            formattedName += (n + " ");
        }
        return formattedName.trim();
    }

    @Override
    public int compareTo(Name other) {
        return fullName.compareTo(other.fullName);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
