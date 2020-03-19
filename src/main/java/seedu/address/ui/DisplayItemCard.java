package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code DisplayItem}.
 */
public abstract class DisplayItemCard extends UiPart<Region> {

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public DisplayItemCard(String fxml) {
        super(fxml);
    }

    public abstract boolean equals(Object other);
}
