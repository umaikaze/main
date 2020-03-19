package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.PetTracker;
import seedu.address.model.ReadOnlyPetTracker;
import seedu.address.model.pet.DateOfBirth;
import seedu.address.model.pet.Food;
import seedu.address.model.pet.Gender;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.Species;
import seedu.address.model.tag.Tag;



/**
 * Contains utility methods for populating {@code Pet Tracker} with sample data.
 */
public class SampleDataUtil {
    public static Pet[] getSamplePets() {
        return new Pet[] {
            new Pet(new Name("Alex"), Gender.MALE, new DateOfBirth("1/12/2005"), new Species("cat"),
                    getFoodSet("cat food"), getTagSet("dumb")),
            new Pet(new Name("Bob"), Gender.FEMALE, new DateOfBirth("10/12/2001"), new Species("cat"),
                    getFoodSet("cat food"), getTagSet("tall")),
            new Pet(new Name("Cindy"), Gender.MALE, new DateOfBirth("2/10/2019"), new Species("cat"),
                    getFoodSet("cat food"), getTagSet("small")),
            new Pet(new Name("David"), Gender.FEMALE, new DateOfBirth("4/12/2017"), new Species("dog"),
                    getFoodSet("dog food"), getTagSet("angry")),
            new Pet(new Name("Elsa"), Gender.FEMALE, new DateOfBirth("6/6/2019"), new Species("dog"),
                    getFoodSet("dog food"), getTagSet("lazy")),
            new Pet(new Name("Foo"), Gender.MALE, new DateOfBirth("1/1/2011"), new Species("dog"),
                    getFoodSet("dog food"), getTagSet("new"))};
    }


    public static ReadOnlyPetTracker getSamplePetTracker() {
        PetTracker samplePt = new PetTracker();
        for (Pet samplePet : getSamplePets()) {
            samplePt.addPet(samplePet);
        }
        return samplePt;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a food set containing the list of strings given.
     */
    public static Set<Food> getFoodSet(String... strings) {
        return Arrays.stream(strings)
                .map(t -> new Food(t, 10))
                .collect(Collectors.toSet());
    }

}
