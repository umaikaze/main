package clzzz.helper.storage;

import static clzzz.helper.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import clzzz.helper.commons.exceptions.IllegalValueException;
import clzzz.helper.commons.util.JsonUtil;
import clzzz.helper.model.PetTracker;
import clzzz.helper.testutil.pet.TypicalPets;

public class JsonSerializablePetTrackerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializablePetTrackerTest");
    private static final Path TYPICAL_PETS_FILE = TEST_DATA_FOLDER.resolve("typicalPetTracker.json");
    private static final Path INVALID_PET_FILE = TEST_DATA_FOLDER.resolve("invalidPetTracker.json");
    private static final Path DUPLICATE_PET_FILE = TEST_DATA_FOLDER.resolve("duplicatePetTracker.json");
    private static final Path TYPICAL_SLOTS_FILE = TEST_DATA_FOLDER.resolve("typicalPetTrackerWithSlots.json");
    private static final Path INVALID_SLOT_FILE = TEST_DATA_FOLDER.resolve("invalidPetTrackerWithSlots.json");

    @Test
    public void toModelType_typicalPetsFile_success() throws Exception {
        JsonSerializablePetTracker dataFromFile = JsonUtil.readJsonFile(TYPICAL_PETS_FILE,
                JsonSerializablePetTracker.class).get();
        PetTracker petTrackerFromFile = dataFromFile.toModelType();
        PetTracker typicalPetTracker = TypicalPets.getTypicalPetTracker();
        assertEquals(petTrackerFromFile, typicalPetTracker);
    }

    @Test
    public void toModelType_invalidPetFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePetTracker dataFromFile = JsonUtil.readJsonFile(INVALID_PET_FILE,
                JsonSerializablePetTracker.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePets_throwsIllegalValueException() throws Exception {
        JsonSerializablePetTracker dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PET_FILE,
                JsonSerializablePetTracker.class).get();
        assertThrows(IllegalValueException.class, JsonSerializablePetTracker.MESSAGE_DUPLICATE_PET,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalSlotsFile_success() throws Exception {
        JsonSerializablePetTracker dataFromFile = JsonUtil.readJsonFile(TYPICAL_SLOTS_FILE,
                JsonSerializablePetTracker.class).get();
        PetTracker petTrackerFromFile = dataFromFile.toModelType();
        PetTracker typicalPetTracker = TypicalPets.getTypicalPetTracker();
        assertEquals(petTrackerFromFile, typicalPetTracker);
    }

    @Test
    public void toModelType_invalidSlotFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePetTracker dataFromFile = JsonUtil.readJsonFile(INVALID_SLOT_FILE,
                JsonSerializablePetTracker.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

}
