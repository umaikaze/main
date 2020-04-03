package w154.helper.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import w154.helper.commons.core.GuiSettings;
import w154.helper.model.PetTracker;
import w154.helper.model.ReadOnlyPetTracker;
import w154.helper.model.UserPrefs;
import w154.helper.testutil.pet.TypicalPets;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonPetTrackerStorage petTrackerStorage = new JsonPetTrackerStorage(getTempFilePath("pt"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(petTrackerStorage, userPrefsStorage);
    }

    public Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void petTrackerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonPetTrackerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonPetTrackerStorageTest} class.
         */
        PetTracker original = TypicalPets.getTypicalPetTracker();
        storageManager.savePetTracker(original);
        ReadOnlyPetTracker retrieved = storageManager.readPetTracker().get();
        assertEquals(original, new PetTracker(retrieved));
    }

    @Test
    public void getPetTrackerFilePath() {
        assertNotNull(storageManager.getPetTrackerFilePath());
    }

}
