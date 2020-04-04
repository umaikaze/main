package clzzz.helper.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import clzzz.helper.commons.exceptions.DataConversionException;
import clzzz.helper.model.ReadOnlyPetTracker;
import clzzz.helper.model.ReadOnlyUserPrefs;
import clzzz.helper.model.UserPrefs;

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
