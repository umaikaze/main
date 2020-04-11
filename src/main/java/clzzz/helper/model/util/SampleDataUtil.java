package clzzz.helper.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import clzzz.helper.model.PetTracker;
import clzzz.helper.model.ReadOnlyPetTracker;
import clzzz.helper.model.pet.DateOfBirth;
import clzzz.helper.model.pet.Food;
import clzzz.helper.model.pet.Gender;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.pet.Species;
import clzzz.helper.model.pet.Tag;
import clzzz.helper.model.slot.DateTime;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.model.slot.SlotDuration;

/**
 * Contains utility methods for populating {@code Pet Tracker} with sample data.
 */
public class SampleDataUtil {
    public static Pet[] getSamplePets() {
        return new Pet[] {
            new Pet(new Name("Alex"), Gender.MALE, new DateOfBirth("1/12/2005"), new Species("cat"),
                        getFoodSet("cat food:10"), getTagSet("dumb")),
            new Pet(new Name("Bob"), Gender.FEMALE, new DateOfBirth("10/12/2001"), new Species("cat"),
                        getFoodSet("cat food:10"), getTagSet("tall")),
            new Pet(new Name("Cindy"), Gender.MALE, new DateOfBirth("2/10/2019"), new Species("cat"),
                        getFoodSet("cat food:10"), getTagSet("small")),
            new Pet(new Name("David"), Gender.FEMALE, new DateOfBirth("4/12/2017"), new Species("rabbit"),
                        getFoodSet("rabbit food:10"), getTagSet("angry")),
            new Pet(new Name("Elsa"), Gender.FEMALE, new DateOfBirth("6/6/2019"), new Species("dog"),
                        getFoodSet("dog food:15"), getTagSet("lazy")),
            new Pet(new Name("Foo"), Gender.MALE, new DateOfBirth("1/1/2011"), new Species("dog"),
                        getFoodSet("dog food:10"), getTagSet("new")),
            new Pet(new Name("Gru"), Gender.MALE, new DateOfBirth("10/1/1999"), new Species("rabbit"),
                        getFoodSet("rabbit food:15"), getTagSet("oldest")),
            new Pet(new Name("Helen"), Gender.FEMALE, new DateOfBirth("1/1/2011"), new Species("dog"),
                        getFoodSet("dog food:10"), getTagSet("new")),
            new Pet(new Name("Inn"), Gender.MALE, new DateOfBirth("1/1/2011"), new Species("dog"),
                        getFoodSet("dog food:10"), getTagSet("new")),
            new Pet(new Name("Judy"), Gender.FEMALE, new DateOfBirth("1/1/2011"), new Species("dog"),
                        getFoodSet("dog food:10"), getTagSet("new")),
            new Pet(new Name("Kelly"), Gender.FEMALE, new DateOfBirth("1/4/2016"), new Species("goose"),
                        getFoodSet("goose food:10"), getTagSet("new")),
            new Pet(new Name("Leo"), Gender.MALE, new DateOfBirth("12/12/2000"), new Species("rabbit"),
                        getFoodSet("rabbit food:10"), getTagSet("new")),
            new Pet(new Name("Molly"), Gender.FEMALE, new DateOfBirth("1/1/2011"), new Species("snake"),
                        getFoodSet("snake food:20"), getTagSet("new")),
            new Pet(new Name("Newbie"), Gender.MALE, new DateOfBirth("14/9/2018"), new Species("rabbit"),
                        getFoodSet("rabbit food:10"), getTagSet("new")),
            new Pet(new Name("Oligay"), Gender.FEMALE, new DateOfBirth("12/7/2019"), new Species("snake"),
                        getFoodSet("snake food:10"), getTagSet("new")),
            new Pet(new Name("Pele"), Gender.MALE, new DateOfBirth("1/12/2019"), new Species("snake"),
                        getFoodSet("snake food:10"), getTagSet("new")),
            new Pet(new Name("Queen"), Gender.MALE, new DateOfBirth("14/11/2019"), new Species("rabbit"),
                        getFoodSet("rabbit food:10"), getTagSet("new")),
            new Pet(new Name("Real"), Gender.FEMALE, new DateOfBirth("11/12/2019"), new Species("hamster"),
                        getFoodSet("hamster food:10"), getTagSet("new")),
            new Pet(new Name("Silly"), Gender.FEMALE, new DateOfBirth("12/1/2020"), new Species("hamster"),
                        getFoodSet("hamster food:10"), getTagSet("new")),
            new Pet(new Name("Tear"), Gender.FEMALE, new DateOfBirth("12/1/2019"), new Species("rabbit"),
                        getFoodSet("rabbit food:10"), getTagSet("new")),
            new Pet(new Name("Ugly"), Gender.MALE, new DateOfBirth("1/10/2018"), new Species("dog"),
                        getFoodSet("dog food:10"), getTagSet("new")),
            new Pet(new Name("Villa"), Gender.MALE, new DateOfBirth("4/6/2014"), new Species("rabbit"),
                        getFoodSet("rabbit food:10"), getTagSet("new")),
            new Pet(new Name("Windy"), Gender.MALE, new DateOfBirth("1/7/2019"), new Species("hamster"),
                        getFoodSet("hamster food:10"), getTagSet("new")),
            new Pet(new Name("Xavi"), Gender.MALE, new DateOfBirth("12/1/2019"), new Species("hamster"),
                        getFoodSet("hamster food:10"), getTagSet("new")),
            new Pet(new Name("Yolo"), Gender.MALE, new DateOfBirth("1/1/2011"), new Species("rabbit"),
                        getFoodSet("rabbit food:10"), getTagSet("new")),
            new Pet(new Name("Zoo"), Gender.MALE, new DateOfBirth("1/1/2011"), new Species("hamster"),
                        getFoodSet("hamster food:10"), getTagSet("new"))
        };
    }

    public static Slot[] getSampleSlots(PetTracker samplePt) {
        return new Slot[] {
            new Slot(samplePt.getPet(new Name("Alex")), new DateTime("8/4/2020 1500"),
                    new SlotDuration("75")),
            new Slot(samplePt.getPet(new Name("David")), new DateTime("17/4/2020 1200"),
                    new SlotDuration("100")),
            new Slot(samplePt.getPet(new Name("Elsa")), new DateTime("18/4/2020 1200"),
                    new SlotDuration("60")),
            new Slot(samplePt.getPet(new Name("Xavi")), new DateTime("18/4/2020 1600"),
                    new SlotDuration("60")),
            new Slot(samplePt.getPet(new Name("Foo")), new DateTime("18/4/2020 1800"),
                    new SlotDuration("80")),
            new Slot(samplePt.getPet(new Name("Gru")), new DateTime("6/4/2020 1300"),
                    new SlotDuration("100")),
            new Slot(samplePt.getPet(new Name("Helen")), new DateTime("7/4/2020 1600"),
                    new SlotDuration("135")),
            new Slot(samplePt.getPet(new Name("Oligay")), new DateTime("8/4/2020 1800"),
                    new SlotDuration("100")),
            new Slot(samplePt.getPet(new Name("Zoo")), new DateTime("8/4/2020 1000"),
                    new SlotDuration("100")),
            new Slot(samplePt.getPet(new Name("Yolo")), new DateTime("12/4/2020 1100"),
                    new SlotDuration("90")),
            new Slot(samplePt.getPet(new Name("Villa")), new DateTime("12/4/2020 1300"),
                    new SlotDuration("100")),
            new Slot(samplePt.getPet(new Name("Windy")), new DateTime("12/4/2020 1500"),
                    new SlotDuration("60")),
            new Slot(samplePt.getPet(new Name("Elsa")), new DateTime("13/4/2020 1900"),
                    new SlotDuration("100")),
            new Slot(samplePt.getPet(new Name("Kelly")), new DateTime("14/4/2020 1300"),
                    new SlotDuration("90")),
            new Slot(samplePt.getPet(new Name("Leo")), new DateTime("15/4/2020 1300"),
                    new SlotDuration("120")),
            new Slot(samplePt.getPet(new Name("Alex")), new DateTime("9/4/2020 1700"),
                    new SlotDuration("100")),
            new Slot(samplePt.getPet(new Name("Bob")), new DateTime("9/4/2020 1200"),
                    new SlotDuration("100")),
            new Slot(samplePt.getPet(new Name("Newbie")), new DateTime("10/4/2020 1200"),
                    new SlotDuration("60"))
        };
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
