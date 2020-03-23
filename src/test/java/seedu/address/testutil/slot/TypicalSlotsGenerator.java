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

import seedu.address.model.PetTracker;
import seedu.address.model.pet.Name;
import seedu.address.model.slot.Slot;

//TODO Use this for ModelManager
public class TypicalSlotsGenerator {

    private final PetTracker pt;

    public TypicalSlotsGenerator(PetTracker pt) {
        this.pt = pt;
    }

    public List<Slot> getTypicalSlots() {
        final Slot COCO_SLOT = new SlotBuilder().withPet(pt.getPet(new Name(VALID_NAME_COCO)))
                .withDateTime(VALID_DATETIME_COCO)
                .withDuration(VALID_DURATION_COCO).build();
        final Slot GARFIELD_SLOT = new SlotBuilder().withPet(pt.getPet(new Name(VALID_NAME_GARFIELD)))
                .withDateTime(VALID_DATETIME_GARFIELD)
                .withDuration(VALID_DURATION_GARFIELD).build();
        return new ArrayList<>(Arrays.asList(COCO_SLOT, GARFIELD_SLOT));
    }
}
