package w154.helper.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import w154.helper.commons.core.GuiSettings;
import w154.helper.logic.commands.general.CommandResult;
import w154.helper.logic.commands.general.exceptions.CommandException;
import w154.helper.logic.parser.general.exceptions.ParseException;
import w154.helper.model.ReadOnlyPetTracker;
import w154.helper.model.pet.FoodCollection;
import w154.helper.model.pet.Pet;
import w154.helper.model.slot.Slot;
import w154.helper.ui.DisplaySystemType;
import w154.helper.ui.list.DisplayItem;

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
    CommandResult execute(String commandText) throws CommandException, ParseException;

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
