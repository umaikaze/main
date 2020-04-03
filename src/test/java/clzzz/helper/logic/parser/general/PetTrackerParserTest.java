package clzzz.helper.logic.parser.general;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static clzzz.helper.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import clzzz.helper.testutil.Assert;
import clzzz.helper.testutil.TypicalIndexes;
import clzzz.helper.testutil.pet.EditPetDescriptorBuilder;
import clzzz.helper.testutil.pet.PetBuilder;
import clzzz.helper.testutil.pet.PetUtil;
import clzzz.helper.testutil.pet.TypicalPets;
import clzzz.helper.logic.commands.pet.AddPetCommand;
import clzzz.helper.logic.commands.pet.DeletePetCommand;
import clzzz.helper.logic.commands.pet.EditPetCommand;
import clzzz.helper.logic.commands.pet.FindPetCommand;
import clzzz.helper.logic.parser.general.exceptions.ParseException;
import clzzz.helper.model.Model;
import clzzz.helper.model.ModelManager;
import clzzz.helper.model.UserPrefs;
import clzzz.helper.model.pet.NameContainsKeywordsPredicate;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.storage.JsonPetTrackerStorage;
import clzzz.helper.storage.JsonUserPrefsStorage;
import clzzz.helper.storage.StorageManager;
import clzzz.helper.commons.core.Messages;


public class PetTrackerParserTest {
    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager(TypicalPets.getTypicalPetTracker(), new UserPrefs());
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
                DeletePetCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST_PET.getOneBased());
        assertEquals(new DeletePetCommand(TypicalIndexes.INDEX_FIRST_PET), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Pet pet = new PetBuilder().build();
        EditPetCommand.EditPetDescriptor descriptor = new EditPetDescriptorBuilder(pet).build();
        EditPetCommand command = (EditPetCommand) parser.parseCommand(EditPetCommand.COMMAND_WORD + " "
                + TypicalIndexes.INDEX_FIRST_PET.getOneBased() + " " + PetUtil.getEditPetDescriptorDetails(descriptor));
        assertEquals(new EditPetCommand(TypicalIndexes.INDEX_FIRST_PET, descriptor, ""), command);
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
        Assert.assertThrows(ParseException.class, Messages.MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
