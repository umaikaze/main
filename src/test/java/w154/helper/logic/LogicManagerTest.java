package w154.helper.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static w154.helper.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import w154.helper.logic.commands.general.CommandResult;
import w154.helper.logic.commands.general.StatsCommand;
import w154.helper.logic.commands.general.exceptions.CommandException;
import w154.helper.logic.commands.pet.AddPetCommand;
import w154.helper.logic.parser.general.exceptions.ParseException;
import w154.helper.model.Model;
import w154.helper.model.ModelManager;
import w154.helper.model.ReadOnlyPetTracker;
import w154.helper.model.UserPrefs;
import w154.helper.model.pet.Pet;
import w154.helper.storage.JsonPetTrackerStorage;
import w154.helper.storage.JsonUserPrefsStorage;
import w154.helper.storage.StorageManager;
import w154.helper.testutil.pet.PetBuilder;
import w154.helper.commons.core.Messages;
import w154.helper.logic.commands.CommandTestUtil;
import w154.helper.testutil.Assert;
import w154.helper.testutil.pet.TypicalPets;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonPetTrackerStorage petTrackerStorage =
                new JsonPetTrackerStorage(temporaryFolder.resolve("petTracker.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(petTrackerStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "deletepet 9";
        assertCommandException(deleteCommand, Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String statsCommand = StatsCommand.COMMAND_WORD;
        assertCommandSuccess(statsCommand, StatsCommand.MESSAGE_SUCCESS, model);
    }


    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonPetTrackerIoExceptionThrowingStub
        JsonPetTrackerStorage petTrackerStorage =
                new JsonPetTrackerIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionPetTracker.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(petTrackerStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddPetCommand.COMMAND_WORD + CommandTestUtil.NAME_DESC_COCO + CommandTestUtil.GENDER_DESC_COCO + CommandTestUtil.DOB_DESC_COCO
                + CommandTestUtil.SPECIES_DESC_COCO + CommandTestUtil.FOOD_DESC_COCO;
        Pet expectedPet = new PetBuilder(TypicalPets.COCO).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPet(expectedPet);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredDisplayList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredDisplayList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getPetTracker(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        Assert.assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonPetTrackerIoExceptionThrowingStub extends JsonPetTrackerStorage {
        private JsonPetTrackerIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void savePetTracker(ReadOnlyPetTracker petTracker, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
