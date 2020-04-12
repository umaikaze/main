package clzzz.helper.model.pet;

import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DOB_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_GENDER_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_NAME_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_SPECIES_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_TAG_FAT;
import static clzzz.helper.testutil.Assert.assertThrows;
import static clzzz.helper.testutil.pet.TypicalPets.COCO;
import static clzzz.helper.testutil.pet.TypicalPets.GARFIELD;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import clzzz.helper.testutil.pet.PetBuilder;

public class PetTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Pet pet = new PetBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> pet.getTags().remove(new Tag("unused")));
    }

    @Test
    public void isSamePet() {
        // same object -> returns true
        assertTrue(COCO.isSamePet(COCO));

        // null -> returns false
        assertFalse(COCO.isSamePet(null));

        // different name -> returns false
        Pet editedCoco = new PetBuilder(COCO).withName(VALID_NAME_GARFIELD).build();
        assertFalse(COCO.isSamePet(editedCoco));

        // different species -> returns true
        editedCoco = new PetBuilder(COCO).withSpecies(VALID_SPECIES_GARFIELD).build();
        assertTrue(COCO.isSamePet(editedCoco));

        // same name, same gender, same date of birth, same species, different foodList -> returns true
        // TBD after SampleDataUtil is finished!

        // different gender and date of birth -> returns true
        editedCoco = new PetBuilder(COCO).withGender(VALID_GENDER_GARFIELD).withDateOfBirth(VALID_DOB_GARFIELD)
                .build();
        assertTrue(COCO.isSamePet(editedCoco));

        // same name, different gender, different date of birth, different species, different  tags -> returns true
        editedCoco = new PetBuilder(COCO).withGender(VALID_GENDER_GARFIELD).withDateOfBirth(VALID_DOB_GARFIELD)
                .withSpecies(VALID_SPECIES_GARFIELD).withTags(VALID_TAG_FAT).build();
        assertTrue(COCO.isSamePet(editedCoco));

        // same name, same gender, same date of birth, same species, different tags -> returns true
        editedCoco = new PetBuilder(COCO).withTags(VALID_TAG_FAT).build();
        assertTrue(COCO.isSamePet(editedCoco));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Pet aliceCopy = new PetBuilder(COCO).build();
        assertTrue(COCO.equals(aliceCopy));

        // same object -> returns true
        assertTrue(COCO.equals(COCO));

        // null -> returns false
        assertFalse(COCO.equals(null));

        // different type -> returns false
        assertFalse(COCO.equals(5));

        // different pet -> returns false
        assertFalse(COCO.equals(GARFIELD));

        // different name -> returns false
        Pet editedCoco = new PetBuilder(COCO).withName(VALID_NAME_GARFIELD).build();
        assertFalse(COCO.equals(editedCoco));

        // different gender -> returns false
        editedCoco = new PetBuilder(COCO).withGender(VALID_GENDER_GARFIELD).build();
        assertFalse(COCO.equals(editedCoco));

        // different date of birth -> returns false
        editedCoco = new PetBuilder(COCO).withDateOfBirth(VALID_DOB_GARFIELD).build();
        assertFalse(COCO.equals(editedCoco));

        // different address -> returns false
        editedCoco = new PetBuilder(COCO).withSpecies(VALID_SPECIES_GARFIELD).build();
        assertFalse(COCO.equals(editedCoco));

        // different tags -> returns false
        editedCoco = new PetBuilder(COCO).withTags(VALID_TAG_FAT).build();
        assertFalse(COCO.equals(editedCoco));
    }

    @Test
    public void initializeFoodList() {
        Food typeA10 = new Food("type A", 10);
        Food typeA20 = new Food("type A", 20);
        Food typeA30 = new Food("type A", 30);
        Set<Food> setWithRepeatingTypes = new HashSet<>();
        setWithRepeatingTypes.add(typeA10);
        setWithRepeatingTypes.add(typeA20);

        Pet cocoCopy = new PetBuilder(COCO).build();
        cocoCopy.initializeFoodList(setWithRepeatingTypes);
        assertTrue(cocoCopy.getFoodList().contains(typeA30));
    }
}
