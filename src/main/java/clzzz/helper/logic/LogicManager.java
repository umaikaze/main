package clzzz.helper.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import clzzz.helper.commons.core.GuiSettings;
import clzzz.helper.commons.core.LogsCenter;
import clzzz.helper.commons.exceptions.IllegalValueException;
import clzzz.helper.logic.commands.Command;
import clzzz.helper.logic.commands.CommandResult;
import clzzz.helper.logic.commands.exceptions.CommandException;
import clzzz.helper.logic.parser.PetTrackerParser;
import clzzz.helper.model.Model;
import clzzz.helper.model.ReadOnlyPetTracker;
import clzzz.helper.model.foodcollection.FoodCollection;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.storage.Storage;
import clzzz.helper.ui.DisplaySystemType;
import clzzz.helper.ui.list.DisplayItem;
import javafx.collections.ObservableList;

/**
 * The main LogicManager of Pet Store Helper.
 */
public class LogicManager implements Logic {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final PetTrackerParser petTrackerParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        petTrackerParser = new PetTrackerParser(model, storage);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, IllegalValueException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = petTrackerParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.savePetTracker(model.getPetTracker());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyPetTracker getPetTracker() {
        return model.getPetTracker();
    }

    @Override
    public ObservableList<DisplayItem> getFilteredDisplayList() {
        return model.getFilteredDisplayList();
    }


    @Override
    public ObservableList<Pet> getFilteredPetList() {
        return model.getFilteredPetList();
    }

    @Override
    public ObservableList<Slot> getFilteredSlotList() {
        return model.getFilteredSlotList();
    }

    @Override
    public ObservableList<FoodCollection> getFilteredFoodCollectionList() {
        return model.getFilteredFoodCollectionList();
    }

    @Override
    public DisplaySystemType getDisplaySystemType() {
        return model.getCurrentDisplaySystemType();
    }

    @Override
    public Path getPetTrackerFilePath() {
        return model.getPetTrackerFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
