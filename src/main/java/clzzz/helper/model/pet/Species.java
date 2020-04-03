package clzzz.helper.model.pet;

import static clzzz.helper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Pet's Species in the pet store helper.
 * Guarantees: immutable; is valid as declared in {@link #isValidSpecies(String)}
 */
public class Species {

    public static final String MESSAGE_CONSTRAINTS =
            "Species should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the species must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String species;

    /**
     * Constructs a {@code Species}.
     *
     * @param species A valid species name.
     */
    public Species(String species) {
        requireNonNull(species);
        checkArgument(isValidSpecies(species), MESSAGE_CONSTRAINTS);
        this.species = formatSpecies(species);
    }

    /**
     * Returns true if a given string is a valid species.
     */
    public static boolean isValidSpecies(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Transfer species string in to the format of "Xxx Xxx ...".
     * @param species The species passed in by user
     * @return The formatted species string
     */
    public String formatSpecies(String species) {
        String[] speciesSubStrings = species.split(" ");
        String formattedSpecies = "";
        for (String s : speciesSubStrings) {
            s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            formattedSpecies += (s + " ");
        }
        return formattedSpecies.trim();
    }

    @Override
    public String toString() {
        return species;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Species // instanceof handles nulls
                && species.equals(((Species) other).species)); // state check
    }

    @Override
    public int hashCode() {
        return species.hashCode();
    }

}
