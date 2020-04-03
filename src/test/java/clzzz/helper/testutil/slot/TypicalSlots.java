package clzzz.helper.testutil.slot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clzzz.helper.logic.commands.CommandTestUtil;
import clzzz.helper.model.slot.Slot;

/**
 * Class to generate sample slots, needs to be initialized first in order to correctly reference the pets in model
 */
public class TypicalSlots {

    public static final Slot COCO_SLOT = new SlotBuilder().withPet(CommandTestUtil.VALID_NAME_COCO)
            .withDateTime(CommandTestUtil.VALID_DATETIME_COCO)
            .withDuration(CommandTestUtil.VALID_DURATION_COCO).build();
    public static final Slot GARFIELD_SLOT = new SlotBuilder().withPet(CommandTestUtil.VALID_NAME_GARFIELD)
            .withDateTime(CommandTestUtil.VALID_DATETIME_GARFIELD)
            .withDuration(CommandTestUtil.VALID_DURATION_GARFIELD).build();


    public static List<Slot> getTypicalSlots() {
        return new ArrayList<>(Arrays.asList(COCO_SLOT, GARFIELD_SLOT));
    }
}
