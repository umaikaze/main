package clzzz.helper.model;

import java.nio.file.Path;

import clzzz.helper.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {
    GuiSettings getGuiSettings();

    Path getPetTrackerFilePath();
}

