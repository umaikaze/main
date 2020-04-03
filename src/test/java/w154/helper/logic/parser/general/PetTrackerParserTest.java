package w154.helper.logic.parser.general;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static w154.helper.testutil.Assert.assertThrows;
import static w154.helper.testutil.TypicalIndexes.INDEX_FIRST_PET;
import static w154.helper.testutil.pet.TypicalPets.getTypicalPetTracker;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import w154.helper.logic.commands.pet.AddPetCommand;
import w154.helper.logic.commands.pet.DeletePetCommand;
import w154.helper.logic.commands.pet.EditPetCommand;
import w154.helper.logic.commands.pet.FindPetCommand;
import w154.helper.logic.parser.general.exceptions.ParseException;
import w154.helper.model.Model;
import w154.helper.model.ModelManager;
import w154.helper.model.UserPrefs;
import w154.helper.model.pet.NameContainsKeywordsPredicate;
import w154.helper.model.pet.Pet;
import w154.helper.storage.JsonPetTrackerStorage;
import w154.helper.storage.JsonUserPrefsStorage;
import w154.helper.storage.StorageManager;
import w154.helper.testutil.pet.EditPetDescriptorBuilder;
import w154.helper.testutil.pet.PetBuilder;
import w154.helper.testutil.pet.PetUtil;
import w154.helper.commons.core.Messages;


public class PetTrackerParserTest {
    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager(getTypicalPetTracker(), new UserPrefs());
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
        Pet pet = new PetBuilder().build();
        AddPetCommand command = (AddPetCommand) parser.parseCommand(PetUtil.getAddPetCommand(pet));
        assertEquals(new AddPetCommand(pet, ""), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeletePetCommand command = (DeletePetCommand) parser.parseCommand(
                DeletePetCommand.COMMAND_WORD + " " + INDEX_FIRST_PET.getOneBased());
        assertEquals(new DeletePetCommand(INDEX_FIRST_PET), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Pet pet = new PetBuilder().build();
        EditPetCommand.EditPetDescriptor descriptor = new EditPetDescriptorBuilder(pet).build();
        EditPetCommand command = (EditPetCommand) parser.parseCommand(EditPetCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PET.getOneBased() + " " + PetUtil.getEditPetDescriptorDetails(descriptor));
        assertEquals(new EditPetCommand(INDEX_FIRST_PET, descriptor, ""), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindPetCommand command = (FindPetCommand) parser.parseCommand(
                FindPetCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPetCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, Messages.MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
