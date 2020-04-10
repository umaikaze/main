package clzzz.helper.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PET_DISPLAYED_INDEX = "The pet index provided is invalid. "
            + "It is larger than the number of showing pets.";
    public static final String MESSAGE_INVALID_SLOT_DISPLAYED_INDEX = "The slot index provided is invalid. "
            + "It is larger than the number of showing slots.";
    public static final String MESSAGE_PETS_LISTED_OVERVIEW = "%1$d pets listed!\n";
    public static final String MESSAGE_SLOTS_LISTED_OVERVIEW = "%1$d slots listed!\n";
    public static final String MESSAGE_SLOT_NOT_IN_ONE_DAY = "Slot does not start and end on the same day!";
    public static final String MESSAGE_INVALID_DATE = "The date entered is not a valid date.\n";
    public static final String MESSAGE_INVALID_TIME = "The time entered is not a valid time.\n";
    public static final String MESSAGE_INVALID_DATE_TIME = "The date and time entered are not valid.\n";


    public static final String WARNING_MESSAGE_DATE_TOO_EARLY = "Warning: The date entered might be too early.\n";
    public static final String WARNING_MESSAGE_DATE_TOO_LATE = "Warning: The date entered might be too late.\n";


    public static final String WARNING_MESSAGE_END = "Only the last one will be accepted.\n";
    public static final String WARNING_MESSAGE_NAME = "Warning: You have entered more than 1 entries for "
            + "name field n/." + " " + WARNING_MESSAGE_END;
    public static final String WARNING_MESSAGE_GENDER = "Warning: You have entered more than 1 entries for "
            + "gender field g/." + " " + WARNING_MESSAGE_END;
    public static final String WARNING_MESSAGE_SPECIES = "Warning: You have entered more than 1 entries for "
            + "species field s/." + " " + WARNING_MESSAGE_END;
    public static final String WARNING_MESSAGE_DOB = "Warning: You have entered more than 1 entries for "
            + "date of birth field b/." + " " + WARNING_MESSAGE_END;
    public static final String WARNING_MESSAGE_DATETIME = "Warning: You have entered more than 1 entries for "
            + "datetime field t/." + " " + WARNING_MESSAGE_END;
    public static final String WARNING_MESSAGE_DURATION = "Warning: You have entered more than 1 entries for "
            + "duration field d/." + " " + WARNING_MESSAGE_END;
}
