package clzzz.helper.ui.list;

import clzzz.helper.ui.DisplaySystemType;

/**
 * An item to be displayed in a list in the Pet Store Helper.
 *
 * Any class that is intended to be displayed as an item in a list in the Pet
 * Store Helper should implement this interface.
 */
public interface DisplayItem {
    /**
     * Returns the type of system that this item constitutes of.
     */
    DisplaySystemType getDisplaySystemType();
}
