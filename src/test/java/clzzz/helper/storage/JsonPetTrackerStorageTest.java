package clzzz.helper.storage;

import static clzzz.helper.testutil.Assert.assertThrows;
import static clzzz.helper.testutil.pet.TypicalPets.AMY;
import static clzzz.helper.testutil.pet.TypicalPets.HOON;
import static clzzz.helper.testutil.pet.TypicalPets.IDA;
import static clzzz.helper.testutil.pet.TypicalPets.getTypicalPetTracker;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import clzzz.helper.commons.exceptions.DataConversionException;
import clzzz.helper.model.PetTracker;
import clzzz.helper.model.ReadOnlyPetTracker;

public class JsonPetTrackerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonPetTrackerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readPetTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPetTracker(null));
    }

    private java.util.Optional<ReadOnlyPetTracker> readPetTracker(String filePath) throws Exception {
        return new JsonPetTrackerStorage(Paths.get(filePath)).readPetTracker(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPetTracker("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readPetTracker("notJsonFormatPetTracker.json"));
    }

    @Test
    public void readPetTracker_invalidPetTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readPetTracker("invalidPetTracker.json"));
    }

    @Test
    public void readPetTracker_invalidAndValidPetTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readPetTracker("invalidAndValidPetTracker.json"));
    }

    @Test
    public void readAndSavePetTracker_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempPetTracker.json");
        PetTracker original = getTypicalPetTracker();
        JsonPetTrackerStorage jsonPetTrackerStorage = new JsonPetTrackerStorage(filePath);

        // Save in new file and read back
        jsonPetTrackerStorage.savePetTracker(original, filePath);
        ReadOnlyPetTracker readBack = jsonPetTrackerStorage.readPetTracker(filePath).get();
        assertEquals(original, new PetTracker(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPet(HOON);
        original.removePet(AMY);
        jsonPetTrackerStorage.savePetTracker(original, filePath);
        readBack = jsonPetTrackerStorage.readPetTracker(filePath).get();
        assertEquals(original, new PetTracker(readBack));

        // Save and read without specifying file path
        original.addPet(IDA);
        jsonPetTrackerStorage.savePetTracker(original); // file path not specified
        readBack = jsonPetTrackerStorage.readPetTracker().get(); // file path not specified
        assertEquals(original, new PetTracker(readBack));

    }

    @Test
    public void savePetTracker_nullPetTracker_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePetTracker(null, "SomeFile.json"));
    }

    /**
     * Saves {@code petTracker} at the specified {@code filePath}.
     */
    private void savePetTracker(ReadOnlyPetTracker petTracker, String filePath) {
        try {
            new JsonPetTrackerStorage(Paths.get(filePath))
                    .savePetTracker(petTracker, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void savePetTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePetTracker(new PetTracker(), null));
    }
}
