package w154.helper.model;

import java.nio.file.Path;

import w154.helper.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {
    GuiSettings getGuiSettings();

    Path getPetTrackerFilePath();
}

