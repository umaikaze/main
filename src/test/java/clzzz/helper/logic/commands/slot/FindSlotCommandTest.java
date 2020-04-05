package clzzz.helper.logic.commands.slot;

import static clzzz.helper.commons.core.Messages.MESSAGE_SLOTS_LISTED_OVERVIEW;
import static clzzz.helper.logic.commands.CommandTestUtil.NAME_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.NAME_DESC_GARFIELD;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_NAME;
import static clzzz.helper.testutil.pet.TypicalPets.getTypicalPetTrackerWithSlots;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.function.Predicate;

import clzzz.helper.logic.parser.CommandParserTestUtil;
import clzzz.helper.logic.parser.exceptions.ParseException;
import clzzz.helper.logic.parser.slot.FindSlotCommandParser;
import clzzz.helper.model.Model;
import clzzz.helper.model.ModelManager;
import clzzz.helper.model.UserPrefs;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.model.slot.SlotPetNamePredicate;
import clzzz.helper.testutil.slot.TypicalSlots;
import org.junit.jupiter.api.Test;


class FindSlotCommandTest {

    private Model model = new ModelManager(getTypicalPetTrackerWithSlots(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalPetTrackerWithSlots(), new UserPrefs());

    @Test
    public void equals() {
        Predicate<Slot> firstPredicate =
                new SlotPetNamePredicate(Arrays.asList("Coco"));
        Predicate<Slot> secondPredicate =
                new SlotPetNamePredicate(Arrays.asList("Garfield"));

        FindSlotCommand findFirstCommand = new FindSlotCommand(firstPredicate, "");
        FindSlotCommand findSecondCommand = new FindSlotCommand(secondPredicate, "");

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // same values -> returns true
        FindSlotCommand findFirstCommandCopy = new FindSlotCommand(firstPredicate, "");
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_throwParseException() throws ParseException {
        String expectedMessage = FindSlotCommand.MESSAGE_EMPTY_NAME_FIELD;
        FindSlotCommandParser parser = new FindSlotCommandParser();
        CommandParserTestUtil.assertParseFailure(parser, " " + PREFIX_NAME, expectedMessage);
    }

    @Test
    public void execute_multipleKeywords_multipleSlotsFound() throws ParseException {
        Predicate<Slot> predicate = FindSlotCommandParser.getPredicates(NAME_DESC_COCO + " " + NAME_DESC_GARFIELD);
        expectedModel.updateFilteredSlotList(predicate);
        assertEquals(TypicalSlots.getTypicalSlots(), model.getFilteredSlotList());
    }

    @Test
    public void execute_partialKeyWords_multipleSlotsFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_SLOTS_LISTED_OVERVIEW, 2);
        Predicate<Slot> predicate = FindSlotCommandParser.getPredicates(" " + PREFIX_NAME + "CO garf");
        FindSlotCommand command = new FindSlotCommand(predicate, "");
        expectedModel.updateFilteredSlotList(predicate);
        //TODO:later add assertFindCommandSuccess after merging stats pr
        assertEquals(TypicalSlots.getTypicalSlots(), model.getFilteredSlotList());
    }
}
