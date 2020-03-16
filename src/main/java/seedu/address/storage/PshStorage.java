package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyPetTracker;
import seedu.address.model.PshReadOnlyUserPrefs;
import seedu.address.model.PshUserPrefs;

/**
 * API of the Storage component
 */
public interface PshStorage extends PetTrackerStorage, PshUserPrefsStorage {

    @Override
    Optional<PshUserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(PshReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getPetTrackerFilePath();

    @Override
    Optional<ReadOnlyPetTracker> readPetTracker() throws DataConversionException, IOException;

    @Override
    void savePetTracker(ReadOnlyPetTracker petTracker) throws IOException;

}
