package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyPetTracker;

/**
 * Represents a storage for {@link seedu.address.model.PetTracker}.
 */
public interface PetTrackerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPetTrackerFilePath();

    /**
     * Returns PetTracker data as a {@link ReadOnlyPetTracker}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyPetTracker> readPetTracker() throws DataConversionException, IOException;

    /**
     * @see #getPetTrackerFilePath()
     */
    Optional<ReadOnlyPetTracker> readPetTracker(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyPetTracker} to the storage.
     * @param petTracker cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePetTracker(ReadOnlyPetTracker petTracker) throws IOException;

    /**
     * @see #savePetTracker(ReadOnlyPetTracker)
     */
    void savePetTracker(ReadOnlyPetTracker petTracker, Path filePath) throws IOException;

}
