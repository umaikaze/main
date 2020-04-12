package clzzz.helper.model.pet;

import static clzzz.helper.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import clzzz.helper.ui.DisplaySystemType;
import clzzz.helper.ui.list.DisplayItem;

/**
 * Represents a Pet in the pet store helper.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Pet implements DisplayItem {

    // Identity fields
    private final Name name;
    private final Gender gender;
    private final DateOfBirth dateOfBirth;
    private final Species species;

    // Data fields
    private final Set<Food> foodList = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Pet(Name name, Gender gender, DateOfBirth dateOfBirth, Species species, Set<Food> foodList, Set<Tag> tags) {
        requireAllNonNull(name, gender, dateOfBirth, species, foodList, tags);
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.species = species;

        initializeFoodList(foodList);
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public Species getSpecies() {
        return species;
    }

    public Set<Food> getFoodList() {
        return Collections.unmodifiableSet(foodList);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both pets have the same name.
     * This defines a weaker notion of equality between two pets.
     */
    public boolean isSamePet(Pet otherPet) {
        if (otherPet == this) {
            return true;
        }

        return otherPet != null && otherPet.getName().equals(getName());
    }

    @Override
    public DisplaySystemType getDisplaySystemType() {
        return DisplaySystemType.PETS;
    }

    /**
     * Returns true if both pets have the same identity and data fields.
     * This defines a stronger notion of equality between two pets.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Pet)) {
            return false;
        }

        Pet otherPet = (Pet) other;
        return otherPet.getName().equals(getName())
                && otherPet.getGender().equals(getGender())
                && otherPet.getDateOfBirth().equals(getDateOfBirth())
                && otherPet.getSpecies().equals(getSpecies())
                && otherPet.getFoodList().equals(getFoodList())
                && otherPet.getTags().equals(getTags());
    }

    /**
     * Adding an input set of food into the foodList attribute of this pet. Note that items with repeating types are
     * combined to become one item.
     * @param input The input set to food to be added.
     */
    public void initializeFoodList(Set<Food> input) {
        for (Food toBeAdded:input) {
            accumulateSameType(toBeAdded);
        }
    }

    /**
     * Add a food into the food list of the pet without creating items of duplicate names.
     */
    public void accumulateSameType(Food toBeAdded) {
        for (Food food:foodList) {
            if (food.isSameType(toBeAdded)) {
                toBeAdded = new Food(toBeAdded.foodName, toBeAdded.foodAmount + food.foodAmount);
                foodList.remove(food);
                break;
            }
        }
        foodList.add(toBeAdded);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, gender, dateOfBirth, species, foodList, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Gender: ")
                .append(getGender())
                .append(" Date Of Birth: ")
                .append(getDateOfBirth())
                .append(" Species: ")
                .append(getSpecies())
                .append(" Foods: ");
        getFoodList().forEach(builder::append);
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
