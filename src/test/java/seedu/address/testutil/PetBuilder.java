package seedu.address.testutil;

import seedu.address.model.pet.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * A utility class to help with building Person objects.
 */
public class PetBuilder {

    public static final String DEFAULT_NAME = "Kiruya Momochi";
    public static final Gender DEFAULT_GENDER = Gender.FEMALE;
    public static final String DEFAULT_DOB = "2-9-1998";
    public static final String DEFAULT_SPECIES = "Cat";

    private Name name;
    private Gender gender;
    private DateOfBirth dob;
    private Species species;
    private Set<Food> foodSet;
    private Set<Tag> tags;

    public PetBuilder() {
        name = new Name(DEFAULT_NAME);
        gender = DEFAULT_GENDER;
        dob = new DateOfBirth(DEFAULT_DOB);
        species = new Species(DEFAULT_SPECIES);
        foodSet = new HashSet<>();
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code petToCopy}.
     */
    public PetBuilder(Pet petToCopy) {
        name = petToCopy.getName();
        gender = petToCopy.getGender();
        dob = petToCopy.getDateOfBirth();
        species = petToCopy.getSpecies();
        foodSet = new HashSet<>(petToCopy.getFoodList());
        tags = new HashSet<>(petToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PetBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PetBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PetBuilder withSpecies(String address) {
        this.species = new Species(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PetBuilder withGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PetBuilder withDateOfBirth(String dob) {
        this.dob = new DateOfBirth(dob);
        return this;
    }

    public Pet build() {
        return new Pet(name, gender, dob, species, foodSet, tags);
    }

}
