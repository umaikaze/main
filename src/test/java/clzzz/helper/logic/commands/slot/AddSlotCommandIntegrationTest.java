package clzzz.helper.logic.commands.slot;

import static clzzz.helper.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clzzz.helper.testutil.pet.TypicalPets;
import clzzz.helper.testutil.slot.SlotBuilder;
import clzzz.helper.model.Model;
import clzzz.helper.model.ModelManager;
import clzzz.helper.model.UserPrefs;
import clzzz.helper.model.slot.Slot;

/**
 * Contains integration tests (interaction with the Model) for {@code AddSlotCommand}.
 */
public class AddSlotCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalPets.getTypicalPetTrackerWithSlots(), new UserPrefs());
    }

    @Test
    public void execute_newSlot_success() {
        Slot validSlot = new SlotBuilder().build();

        Model expectedModel = new ModelManager(model.getPetTracker(), new UserPrefs());
        expectedModel.addSlot(validSlot);

        assertCommandSuccess(new AddSlotCommand(validSlot, ""), model,
                String.format(AddSlotCommand.MESSAGE_SUCCESS, validSlot), expectedModel);
    }

}
