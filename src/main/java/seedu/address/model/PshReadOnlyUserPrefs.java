package seedu.address.model;

import seedu.address.commons.core.GuiSettings;

import java.nio.file.Path;

public interface PshReadOnlyUserPrefs {
    GuiSettings getGuiSettings();

    Path getPetTrackerFilePath();
}

