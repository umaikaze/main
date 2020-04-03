package w154.helper.logic.commands.slot;

import static w154.helper.logic.commands.CommandTestUtil.assertCommandSuccess;
import static w154.helper.testutil.pet.TypicalPets.getTypicalPetTrackerWithSlots;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import w154.helper.model.Model;
import w154.helper.model.ModelManager;
import w154.helper.model.UserPrefs;
import w154.helper.model.slot.Slot;
import w154.helper.testutil.slot.SlotBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddSlotCommand}.
 */
public class AddSlotCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPetTrackerWithSlots(), new UserPrefs());
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
