package clzzz.helper.testutil.pet;

import java.util.HashSet;
import java.util.Set;

import clzzz.helper.model.pet.DateOfBirth;
import clzzz.helper.model.pet.Food;
import clzzz.helper.model.pet.Gender;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.pet.Species;
import clzzz.helper.model.pet.Tag;
import clzzz.helper.model.util.SampleDataUtil;

/**
 * A utility class to help with building Pet objects.
 */
public class PetBuilder {

    public static final String DEFAULT_NAME = "Kiruya Momochi";
    public static final String DEFAULT_GENDER = "female";
    public static final String DEFAULT_DOB = "2/9/1998";
    public static final String DEFAULT_SPECIES = "Cat";
    private static final String DEFAULT_FOOD_NAME = "catfood";
    private static final Integer DEFAULT_FOOD_AMOUNT = 30;

    private Name name;
    private Gender gender;
    private DateOfBirth dob;
    private Species species;
    private Set<Food> foodSet;
    private Set<Tag> tags;

    public PetBuilder() {
        name = new Name(DEFAULT_NAME);
        gender = Gender.valueOf(DEFAULT_GENDER.toUpperCase());
        dob = new DateOfBirth(DEFAULT_DOB);
        species = new Species(DEFAULT_SPECIES);
        foodSet = new HashSet<>();
        foodSet.add(new Food(DEFAULT_FOOD_NAME, DEFAULT_FOOD_AMOUNT));
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
    public PetBuilder withGender(String gender) {
        this.gender = Gender.valueOf(gender.toUpperCase());
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
