package clzzz.helper.model;

import static clzzz.helper.logic.commands.CommandTestUtil.VALID_SPECIES_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_TAG_FAT;
import static clzzz.helper.testutil.Assert.assertThrows;
import static clzzz.helper.testutil.pet.TypicalPets.COCO;
import static clzzz.helper.testutil.pet.TypicalPets.GARFIELD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.pet.exceptions.DuplicatePetException;
import clzzz.helper.model.pet.exceptions.PetNotFoundException;
import clzzz.helper.testutil.pet.PetBuilder;

public class UniquePetListTest {

    private final UniquePetList uniquePetList = new UniquePetList();

    @Test
    public void contains_nullPet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.contains(null));
    }

    @Test
    public void contains_petNotInList_returnsFalse() {
        assertFalse(uniquePetList.contains(COCO));
    }

    @Test
    public void contains_petInList_returnsTrue() {
        uniquePetList.add(COCO);
        assertTrue(uniquePetList.contains(COCO));
    }

    @Test
    public void contains_petWithSameIdentityFieldsInList_returnsTrue() {
        uniquePetList.add(COCO);
        Pet editedAlice = new PetBuilder(COCO).withTags(VALID_TAG_FAT)
                .build();
        assertTrue(uniquePetList.contains(editedAlice));
    }

    @Test
    public void add_nullPet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.add(null));
    }

    @Test
    public void add_duplicatePet_throwsDuplicatePetException() {
        uniquePetList.add(COCO);
        assertThrows(DuplicatePetException.class, () -> uniquePetList.add(COCO));
    }

    @Test
    public void setPet_nullTargetPet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.setPet(null, COCO));
    }

    @Test
    public void setPet_nullEditedPet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.setPet(COCO, null));
    }

    @Test
    public void setPet_targetPetNotInList_throwsPetNotFoundException() {
        assertThrows(PetNotFoundException.class, () -> uniquePetList.setPet(COCO, COCO));
    }

    @Test
    public void setPet_editedPetIsSamePet_success() {
        uniquePetList.add(COCO);
        uniquePetList.setPet(COCO, COCO);
        UniquePetList expectedUniquePetList = new UniquePetList();
        expectedUniquePetList.add(COCO);
        assertEquals(expectedUniquePetList, uniquePetList);
    }

    @Test
    public void setPet_editedPetHasSameIdentity_success() {
        uniquePetList.add(COCO);
        Pet editedAlice = new PetBuilder(COCO).withSpecies(VALID_SPECIES_GARFIELD).withTags(VALID_TAG_FAT)
                .build();
        uniquePetList.setPet(COCO, editedAlice);
        UniquePetList expectedUniquePetList = new UniquePetList();
        expectedUniquePetList.add(editedAlice);
        assertEquals(expectedUniquePetList, uniquePetList);
    }

    @Test
    public void setPet_editedPetHasDifferentIdentity_success() {
        uniquePetList.add(COCO);
        uniquePetList.setPet(COCO, GARFIELD);
        UniquePetList expectedUniquePetList = new UniquePetList();
        expectedUniquePetList.add(GARFIELD);
        assertEquals(expectedUniquePetList, uniquePetList);
    }

    @Test
    public void setPet_editedPetHasNonUniqueIdentity_throwsDuplicatePetException() {
        uniquePetList.add(COCO);
        uniquePetList.add(GARFIELD);
        assertThrows(DuplicatePetException.class, () -> uniquePetList.setPet(COCO, GARFIELD));
    }

    @Test
    public void remove_nullPet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.remove(null));
    }

    @Test
    public void remove_petDoesNotExist_throwsPetNotFoundException() {
        assertThrows(PetNotFoundException.class, () -> uniquePetList.remove(COCO));
    }

    @Test
    public void remove_existingPet_removesPet() {
        uniquePetList.add(COCO);
        uniquePetList.remove(COCO);
        UniquePetList expectedUniquePetList = new UniquePetList();
        assertEquals(expectedUniquePetList, uniquePetList);
    }

    @Test
    public void setPets_nullUniquePetList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.setPets((UniquePetList) null));
    }

    @Test
    public void setPets_uniquePetList_replacesOwnListWithProvidedUniquePetList() {
        uniquePetList.add(COCO);
        UniquePetList expectedUniquePetList = new UniquePetList();
        expectedUniquePetList.add(GARFIELD);
        uniquePetList.setPets(expectedUniquePetList);
        assertEquals(expectedUniquePetList, uniquePetList);
    }

    @Test
    public void setPets_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.setPets((List<Pet>) null));
    }

    @Test
    public void setPets_list_replacesOwnListWithProvidedList() {
        uniquePetList.add(COCO);
        List<Pet> petList = Collections.singletonList(GARFIELD);
        uniquePetList.setPets(petList);
        UniquePetList expectedUniquePetList = new UniquePetList();
        expectedUniquePetList.add(GARFIELD);
        assertEquals(expectedUniquePetList, uniquePetList);
    }

    @Test
    public void setPets_listWithDuplicatePets_throwsDuplicatePetException() {
        List<Pet> listWithDuplicatePets = Arrays.asList(COCO, COCO);
        assertThrows(DuplicatePetException.class, () -> uniquePetList.setPets(listWithDuplicatePets));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePetList.asUnmodifiableObservableList().remove(0));
    }
}
