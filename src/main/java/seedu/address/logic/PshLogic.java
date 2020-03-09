package seedu.address.logic;


import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.generalCommands.CommandResult;
import seedu.address.logic.generalCommands.exceptions.CommandException;
import seedu.address.logic.generalParser.exceptions.ParseException;
import seedu.address.model.ReadOnlyPetTracker;
import seedu.address.model.pet.Pet;

import java.nio.file.Path;


public interface PshLogic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyPetTracker getPetTracker();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Pet> getFilteredPetList();

    /**
     * Returns the user prefs' address book file path.
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
