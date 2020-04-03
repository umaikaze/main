package w154.helper.model.pet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static w154.helper.logic.commands.CommandTestUtil.VALID_SPECIES_GARFIELD;
import static w154.helper.logic.commands.CommandTestUtil.VALID_TAG_FAT;
import static w154.helper.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import w154.helper.model.pet.exceptions.DuplicatePetException;
import w154.helper.model.pet.exceptions.PetNotFoundException;
import w154.helper.testutil.pet.PetBuilder;
import w154.helper.testutil.pet.TypicalPets;

public class UniquePetListTest {

    private final UniquePetList uniquePetList = new UniquePetList();

    @Test
    public void contains_nullPet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.contains(null));
    }

    @Test
    public void contains_petNotInList_returnsFalse() {
        assertFalse(uniquePetList.contains(TypicalPets.COCO));
    }

    @Test
    public void contains_petInList_returnsTrue() {
        uniquePetList.add(TypicalPets.COCO);
        assertTrue(uniquePetList.contains(TypicalPets.COCO));
    }

    @Test
    public void contains_petWithSameIdentityFieldsInList_returnsTrue() {
        uniquePetList.add(TypicalPets.COCO);
        Pet editedAlice = new PetBuilder(TypicalPets.COCO).withTags(VALID_TAG_FAT)
                .build();
        assertTrue(uniquePetList.contains(editedAlice));
    }

    @Test
    public void add_nullPet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.add(null));
    }

    @Test
    public void add_duplicatePet_throwsDuplicatePetException() {
        uniquePetList.add(TypicalPets.COCO);
        assertThrows(DuplicatePetException.class, () -> uniquePetList.add(TypicalPets.COCO));
    }

    @Test
    public void setPet_nullTargetPet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.setPet(null, TypicalPets.COCO));
    }

    @Test
    public void setPet_nullEditedPet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.setPet(TypicalPets.COCO, null));
    }

    @Test
    public void setPet_targetPetNotInList_throwsPetNotFoundException() {
        assertThrows(PetNotFoundException.class, () -> uniquePetList.setPet(TypicalPets.COCO, TypicalPets.COCO));
    }

    @Test
    public void setPet_editedPetIsSamePet_success() {
        uniquePetList.add(TypicalPets.COCO);
        uniquePetList.setPet(TypicalPets.COCO, TypicalPets.COCO);
        UniquePetList expectedUniquePetList = new UniquePetList();
        expectedUniquePetList.add(TypicalPets.COCO);
        assertEquals(expectedUniquePetList, uniquePetList);
    }

    @Test
    public void setPet_editedPetHasSameIdentity_success() {
        uniquePetList.add(TypicalPets.COCO);
        Pet editedAlice = new PetBuilder(TypicalPets.COCO).withSpecies(VALID_SPECIES_GARFIELD).withTags(VALID_TAG_FAT)
                .build();
        uniquePetList.setPet(TypicalPets.COCO, editedAlice);
        UniquePetList expectedUniquePetList = new UniquePetList();
        expectedUniquePetList.add(editedAlice);
        assertEquals(expectedUniquePetList, uniquePetList);
    }

    @Test
    public void setPet_editedPetHasDifferentIdentity_success() {
        uniquePetList.add(TypicalPets.COCO);
        uniquePetList.setPet(TypicalPets.COCO, TypicalPets.GARFIELD);
        UniquePetList expectedUniquePetList = new UniquePetList();
        expectedUniquePetList.add(TypicalPets.GARFIELD);
        assertEquals(expectedUniquePetList, uniquePetList);
    }

    @Test
    public void setPet_editedPetHasNonUniqueIdentity_throwsDuplicatePetException() {
        uniquePetList.add(TypicalPets.COCO);
        uniquePetList.add(TypicalPets.GARFIELD);
        assertThrows(DuplicatePetException.class, () -> uniquePetList.setPet(TypicalPets.COCO, TypicalPets.GARFIELD));
    }

    @Test
    public void remove_nullPet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.remove(null));
    }

    @Test
    public void remove_petDoesNotExist_throwsPetNotFoundException() {
        assertThrows(PetNotFoundException.class, () -> uniquePetList.remove(TypicalPets.COCO));
    }

    @Test
    public void remove_existingPet_removesPet() {
        uniquePetList.add(TypicalPets.COCO);
        uniquePetList.remove(TypicalPets.COCO);
        UniquePetList expectedUniquePetList = new UniquePetList();
        assertEquals(expectedUniquePetList, uniquePetList);
    }

    @Test
    public void setPets_nullUniquePetList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.setPets((UniquePetList) null));
    }

    @Test
    public void setPets_uniquePetList_replacesOwnListWithProvidedUniquePetList() {
        uniquePetList.add(TypicalPets.COCO);
        UniquePetList expectedUniquePetList = new UniquePetList();
        expectedUniquePetList.add(TypicalPets.GARFIELD);
        uniquePetList.setPets(expectedUniquePetList);
        assertEquals(expectedUniquePetList, uniquePetList);
    }

    @Test
    public void setPets_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.setPets((List<Pet>) null));
    }

    @Test
    public void setPets_list_replacesOwnListWithProvidedList() {
        uniquePetList.add(TypicalPets.COCO);
        List<Pet> petList = Collections.singletonList(TypicalPets.GARFIELD);
        uniquePetList.setPets(petList);
        UniquePetList expectedUniquePetList = new UniquePetList();
        expectedUniquePetList.add(TypicalPets.GARFIELD);
        assertEquals(expectedUniquePetList, uniquePetList);
    }

    @Test
    public void setPets_listWithDuplicatePets_throwsDuplicatePetException() {
        List<Pet> listWithDuplicatePets = Arrays.asList(TypicalPets.COCO, TypicalPets.COCO);
        assertThrows(DuplicatePetException.class, () -> uniquePetList.setPets(listWithDuplicatePets));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePetList.asUnmodifiableObservableList().remove(0));
    }
}
