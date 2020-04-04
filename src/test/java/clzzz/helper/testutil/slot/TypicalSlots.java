package clzzz.helper.testutil.slot;

import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DATETIME_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DATETIME_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DURATION_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_DURATION_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_NAME_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_NAME_GARFIELD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clzzz.helper.model.slot.Slot;

/**
 * Class to generate sample slots, needs to be initialized first in order to correctly reference the pets in model
 */
public class TypicalSlots {

    public static final Slot COCO_SLOT = new SlotBuilder().withPet(VALID_NAME_COCO)
            .withDateTime(VALID_DATETIME_COCO)
            .withDuration(VALID_DURATION_COCO).build();
    public static final Slot GARFIELD_SLOT = new SlotBuilder().withPet(VALID_NAME_GARFIELD)
            .withDateTime(VALID_DATETIME_GARFIELD)
            .withDuration(VALID_DURATION_GARFIELD).build();


    public static List<Slot> getTypicalSlots() {
        return new ArrayList<>(Arrays.asList(COCO_SLOT, GARFIELD_SLOT));
    }
}
