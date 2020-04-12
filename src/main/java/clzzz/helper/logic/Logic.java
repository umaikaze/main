package clzzz.helper.logic;

import java.nio.file.Path;

import clzzz.helper.commons.core.GuiSettings;
import clzzz.helper.commons.exceptions.IllegalValueException;
import clzzz.helper.logic.commands.CommandResult;
import clzzz.helper.logic.commands.exceptions.CommandException;
import clzzz.helper.logic.parser.exceptions.ParseException;
import clzzz.helper.model.ReadOnlyPetTracker;
import clzzz.helper.model.foodcollection.FoodCollection;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.ui.DisplaySystemType;
import clzzz.helper.ui.list.DisplayItem;
import javafx.collections.ObservableList;

/**
 * API of the Logic component for Pet Store Helper
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, IllegalValueException;

    /**
     * Returns the PetTracker.
     */
    ReadOnlyPetTracker getPetTracker();

    /**
     * Returns an unmodifiable view of the filtered list to be displayed.
     */
    ObservableList<DisplayItem> getFilteredDisplayList();

    ObservableList<Pet> getFilteredPetList();

    ObservableList<Slot> getFilteredSlotList();

    ObservableList<FoodCollection> getFilteredFoodCollectionList();

    DisplaySystemType getDisplaySystemType();

    /**
     * Returns the user prefs' pet tracker file path.
     */
    Path getPetTrackerFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
