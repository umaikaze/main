package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefsPet implements ReadOnlyUserPrefsPet {

    private GuiSettings guiSettings = new GuiSettings();
    private Path petStoreHelperFilePath = Paths.get("data" , "addressbook.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefsPet() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefsPet(ReadOnlyUserPrefsPet userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefsPet newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setPetStoreHelperFilePath(newUserPrefs.getPetStoreHelperFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getPetStoreHelperFilePath() {
        return petStoreHelperFilePath;
    }

    public void setPetStoreHelperFilePath(Path petStoreHelperFilePath) {
        requireNonNull(petStoreHelperFilePath);
        this.petStoreHelperFilePath = petStoreHelperFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefsPet)) { //this handles null as well.
            return false;
        }

        UserPrefsPet o = (UserPrefsPet) other;

        return guiSettings.equals(o.petStoreHelperFilePath)
                && petStoreHelperFilePath.equals(o.petStoreHelperFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, petStoreHelperFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + petStoreHelperFilePath);
        return sb.toString();
    }

}
