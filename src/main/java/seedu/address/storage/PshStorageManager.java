package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyPetTracker;
import seedu.address.model.PshReadOnlyUserPrefs;
import seedu.address.model.PshUserPrefs;

/**
 * Manages storage of PetTracker data in local storage.
 */
public class PshStorageManager implements PshStorage {

    private static final Logger logger = LogsCenter.getLogger(PshStorageManager.class);
    private PetTrackerStorage petTrackerStorage;
    private PshUserPrefsStorage userPrefsStorage;


    public PshStorageManager(PetTrackerStorage petTrackerStorage, PshUserPrefsStorage userPrefsStorage) {
        super();
        this.petTrackerStorage = petTrackerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<PshUserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(PshReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ PetTracker methods ==============================

    @Override
    public Path getPetTrackerFilePath() {
        return petTrackerStorage.getPetTrackerFilePath();
    }

    @Override
    public Optional<ReadOnlyPetTracker> readPetTracker() throws DataConversionException, IOException {
        return readPetTracker(petTrackerStorage.getPetTrackerFilePath());
    }

    @Override
    public Optional<ReadOnlyPetTracker> readPetTracker(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return petTrackerStorage.readPetTracker(filePath);
    }

    @Override
    public void savePetTracker(ReadOnlyPetTracker petTracker) throws IOException {
        savePetTracker(petTracker, petTrackerStorage.getPetTrackerFilePath());
    }

    @Override
    public void savePetTracker(ReadOnlyPetTracker petTracker, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        petTrackerStorage.savePetTracker(petTracker, filePath);
    }

}
