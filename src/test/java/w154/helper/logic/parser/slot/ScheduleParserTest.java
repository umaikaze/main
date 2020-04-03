package w154.helper.logic.parser.slot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static w154.helper.logic.parser.general.CliSyntax.PREFIX_NAME;
import static w154.helper.testutil.Assert.assertThrows;
import static w154.helper.testutil.TypicalIndexes.INDEX_FIRST_SLOT;
import static w154.helper.testutil.pet.TypicalPets.getTypicalPetTrackerWithSlots;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import w154.helper.logic.commands.slot.AddSlotCommand;
import w154.helper.logic.commands.slot.DeleteSlotCommand;
import w154.helper.logic.commands.slot.EditSlotCommand;
import w154.helper.logic.commands.slot.FindSlotCommand;
import w154.helper.logic.parser.general.PetTrackerParser;
import w154.helper.logic.parser.general.exceptions.ParseException;
import w154.helper.model.Model;
import w154.helper.model.ModelManager;
import w154.helper.model.UserPrefs;
import w154.helper.model.slot.Slot;
import w154.helper.storage.JsonPetTrackerStorage;
import w154.helper.storage.JsonUserPrefsStorage;
import w154.helper.storage.StorageManager;
import w154.helper.testutil.slot.EditSlotDescriptorBuilder;
import w154.helper.testutil.slot.SlotBuilder;
import w154.helper.testutil.slot.SlotUtil;
import w154.helper.commons.core.Messages;

class ScheduleParserTest {
    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager(getTypicalPetTrackerWithSlots(), new UserPrefs());
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
                DeleteSlotCommand.COMMAND_WORD + " " + INDEX_FIRST_SLOT.getOneBased());
        assertEquals(new DeleteSlotCommand(INDEX_FIRST_SLOT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Slot slot = new SlotBuilder().build();
        EditSlotCommand.EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder(slot).build();
        EditSlotCommand command = (EditSlotCommand) parser.parseCommand(EditSlotCommand.COMMAND_WORD + " "
                + INDEX_FIRST_SLOT.getOneBased() + " " + SlotUtil.getEditSlotDescriptorDetails(descriptor));
        assertEquals(new EditSlotCommand(INDEX_FIRST_SLOT, descriptor, ""), command);
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
        assertThrows(ParseException.class, Messages.MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
