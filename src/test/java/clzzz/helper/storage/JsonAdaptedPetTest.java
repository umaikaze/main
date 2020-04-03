package clzzz.helper.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static clzzz.helper.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import clzzz.helper.commons.exceptions.IllegalValueException;
import clzzz.helper.model.pet.DateOfBirth;
import clzzz.helper.model.pet.Gender;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Species;
import clzzz.helper.testutil.Assert;
import clzzz.helper.testutil.pet.TypicalPets;

public class JsonAdaptedPetTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DOB = "01 Mar 2010";
    private static final String INVALID_GENDER = "m";
    private static final String INVALID_SPECIES = "cat^";
    private static final String INVALID_FOOD_NAME = "ABC&";
    private static final String INVALID_FOOD_AMOUNT = "-1";
    private static final String INVALID_FOOD = INVALID_FOOD_NAME + ":" + INVALID_FOOD_AMOUNT;
    private static final String INVALID_TAG = "#lazy";

    private static final String VALID_NAME = TypicalPets.GARFIELD.getName().toString();
    private static final String VALID_DOB = TypicalPets.GARFIELD.getDateOfBirth().toString();
    private static final String VALID_GENDER = TypicalPets.GARFIELD.getGender().toString();
    private static final String VALID_SPECIES = TypicalPets.GARFIELD.getSpecies().toString();
    private static final List<JsonAdaptedFood> VALID_FOODLIST = TypicalPets.GARFIELD.getFoodList().stream()
            .map(JsonAdaptedFood::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedTag> VALID_TAGS = TypicalPets.GARFIELD.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPetDetails_returnsPet() throws Exception {
        JsonAdaptedPet pet = new JsonAdaptedPet(TypicalPets.GARFIELD);
        Assertions.assertEquals(TypicalPets.GARFIELD, pet.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPet pet =
                new JsonAdaptedPet(INVALID_NAME, VALID_GENDER, VALID_DOB, VALID_SPECIES, VALID_FOODLIST, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(
                null, VALID_GENDER, VALID_DOB, VALID_SPECIES, VALID_FOODLIST, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedPet.MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        JsonAdaptedPet pet =
                new JsonAdaptedPet(VALID_NAME, INVALID_GENDER, VALID_DOB, VALID_SPECIES, VALID_FOODLIST, VALID_TAGS);
        String expectedMessage = Gender.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(
                VALID_NAME, null, VALID_DOB, VALID_SPECIES, VALID_FOODLIST, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedPet.MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_invalidDateOfBirth_throwsIllegalValueException() {
        JsonAdaptedPet pet =
                new JsonAdaptedPet(VALID_NAME, VALID_GENDER, INVALID_DOB, VALID_SPECIES, VALID_FOODLIST, VALID_TAGS);
        String expectedMessage = DateOfBirth.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullDateOfBirth_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(
                VALID_NAME, VALID_GENDER, null, VALID_SPECIES, VALID_FOODLIST, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedPet.MISSING_FIELD_MESSAGE_FORMAT, DateOfBirth.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_invalidSpecies_throwsIllegalValueException() {
        JsonAdaptedPet pet =
                new JsonAdaptedPet(VALID_NAME, VALID_GENDER, VALID_DOB, INVALID_SPECIES, VALID_FOODLIST, VALID_TAGS);
        String expectedMessage = Species.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullSpecies_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(
                VALID_NAME, VALID_GENDER, VALID_DOB, null, VALID_FOODLIST, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedPet.MISSING_FIELD_MESSAGE_FORMAT, Species.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_invalidFoodList_throwsIllegalValueException() {
        List<JsonAdaptedFood> invalidFoodList = new ArrayList<>(VALID_FOODLIST);
        invalidFoodList.add(new JsonAdaptedFood(INVALID_FOOD));
        JsonAdaptedPet pet =
                new JsonAdaptedPet(VALID_NAME, VALID_GENDER, VALID_DOB, VALID_SPECIES, invalidFoodList, VALID_TAGS);
        Assert.assertThrows(IllegalValueException.class, pet::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPet pet =
                new JsonAdaptedPet(VALID_NAME, VALID_GENDER, VALID_DOB, VALID_SPECIES, VALID_FOODLIST, invalidTags);
        Assert.assertThrows(IllegalValueException.class, pet::toModelType);
    }

}
