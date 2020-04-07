package clzzz.helper.logic.commands.slot;

import static clzzz.helper.commons.core.Messages.MESSAGE_SLOTS_LISTED_OVERVIEW;
import static clzzz.helper.logic.commands.CommandTestUtil.NAME_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.NAME_DESC_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.VALID_NAME_GARFIELD;
import static clzzz.helper.logic.commands.CommandTestUtil.assertFindCommandSuccess;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_NAME;
import static clzzz.helper.testutil.pet.TypicalPets.getTypicalPetTrackerWithSlots;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import clzzz.helper.commons.core.Messages;
import clzzz.helper.commons.exceptions.IllegalValueException;
import clzzz.helper.logic.parser.CommandParserTestUtil;
import clzzz.helper.logic.parser.exceptions.ParseException;
import clzzz.helper.logic.parser.slot.FindSlotCommandParser;
import clzzz.helper.model.Model;
import clzzz.helper.model.ModelManager;
import clzzz.helper.model.UserPrefs;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.model.slot.SlotPetNamePredicate;
import clzzz.helper.testutil.slot.TypicalSlots;
import clzzz.helper.ui.DisplaySystemType;

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
        assertEquals(findFirstCommand, findFirstCommand);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // same values -> returns true
        FindSlotCommand findFirstCommandCopy = new FindSlotCommand(firstPredicate, "");
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different person -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_zeroKeywords_throwParseException() throws ParseException {
        String expectedMessage = FindSlotCommand.MESSAGE_EMPTY_NAME_FIELD;
        FindSlotCommandParser parser = new FindSlotCommandParser();
        CommandParserTestUtil.assertParseFailure(parser, " " + PREFIX_NAME, expectedMessage);
    }

    @Test
    public void execute_multiplePrefixes_oneSlotFound() throws IllegalValueException {
        Predicate<Slot> predicate = FindSlotCommandParser.getPredicates(NAME_DESC_COCO + " " + NAME_DESC_GARFIELD);
        FindSlotCommand command = new FindSlotCommand(predicate, "");
        String expectedMessage = String.format(Messages.MESSAGE_SLOTS_LISTED_OVERVIEW, 1);
        expectedModel.updateFilteredSlotList(predicate);
        expectedModel.changeDisplaySystem(DisplaySystemType.SCHEDULE);
        assertEquals(Arrays.asList(TypicalSlots.GARFIELD_SLOT), expectedModel.getFilteredSlotList());
        assertFindCommandSuccess(command, model, expectedMessage, expectedModel, DisplaySystemType.SCHEDULE);
    }

    @Test
    public void execute_multipleNames_multipleSlotsFound() throws IllegalValueException {
        Predicate<Slot> predicate = FindSlotCommandParser.getPredicates(NAME_DESC_COCO + " " + VALID_NAME_GARFIELD);
        FindSlotCommand command = new FindSlotCommand(predicate, "");
        String expectedMessage = String.format(Messages.MESSAGE_SLOTS_LISTED_OVERVIEW, 2);
        expectedModel.updateFilteredSlotList(predicate);
        expectedModel.changeDisplaySystem(DisplaySystemType.SCHEDULE);
        assertEquals(TypicalSlots.getTypicalSlots(), expectedModel.getFilteredSlotList());
        assertFindCommandSuccess(command, model, expectedMessage, expectedModel, DisplaySystemType.SCHEDULE);
    }

    @Test
    public void execute_partialKeyWords_multipleSlotsFound() throws IllegalValueException {
        String expectedMessage = String.format(MESSAGE_SLOTS_LISTED_OVERVIEW, 2);
        Predicate<Slot> predicate = FindSlotCommandParser.getPredicates(" " + PREFIX_NAME + "CO garf");
        FindSlotCommand command = new FindSlotCommand(predicate, "");
        expectedModel.updateFilteredSlotList(predicate);
        expectedModel.changeDisplaySystem(DisplaySystemType.SCHEDULE);
        assertFindCommandSuccess(command, model, expectedMessage, expectedModel, DisplaySystemType.SCHEDULE);
        assertEquals(TypicalSlots.getTypicalSlots(), model.getFilteredSlotList());
    }
}
