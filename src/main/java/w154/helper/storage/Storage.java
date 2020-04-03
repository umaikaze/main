package w154.helper.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import w154.helper.commons.exceptions.DataConversionException;
import w154.helper.model.ReadOnlyPetTracker;
import w154.helper.model.ReadOnlyUserPrefs;
import w154.helper.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends PetTrackerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getPetTrackerFilePath();

    @Override
    Optional<ReadOnlyPetTracker> readPetTracker() throws DataConversionException, IOException;

    @Override
    void savePetTracker(ReadOnlyPetTracker petTracker) throws IOException;

}
