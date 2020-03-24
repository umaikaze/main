package seedu.address.testutil.slot;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_COCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_COCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_GARFIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GARFIELD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.pet.Name;
import seedu.address.model.slot.Slot;

/**
 * Class to generate sample slots, needs to be initialized first in order to correctly reference the pets in model
 */
public class TypicalSlotsGenerator {

    private Slot cocoSlot;
    private Slot garfieldSlot;

    //TODO Modify this for getTypicalModelManager
    private final Model model;

    public TypicalSlotsGenerator(Model model) {
        this.model = model;
    }

    public List<Slot> getTypicalSlots() {
        cocoSlot = new SlotBuilder(model).withPet(model.getPet(new Name(VALID_NAME_COCO)))
                .withDateTime(VALID_DATETIME_COCO)
                .withDuration(VALID_DURATION_COCO).build();
        garfieldSlot = new SlotBuilder(model).withPet(model.getPet(new Name(VALID_NAME_GARFIELD)))
                .withDateTime(VALID_DATETIME_GARFIELD)
                .withDuration(VALID_DURATION_GARFIELD).build();
        return new ArrayList<>(Arrays.asList(cocoSlot, garfieldSlot));
    }
}
