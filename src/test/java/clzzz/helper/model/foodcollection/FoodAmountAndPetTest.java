package clzzz.helper.model.foodcollection;

import static clzzz.helper.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import clzzz.helper.model.pet.Pet;
import clzzz.helper.testutil.pet.PetBuilder;

class FoodAmountAndPetTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FoodAmountAndPet(0, null));
    }

    @Test
    void constructor_invalidFoodAmount_throwsIllegalArgumentException() {
        Pet validPet = new PetBuilder().build();
        int invalidAmount = -1;
        assertThrows(IllegalArgumentException.class, () -> new FoodAmountAndPet(invalidAmount,
                validPet.getName().fullName));
    }
}
