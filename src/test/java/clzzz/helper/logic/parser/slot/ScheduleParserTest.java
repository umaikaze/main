package clzzz.helper.logic.parser.slot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static clzzz.helper.logic.parser.general.CliSyntax.PREFIX_NAME;
import static clzzz.helper.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import clzzz.helper.testutil.Assert;
import clzzz.helper.testutil.TypicalIndexes;
import clzzz.helper.testutil.pet.TypicalPets;
import clzzz.helper.testutil.slot.EditSlotDescriptorBuilder;
import clzzz.helper.testutil.slot.SlotBuilder;
import clzzz.helper.testutil.slot.SlotUtil;
import clzzz.helper.logic.commands.slot.AddSlotCommand;
import clzzz.helper.logic.commands.slot.DeleteSlotCommand;
import clzzz.helper.logic.commands.slot.EditSlotCommand;
import clzzz.helper.logic.commands.slot.FindSlotCommand;
import clzzz.helper.logic.parser.general.PetTrackerParser;
import clzzz.helper.logic.parser.general.exceptions.ParseException;
import clzzz.helper.model.Model;
import clzzz.helper.model.ModelManager;
import clzzz.helper.model.UserPrefs;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.storage.JsonPetTrackerStorage;
import clzzz.helper.storage.JsonUserPrefsStorage;
import clzzz.helper.storage.StorageManager;
import clzzz.helper.commons.core.Messages;

class ScheduleParserTest {
    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager(TypicalPets.getTypicalPetTrackerWithSlots(), new UserPrefs());
    private PetTrackerParser parser;

    @BeforeEach
    public void setUp() {
        JsonPetTrackerStorage petTrackerStorage =
                new JsonPetTrackerStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(petTrackerStorage, userPrefsStorage);
        parser = new PetTrackerParser(model, storage);
    }


    @Test
    public void parseCommand_add() throws Exception {
        Slot slot = new SlotBuilder().build();
        AddSlotCommand command = (AddSlotCommand) parser.parseCommand(SlotUtil.getAddSlotCommand(slot));
        assertEquals(new AddSlotCommand(slot, ""), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteSlotCommand command = (DeleteSlotCommand) parser.parseCommand(
                DeleteSlotCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST_SLOT.getOneBased());
        assertEquals(new DeleteSlotCommand(TypicalIndexes.INDEX_FIRST_SLOT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Slot slot = new SlotBuilder().build();
        EditSlotCommand.EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder(slot).build();
        EditSlotCommand command = (EditSlotCommand) parser.parseCommand(EditSlotCommand.COMMAND_WORD + " "
                + TypicalIndexes.INDEX_FIRST_SLOT.getOneBased() + " " + SlotUtil.getEditSlotDescriptorDetails(descriptor));
        assertEquals(new EditSlotCommand(TypicalIndexes.INDEX_FIRST_SLOT, descriptor, ""), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        StringBuilder sb = new StringBuilder();
        keywords.forEach(x -> sb.append(PREFIX_NAME).append(x).append(" "));
        FindSlotCommand command = (FindSlotCommand) parser.parseCommand(
                FindSlotCommand.COMMAND_WORD + " " + sb.toString());
        assertEquals(new FindSlotCommand(FindSlotCommandParser.getPredicates(sb.toString()), Messages.WARNING_MESSAGE_NAME), command);
    }

    // Test for help already done in PetTrackerParserTest

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        Assert.assertThrows(ParseException.class, Messages.MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
