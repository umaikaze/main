package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_COCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_COCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
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

    public static final Pet COCO = new PetBuilder().withName("Coco")
            .withSpecies("Dog").withDateOfBirth("1/6/2015")
            .withGender(Gender.FEMALE).withFoodList("Brand A")
            .withTags("darkFur").build();
    public static final Pet GARFIELD = new PetBuilder().withName("Garfield Arbuckle")
            .withSpecies("Cat").withFoodList("Brand B")
            .withDateOfBirth("19/6/1978").withGender(Gender.MALE)
            .withTags("fat", "lazy").build();
    public static final Pet CARL = new PetBuilder().withName("Carl Kurz").withGender(Gender.MALE)
            .withDateOfBirth("1/3/2015").withSpecies("Husky").withFoodList("Brand C").build();
    public static final Pet DANIEL = new PetBuilder().withName("Daniel Meier").withGender(Gender.MALE)
            .withDateOfBirth("2/3/2015").withSpecies("Dolphin").withTags("pink").withFoodList("Brand D").build();
    public static final Pet ELLE = new PetBuilder().withName("Elle Meyer").withGender(Gender.FEMALE)
            .withDateOfBirth("3/3/2015").withFoodList("Brand E").withSpecies("Parrot").build();
    public static final Pet FIONA = new PetBuilder().withName("Fiona Kunz").withGender(Gender.FEMALE)
            .withDateOfBirth("4/3/2015").withFoodList("Brand F").withSpecies("Goldfish").build();
    public static final Pet GEORGE = new PetBuilder().withName("George Best").withGender(Gender.MALE)
            .withDateOfBirth("5/3/2015").withSpecies("Slug").withFoodList("Brand G").build();

    // Manually added
    public static final Pet HOON = new PetBuilder().withName("Hoon Meier").withGender(Gender.MALE)
            .withDateOfBirth("6/3/2015").withSpecies("little india").build();
    public static final Pet IDA = new PetBuilder().withName("Ida Mueller").withGender(Gender.MALE)
            .withDateOfBirth("7/3/2015").withSpecies("chicago ave").build();

    // Manually added - Pet's details found in {@code CommandTestUtil}
    public static final Pet AMY = new PetBuilder().withName(VALID_NAME_AMY).withGender(VALID_GENDER_COCO)
            .withDateOfBirth(VALID_DOB_COCO).withSpecies(VALID_SPECIES_GARFIELD).withTags(VALID_TAG_HYPER).build();
    public static final Pet BOB = new PetBuilder().withName(VALID_NAME_BOB).withGender(VALID_GENDER_GARFIELD)
            .withDateOfBirth(VALID_DOB_GARFIELD).withSpecies(VALID_SPECIES_GARFIELD)
            .withTags(VALID_TAG_FAT, VALID_TAG_LAZY).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPets() {} // prevents instantiation

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
        return new ArrayList<>(Arrays.asList(COCO, GARFIELD, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
