package clzzz.helper.model.util;

import static clzzz.helper.commons.util.DateTimeUtil.DATETIME_FORMAT;

import java.time.Duration;
import java.time.LocalDateTime;
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
import clzzz.helper.model.slot.Slot;
import clzzz.helper.model.tag.Tag;


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
                        getFoodSet("hamster food:10"), getTagSet("new"))};
    }

    public static Slot[] getSampleSlots(PetTracker samplePt) {
        return new Slot[]{
            new Slot(samplePt.getPet(new Name("Alex")), LocalDateTime.parse("8/4/2020 1500", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("75"))),
            new Slot(samplePt.getPet(new Name("David")), LocalDateTime.parse("8/4/2020 2000", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("88"))),
            new Slot(samplePt.getPet(new Name("Elsa")), LocalDateTime.parse("18/4/2020 1200", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("60"))),
            new Slot(samplePt.getPet(new Name("Xavi")), LocalDateTime.parse("18/4/2020 1600", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("60"))),
            new Slot(samplePt.getPet(new Name("Foo")), LocalDateTime.parse("18/4/2020 1800", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("80"))),
            new Slot(samplePt.getPet(new Name("Gru")), LocalDateTime.parse("6/4/2020 1300", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("100"))),
            new Slot(samplePt.getPet(new Name("Helen")), LocalDateTime.parse("7/4/2020 1600", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("40"))),
            new Slot(samplePt.getPet(new Name("Oligay")), LocalDateTime.parse("8/4/2020 1800", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("100"))),
            new Slot(samplePt.getPet(new Name("Zoo")), LocalDateTime.parse("8/4/2020 1000", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("100"))),
            new Slot(samplePt.getPet(new Name("Yolo")), LocalDateTime.parse("12/4/2020 1200", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("40"))),
            new Slot(samplePt.getPet(new Name("Villa")), LocalDateTime.parse("12/4/2020 1300", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("100"))),
            new Slot(samplePt.getPet(new Name("Windy")), LocalDateTime.parse("12/4/2020 1500", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("20"))),
            new Slot(samplePt.getPet(new Name("Elsa")), LocalDateTime.parse("13/4/2020 1900", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("100"))),
            new Slot(samplePt.getPet(new Name("Kelly")), LocalDateTime.parse("14/4/2020 1300", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("90"))),
            new Slot(samplePt.getPet(new Name("Leo")), LocalDateTime.parse("15/4/2020 1300", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("100"))),
            new Slot(samplePt.getPet(new Name("Alex")), LocalDateTime.parse("9/4/2020 1700", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("100"))),
            new Slot(samplePt.getPet(new Name("Bob")), LocalDateTime.parse("9/4/2020 1200", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("100"))),
            new Slot(samplePt.getPet(new Name("Newbie")), LocalDateTime.parse("10/4/2020 1200", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("60"))),
            new Slot(samplePt.getPet(new Name("Molly")), LocalDateTime.parse("17/4/2020 1200", DATETIME_FORMAT),
                    Duration.ofMinutes(Long.parseLong("100")))};
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
