package seedu.address.model;

import seedu.address.commons.core.GuiSettings;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class PshUserPrefs implements PshReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path petTrackerFilePath = Paths.get("data" , "pettracker.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public PshUserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public PshUserPrefs(PshReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(PshReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setPetTrackerFilePath(newUserPrefs.getPetTrackerFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getPetTrackerFilePath() {
        return petTrackerFilePath;
    }

    public void setPetTrackerFilePath(Path petTrackerFilePath) {
        requireNonNull(petTrackerFilePath);
        this.petTrackerFilePath = petTrackerFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        PshUserPrefs o = (PshUserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && petTrackerFilePath.equals(o.petTrackerFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, petTrackerFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + petTrackerFilePath);
        return sb.toString();
    }

}
