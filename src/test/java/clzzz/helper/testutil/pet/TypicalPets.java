package clzzz.helper.testutil.pet;

import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DOB_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DOB_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_FOOD_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_FOOD_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_GENDER_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_GENDER_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_NAME_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_NAME_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_SPECIES_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_SPECIES_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_TAG_FAT;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_TAG_LAZY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clzzz.helper.model.PetTracker;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.testutil.slot.TypicalSlots;

//TODO the package it belongs to does not fit its usage (potentially used for slots too, make it outside or split up)

/**
 * A utility class containing a list of {@code Pet} objects to be used in tests.
 */
public class TypicalPets {

    public static final Pet AMY = new PetBuilder().withName("Amy")
            .withSpecies("Dog").withDateOfBirth("1/6/2015")
            .withGender("female").withFoodList("Brand A:10")
            .withTags("darkFur").build();
    public static final Pet BOB = new PetBuilder().withName("Bob")
            .withSpecies("Cat").withFoodList("Brand B:10")
            .withDateOfBirth("19/6/1978").withGender("MALE")
            .withTags("fat", "lazy").build();
    public static final Pet CARL = new PetBuilder().withName("Carl").withGender("MALE")
            .withDateOfBirth("1/3/2015").withSpecies("Husky").withFoodList("Brand C:10").build();
    public static final Pet DANIEL = new PetBuilder().withName("Daniel").withGender("MALE")
            .withDateOfBirth("2/3/2015").withSpecies("Dolphin").withTags("pink").withFoodList("Brand D:10").build();
    public static final Pet ELLE = new PetBuilder().withName("Elle").withGender("FEMALE")
            .withDateOfBirth("3/3/2015").withFoodList("Brand E:10").withSpecies("Parrot").build();
    public static final Pet FIONA = new PetBuilder().withName("Fiona").withGender("FEMALE")
            .withDateOfBirth("4/3/2015").withFoodList("Brand F:10").withSpecies("Goldfish").build();
    public static final Pet GEORGE = new PetBuilder().withName("George").withGender("MALE")
            .withDateOfBirth("5/3/2015").withSpecies("Slug").withFoodList("Brand G:10").build();

    // Manually added
    public static final Pet HOON = new PetBuilder().withName("Hoon").withGender("male")
            .withDateOfBirth("6/3/2015").withSpecies("passerine").build();
    public static final Pet IDA = new PetBuilder().withName("Ida").withGender("MALE")
            .withDateOfBirth("7/3/2015").withSpecies("bull").build();

    // Manually added - Pet's details found in {@code CommandTestUtil}
    public static final Pet COCO = new PetBuilder().withName(VALID_NAME_COCO).withGender(VALID_GENDER_COCO)
            .withDateOfBirth(VALID_DOB_COCO).withSpecies(VALID_SPECIES_COCO).withFoodList(VALID_FOOD_COCO)
            .withTags(VALID_TAG_LAZY).build();
    public static final Pet GARFIELD = new PetBuilder().withName(VALID_NAME_GARFIELD).withGender(VALID_GENDER_GARFIELD)
            .withDateOfBirth(VALID_DOB_GARFIELD).withSpecies(VALID_SPECIES_GARFIELD).withFoodList(VALID_FOOD_GARFIELD)
            .withTags(VALID_TAG_FAT, VALID_TAG_LAZY).build();

    private TypicalPets() {
    } // prevents instantiation

    /**
     * Returns a {@code PetTracker} with all the typical pets.
     */
    public static PetTracker getTypicalPetTracker() {
        PetTracker pt = new PetTracker();
        for (Pet pet : getTypicalPets()) {
            pt.addPet(pet);
        }
        return pt;
    }

    public static PetTracker getTypicalPetTrackerWithSlots() {
        PetTracker pt = new PetTracker();
        for (Pet pet : getTypicalPets()) {
            pt.addPet(pet);
        }
        for (Slot slot : TypicalSlots.getTypicalSlots()) {
            pt.addSlot(slot);
        }

        return pt;
    }

    public static List<Pet> getTypicalPets() {
        return new ArrayList<>(Arrays.asList(AMY, BOB, CARL, DANIEL, ELLE, FIONA, GEORGE, COCO, GARFIELD));
    }
}
