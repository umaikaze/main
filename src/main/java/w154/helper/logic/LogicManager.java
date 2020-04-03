package w154.helper.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import w154.helper.commons.core.GuiSettings;
import w154.helper.commons.core.LogsCenter;
import w154.helper.logic.commands.general.Command;
import w154.helper.logic.commands.general.CommandResult;
import w154.helper.logic.commands.general.exceptions.CommandException;
import w154.helper.logic.parser.general.PetTrackerParser;
import w154.helper.logic.parser.general.exceptions.ParseException;
import w154.helper.model.Model;
import w154.helper.model.ReadOnlyPetTracker;
import w154.helper.model.pet.FoodCollection;
import w154.helper.model.pet.Pet;
import w154.helper.model.slot.Slot;
import w154.helper.storage.Storage;
import w154.helper.ui.DisplaySystemType;
import w154.helper.ui.list.DisplayItem;

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
    public CommandResult execute(String commandText) throws CommandException, ParseException {
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
