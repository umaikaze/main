package seedu.address.model;

import seedu.address.commons.core.GuiSettings;

import java.nio.file.Path;

public interface GeneralModel {
    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getPetStoreHelperFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setPetStoreHelperFilePath(Path petStoreHelperFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setPetStoreHelper(ReadOnlyPetStoreHelper petStoreHelper);

    /** Returns the AddressBook */
    ReadOnlyPetStoreHelper getPetStoreHelper();

}
