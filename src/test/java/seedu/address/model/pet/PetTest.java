package seedu.address.model.pet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPets.COCO;
import static seedu.address.testutil.TypicalPets.GARFIELD;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.PetBuilder;

public class PetTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Pet person = new PetBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(new Tag("unused")));
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

        // different person -> returns false
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
}
