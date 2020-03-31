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

import seedu.address.model.slot.Slot;

/**
 * Class to generate sample slots, needs to be initialized first in order to correctly reference the pets in model
 */
public class TypicalSlots {

    public static Slot cocoSlot;
    public static Slot garfieldSlot;


    public static List<Slot> getTypicalSlots() {
        cocoSlot = new SlotBuilder().withPet(VALID_NAME_COCO)
                .withDateTime(VALID_DATETIME_COCO)
                .withDuration(VALID_DURATION_COCO).build();
        garfieldSlot = new SlotBuilder().withPet(VALID_NAME_GARFIELD)
                .withDateTime(VALID_DATETIME_GARFIELD)
                .withDuration(VALID_DURATION_GARFIELD).build();
        return new ArrayList<>(Arrays.asList(cocoSlot, garfieldSlot));
    }
}
