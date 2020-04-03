package w154.helper.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

import w154.helper.commons.core.LogsCenter;
import w154.helper.commons.exceptions.DataConversionException;
import w154.helper.model.ReadOnlyPetTracker;
import w154.helper.model.ReadOnlyUserPrefs;
import w154.helper.model.UserPrefs;
import w154.helper.commons.util.DateTimeUtil;

/**
 * Manages storage of PetTracker data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private PetTrackerStorage petTrackerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(PetTrackerStorage petTrackerStorage, UserPrefsStorage userPrefsStorage) {
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
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
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
    public void savePetTracker(ReadOnlyPetTracker petTracker, LocalDateTime timestamp) throws IOException {
        savePetTracker(petTracker,
                petTrackerStorage.getPetTrackerFilePath().resolveSibling(timestamp.format(DateTimeUtil.BACK_UP_FORMAT) + ".json"));
    }

    @Override
    public void savePetTracker(ReadOnlyPetTracker petTracker, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        petTrackerStorage.savePetTracker(petTracker, filePath);
    }

}
