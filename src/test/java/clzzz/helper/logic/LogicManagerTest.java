package clzzz.helper.logic;

import static clzzz.helper.commons.core.Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX;
import static clzzz.helper.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static clzzz.helper.logic.commands.CommandTestUtil.DOB_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.FOOD_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.GENDER_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.NAME_DESC_COCO;
import static clzzz.helper.logic.commands.CommandTestUtil.SPECIES_DESC_COCO;
import static clzzz.helper.testutil.Assert.assertThrows;
import static clzzz.helper.testutil.pet.TypicalPets.COCO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import clzzz.helper.commons.exceptions.IllegalValueException;

import clzzz.helper.logic.commands.CommandResult;
import clzzz.helper.logic.commands.StatsCommand;
import clzzz.helper.logic.commands.exceptions.CommandException;
import clzzz.helper.logic.commands.pet.AddPetCommand;
import clzzz.helper.logic.parser.exceptions.ParseException;
import clzzz.helper.model.Model;
import clzzz.helper.model.ModelManager;
import clzzz.helper.model.ReadOnlyPetTracker;
import clzzz.helper.model.UserPrefs;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.storage.JsonPetTrackerStorage;
import clzzz.helper.storage.JsonUserPrefsStorage;
import clzzz.helper.storage.StorageManager;
import clzzz.helper.testutil.pet.PetBuilder;

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
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "deletepet 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PET_DISPLAYED_INDEX);
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
        String addCommand = AddPetCommand.COMMAND_WORD + NAME_DESC_COCO + GENDER_DESC_COCO + DOB_DESC_COCO
                + SPECIES_DESC_COCO + FOOD_DESC_COCO;
        Pet expectedPet = new PetBuilder(COCO).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPet(expectedPet);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredDisplayList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredDisplayList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, IllegalValueException {
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
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
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
