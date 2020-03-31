package seedu.address.model.util;

import static seedu.address.commons.util.DateTimeUtil.DATETIME_FORMAT;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
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
import seedu.address.model.slot.Slot;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods for populating {@code Pet Tracker} with sample data.
 */
public class SampleDataUtil {
    public static Pet[] getSamplePets() {
        return new Pet[]{
                new Pet(new Name("Alex"), Gender.MALE, new DateOfBirth("1/12/2005"), new Species("cat"),
                        getFoodSet("cat food:10"), getTagSet("dumb")),
                new Pet(new Name("Bob"), Gender.FEMALE, new DateOfBirth("10/12/2001"), new Species("cat"),
                        getFoodSet("cat food:10"), getTagSet("tall")),
                new Pet(new Name("Cindy"), Gender.MALE, new DateOfBirth("2/10/2019"), new Species("cat"),
                        getFoodSet("cat food:10"), getTagSet("small")),
                new Pet(new Name("David"), Gender.FEMALE, new DateOfBirth("4/12/2017"), new Species("dog"),
                        getFoodSet("dog food:10"), getTagSet("angry")),
                new Pet(new Name("Elsa"), Gender.FEMALE, new DateOfBirth("6/6/2019"), new Species("dog"),
                        getFoodSet("dog food:10"), getTagSet("lazy")),
                new Pet(new Name("Foo"), Gender.MALE, new DateOfBirth("1/1/2011"), new Species("dog"),
                        getFoodSet("dog food:10"), getTagSet("new"))};
    }

    public static Slot[] getSampleSlots(PetTracker samplePt) {
        return new Slot[]{
                new Slot(samplePt.getPet(new Name("Alex")), LocalDateTime.parse("4/1/2030 1200", DATETIME_FORMAT), Duration.ofMinutes(Long.parseLong("75"))),
                new Slot(samplePt.getPet(new Name("David")), LocalDateTime.parse("7/7/2030 1200", DATETIME_FORMAT), Duration.ofMinutes(Long.parseLong("88"))),
                new Slot(samplePt.getPet(new Name("Elsa")), LocalDateTime.parse("4/2/2030 1200", DATETIME_FORMAT), Duration.ofMinutes(Long.parseLong("100")))};
    }


    public static ReadOnlyPetTracker getSamplePetTrackerWithSlots() {
        PetTracker samplePt = new PetTracker();
        for (Pet samplePet : getSamplePets()) {
            samplePt.addPet(samplePet);
        }
        for (Slot sampleSlot : getSampleSlots(samplePt)) {
            samplePt.addSlot(sampleSlot);
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
        Set<Food> set = new HashSet<>();
        for (String t : strings) {
            String[] temp = t.split(":");
            Food food = new Food(temp[0], Integer.parseInt(temp[1]));
            set.add(food);
        }
        return set;
    }

}
