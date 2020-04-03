package w154.helper.model.pet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static w154.helper.logic.commands.CommandTestUtil.VALID_DOB_GARFIELD;
import static w154.helper.logic.commands.CommandTestUtil.VALID_GENDER_GARFIELD;
import static w154.helper.logic.commands.CommandTestUtil.VALID_NAME_GARFIELD;
import static w154.helper.logic.commands.CommandTestUtil.VALID_SPECIES_GARFIELD;
import static w154.helper.logic.commands.CommandTestUtil.VALID_TAG_FAT;
import static w154.helper.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import w154.helper.model.tag.Tag;
import w154.helper.testutil.pet.PetBuilder;
import w154.helper.testutil.pet.TypicalPets;

public class PetTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Pet pet = new PetBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> pet.getTags().remove(new Tag("unused")));
    }

    @Test
    public void isSamePet() {
        // same object -> returns true
        Assertions.assertTrue(TypicalPets.COCO.isSamePet(TypicalPets.COCO));

        // null -> returns false
        Assertions.assertFalse(TypicalPets.COCO.isSamePet(null));

        // different name -> returns false
        Pet editedCoco = new PetBuilder(TypicalPets.COCO).withName(VALID_NAME_GARFIELD).build();
        Assertions.assertFalse(TypicalPets.COCO.isSamePet(editedCoco));

        // different species -> returns true
        editedCoco = new PetBuilder(TypicalPets.COCO).withSpecies(VALID_SPECIES_GARFIELD).build();
        Assertions.assertTrue(TypicalPets.COCO.isSamePet(editedCoco));

        // same name, same gender, same date of birth, same species, different foodList -> returns true
        // TBD after SampleDataUtil is finished!

        // different gender and date of birth -> returns true
        editedCoco = new PetBuilder(TypicalPets.COCO).withGender(VALID_GENDER_GARFIELD).withDateOfBirth(VALID_DOB_GARFIELD)
                .build();
        Assertions.assertTrue(TypicalPets.COCO.isSamePet(editedCoco));

        // same name, different gender, different date of birth, different species, different  tags -> returns true
        editedCoco = new PetBuilder(TypicalPets.COCO).withGender(VALID_GENDER_GARFIELD).withDateOfBirth(VALID_DOB_GARFIELD)
                .withSpecies(VALID_SPECIES_GARFIELD).withTags(VALID_TAG_FAT).build();
        Assertions.assertTrue(TypicalPets.COCO.isSamePet(editedCoco));

        // same name, same gender, same date of birth, same species, different tags -> returns true
        editedCoco = new PetBuilder(TypicalPets.COCO).withTags(VALID_TAG_FAT).build();
        Assertions.assertTrue(TypicalPets.COCO.isSamePet(editedCoco));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Pet aliceCopy = new PetBuilder(TypicalPets.COCO).build();
        Assertions.assertTrue(TypicalPets.COCO.equals(aliceCopy));

        // same object -> returns true
        Assertions.assertTrue(TypicalPets.COCO.equals(TypicalPets.COCO));

        // null -> returns false
        Assertions.assertFalse(TypicalPets.COCO.equals(null));

        // different type -> returns false
        Assertions.assertFalse(TypicalPets.COCO.equals(5));

        // different pet -> returns false
        Assertions.assertFalse(TypicalPets.COCO.equals(TypicalPets.GARFIELD));

        // different name -> returns false
        Pet editedCoco = new PetBuilder(TypicalPets.COCO).withName(VALID_NAME_GARFIELD).build();
        Assertions.assertFalse(TypicalPets.COCO.equals(editedCoco));

        // different gender -> returns false
        editedCoco = new PetBuilder(TypicalPets.COCO).withGender(VALID_GENDER_GARFIELD).build();
        Assertions.assertFalse(TypicalPets.COCO.equals(editedCoco));

        // different date of birth -> returns false
        editedCoco = new PetBuilder(TypicalPets.COCO).withDateOfBirth(VALID_DOB_GARFIELD).build();
        Assertions.assertFalse(TypicalPets.COCO.equals(editedCoco));

        // different address -> returns false
        editedCoco = new PetBuilder(TypicalPets.COCO).withSpecies(VALID_SPECIES_GARFIELD).build();
        Assertions.assertFalse(TypicalPets.COCO.equals(editedCoco));

        // different tags -> returns false
        editedCoco = new PetBuilder(TypicalPets.COCO).withTags(VALID_TAG_FAT).build();
        Assertions.assertFalse(TypicalPets.COCO.equals(editedCoco));
    }

    @Test
    public void initializeFoodList() {
        Food typeA10 = new Food("type A", 10);
        Food typeA20 = new Food("type A", 20);
        Food typeA30 = new Food("type A", 30);
        Set<Food> setWithRepeatingTypes = new HashSet<>();
        setWithRepeatingTypes.add(typeA10);
        setWithRepeatingTypes.add(typeA20);

        Pet cocoCopy = new PetBuilder(TypicalPets.COCO).build();
        cocoCopy.initializeFoodList(setWithRepeatingTypes);
        assertTrue(cocoCopy.getFoodList().contains(typeA30));
    }
}
