package clzzz.helper.storage;

import static clzzz.helper.commons.util.DateTimeUtil.BACK_UP_FORMAT;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

import clzzz.helper.commons.core.LogsCenter;
import clzzz.helper.commons.exceptions.DataConversionException;
import clzzz.helper.commons.exceptions.IllegalValueException;
import clzzz.helper.commons.util.FileUtil;
import clzzz.helper.commons.util.JsonUtil;
import clzzz.helper.model.ReadOnlyPetTracker;

/**
 * A class to access PetTracker data stored as a json file on the hard disk.
 */
public class JsonPetTrackerStorage implements PetTrackerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPetTrackerStorage.class);

    private Path filePath;

    public JsonPetTrackerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPetTrackerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPetTracker> readPetTracker() throws DataConversionException {
        return readPetTracker(filePath);
    }

    /**
     * Similar to {@link #readPetTracker()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyPetTracker> readPetTracker(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializablePetTracker> jsonPetTracker = JsonUtil.readJsonFile(
                filePath, JsonSerializablePetTracker.class);
        if (!jsonPetTracker.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPetTracker.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void savePetTracker(ReadOnlyPetTracker petTracker) throws IOException {
        savePetTracker(petTracker, filePath);
    }

    @Override
    public void savePetTracker(ReadOnlyPetTracker petTracker, LocalDateTime timestamp) throws IOException {
        savePetTracker(petTracker, filePath.resolveSibling(timestamp.format(BACK_UP_FORMAT) + ".json"));
    }

    /**
     * Similar to {@link #savePetTracker(ReadOnlyPetTracker)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePetTracker(ReadOnlyPetTracker petTracker, Path filePath) throws IOException {
        requireNonNull(petTracker);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePetTracker(petTracker), filePath);
    }

}
