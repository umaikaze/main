package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PET_DISPLAYED_INDEX = "The pet index provided is invalid";
    public static final String MESSAGE_INVALID_SLOT_DISPLAYED_INDEX = "The slot index provided is invalid";
    public static final String MESSAGE_PETS_LISTED_OVERVIEW = "%1$d pets listed!\n";
    public static final String MESSAGE_SLOTS_LISTED_OVERVIEW = "%1$d slots listed!\n";
    public static final String MESSAGE_SLOT_NOT_IN_ONE_DAY = "Slot does not start and end on the same day!";


    public static final String WARNING_MESSAGE_END = "Only the last one will be accepted.\n";
    public static final String WARNING_MESSAGE_NAME = "Warning: You have entered more than 1 entries for "
            + "name field n/." + WARNING_MESSAGE_END;
    public static final String WARNING_MESSAGE_GENDER = "Warning: You have entered more than 1 entries for "
            + "gender field g/." + WARNING_MESSAGE_END;
    public static final String WARNING_MESSAGE_SPECIES = "Warning: You have entered more than 1 entries for "
            + "species field s/." + WARNING_MESSAGE_END;
    public static final String WARNING_MESSAGE_DOB = "Warning: You have entered more than 1 entries for "
            + "date of birth field b/." + WARNING_MESSAGE_END;
    public static final String WARNING_MESSAGE_TIME = "Warning: You have entered more than 1 entries for "
            + "time field t/." + WARNING_MESSAGE_END;
    public static final String WARNING_MESSAGE_DURATION = "Warning: You have entered more than 1 entries for "
            + "duration field d/." + WARNING_MESSAGE_END;
}
