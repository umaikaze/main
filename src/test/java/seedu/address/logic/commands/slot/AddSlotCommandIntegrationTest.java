package seedu.address.logic.commands.slot;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.pet.TypicalPets.getTypicalModelManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.slot.Slot;
import seedu.address.testutil.slot.SlotBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddSlotCommand}.
 */
public class AddSlotCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = getTypicalModelManager();
    }

    @Test
    public void execute_newSlot_success() {
        Slot validSlot = new SlotBuilder(model).build();

        Model expectedModel = new ModelManager(model.getPetTracker(), new UserPrefs());
        expectedModel.addSlot(validSlot);

        assertCommandSuccess(new AddSlotCommand(validSlot, ""), model,
                String.format(AddSlotCommand.MESSAGE_SUCCESS, validSlot), expectedModel);
    }

}
