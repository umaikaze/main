package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.general.Command;
import seedu.address.logic.commands.general.CommandResult;
import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.logic.parser.pet.PetTrackerParser;
import seedu.address.model.PshModel;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyPetTracker;
import seedu.address.model.pet.Pet;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of Pet Store Helper.
 */
public class PshLogicManager implements PshLogic {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final PshModel model;
    private final Storage storage;
    private final PetTrackerParser petTrackerParser;

    public PshLogicManager(PshModel model, Storage storage) {
        this.model = model;
        this.storage = storage;
        petTrackerParser = new PetTrackerParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = petTrackerParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            //model.getPetTracker is ReadOnlyPetTracker,
            //here is casted to ReadOnlyAddressBook to pass checkstyle test
            storage.saveAddressBook((ReadOnlyAddressBook) model.getPetTracker());
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
    public ObservableList<Pet> getFilteredPetList() {
        return model.getFilteredPetList();
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
