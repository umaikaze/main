package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_COCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_COCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_COCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_COCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HYPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LAZY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.PetTracker;
import seedu.address.model.pet.Gender;
import seedu.address.model.pet.Pet;

/**
 * A utility class containing a list of {@code Pet} objects to be used in tests.
 */
public class TypicalPets {

    public static final Pet AMY = new PetBuilder().withName("Amy")
            .withSpecies("Dog").withDateOfBirth("1/6/2015")
            .withGender(Gender.FEMALE).withFoodList("Brand A:10")
            .withTags("darkFur").build();
    public static final Pet BOB = new PetBuilder().withName("Bob")
            .withSpecies("Cat").withFoodList("Brand B:10")
            .withDateOfBirth("19/6/1978").withGender(Gender.MALE)
            .withTags("fat", "lazy").build();
    public static final Pet CARL = new PetBuilder().withName("Carl").withGender(Gender.MALE)
            .withDateOfBirth("1/3/2015").withSpecies("Husky").withFoodList("Brand C:10").build();
    public static final Pet DANIEL = new PetBuilder().withName("Daniel").withGender(Gender.MALE)
            .withDateOfBirth("2/3/2015").withSpecies("Dolphin").withTags("pink").withFoodList("Brand D:10").build();
    public static final Pet ELLE = new PetBuilder().withName("Elle").withGender(Gender.FEMALE)
            .withDateOfBirth("3/3/2015").withFoodList("Brand E:10").withSpecies("Parrot").build();
    public static final Pet FIONA = new PetBuilder().withName("Fiona").withGender(Gender.FEMALE)
            .withDateOfBirth("4/3/2015").withFoodList("Brand F:10").withSpecies("Goldfish").build();
    public static final Pet GEORGE = new PetBuilder().withName("George").withGender(Gender.MALE)
            .withDateOfBirth("5/3/2015").withSpecies("Slug").withFoodList("Brand G:10").build();

    // Manually added
    public static final Pet HOON = new PetBuilder().withName("Hoon").withGender(Gender.MALE)
            .withDateOfBirth("6/3/2015").withSpecies("passerine").build();
    public static final Pet IDA = new PetBuilder().withName("Ida").withGender(Gender.MALE)
            .withDateOfBirth("7/3/2015").withSpecies("bull").build();

    // Manually added - Pet's details found in {@code CommandTestUtil}
    public static final Pet COCO = new PetBuilder().withName(VALID_NAME_COCO).withGender(VALID_GENDER_COCO)
            .withDateOfBirth(VALID_DOB_COCO).withSpecies(VALID_SPECIES_COCO).withFoodList(VALID_FOOD_COCO)
            .withTags(VALID_TAG_HYPER).build();
    public static final Pet GARFIELD = new PetBuilder().withName(VALID_NAME_GARFIELD).withGender(VALID_GENDER_GARFIELD)
            .withDateOfBirth(VALID_DOB_GARFIELD).withSpecies(VALID_SPECIES_GARFIELD).withFoodList(VALID_FOOD_GARFIELD)
            .withTags(VALID_TAG_FAT, VALID_TAG_LAZY).build();

    private TypicalPets() {
    } // prevents instantiation

    /**
     * TBD
     */
    /* public static AddressBook getTypicalSpeciesBook() {
        AddressBook ab = new AddressBook();
        for (Pet pet : getTypicalPets()) {
            ab.addPet(pet);
        }
        return ab;
    }*/

    //for JsonPetTrackerStorageTest
    public static PetTracker getTypicalPetTracker() {
        PetTracker ab = new PetTracker();
        for (Pet pet : getTypicalPets()) {
            ab.addPet(pet);
        }
        return ab;
    }

    public static List<Pet> getTypicalPets() {
        return new ArrayList<>(Arrays.asList(AMY, BOB, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
