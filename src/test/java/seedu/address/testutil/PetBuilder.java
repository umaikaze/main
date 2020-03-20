package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.pet.DateOfBirth;
import seedu.address.model.pet.Food;
import seedu.address.model.pet.Gender;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.Species;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Pet objects.
 */
public class PetBuilder {

    public static final String DEFAULT_NAME = "Kiruya Momochi";
    public static final Gender DEFAULT_GENDER = Gender.FEMALE;
    public static final String DEFAULT_DOB = "2/9/1998";
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
     * Initializes the PetBuilder with the data of {@code petToCopy}.
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
     * Sets the {@code Name} of the {@code Pet} that we are building.
     */
    public PetBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Pet} that we are building.
     */
    public PetBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Species} of the {@code Pet} that we are building.
     */
    public PetBuilder withSpecies(String species) {
        this.species = new Species(species);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Pet} that we are building.
     */
    public PetBuilder withGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    /**
     * Sets the {@code DateOfBirth} of the {@code Pet} that we are building.
     */
    public PetBuilder withDateOfBirth(String dob) {
        this.dob = new DateOfBirth(dob);
        return this;
    }

    /**
     * Parses the {@code foodList} into a {@code Set<Food>} and set it to the {@code Pet} that we are building.
     */
    public PetBuilder withFoodList(String ... foodList) {
        this.foodSet = SampleDataUtil.getFoodSet(foodList);
        return this;
    }

    public Pet build() {
        return new Pet(name, gender, dob, species, foodSet, tags);
    }

}
