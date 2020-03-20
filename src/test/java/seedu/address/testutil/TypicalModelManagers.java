package seedu.address.testutil;

import static seedu.address.testutil.TypicalPets.COCO;

import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * A utility class containing a list of {@code ModelManager} objects to be used in tests.
 */
public class TypicalModelManagers {
    public static final ModelManager JUST_COCO = new ModelManager(
            new PetTrackerBuilder().withPet(COCO).build(), new UserPrefs());
}
