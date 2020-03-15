package seedu.address.logic.commands.pet;

import static seedu.address.logic.commands.pet.PshCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.pet.PshCommandTestUtil.showPetAtIndex;
import static seedu.address.testutil.pet.PshTypicalIndexes.INDEX_FIRST_PET;
import static seedu.address.testutil.TypicalPets.getTypicalPetTracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.PshModel;
import seedu.address.model.PshModelManager;
import seedu.address.model.PshUserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private PshModel model;
    private PshModel expectedModel;

    @BeforeEach
    public void setUp() {
        model = new PshModelManager(getTypicalPetTracker(), new PshUserPrefs());
        expectedModel = new PshModelManager(model.getPetTracker(), new PshUserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPetAtIndex(model, INDEX_FIRST_PET);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
