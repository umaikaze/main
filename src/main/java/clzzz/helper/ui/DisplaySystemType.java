package clzzz.helper.ui;

/**
 * Represents the type of system to be shown in the display board in the Pet Store Helper.
 */
public enum DisplaySystemType {
    PETS("p"),
    SCHEDULE("s"),
    INVENTORY("i"),
    CALENDAR("c"),
    FOOD_AMOUNT_AND_PET("fp"),
    STATISTICS("stats"),
    NO_CHANGE(DisplaySystemType.UNUSED_CLI_ARG);

    public static final String MESSAGE_CONSTRAINTS =
            "System type must be p (pets), s (schedule), c (calendar) or i(inventory).";

    private static final String UNUSED_CLI_ARG = "unused";

    private final String cliArg;

    DisplaySystemType(String cliArg) {
        this.cliArg = cliArg;
    }

    /**
     * Returns the corresponding {@code DisplaySystemType} based on the {@code cliArg} passed in.
     */
    public static DisplaySystemType fromCliArg(String cliArg) throws IllegalArgumentException {
        for (DisplaySystemType type : DisplaySystemType.values()) {
            if (type.isAvailableToUser() && type.cliArg.equals(cliArg)) {
                return type;
            }
        }
        throw new IllegalArgumentException(cliArg);
    }

    private boolean isAvailableToUser() {
        return !cliArg.equals(UNUSED_CLI_ARG);
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
