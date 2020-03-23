package seedu.address.ui;

/**
 * Represents the type of system to be shown in the display board in the Pet Store Helper.
 */
public enum DisplaySystemType {
    PETS("p"),
    SCHEDULE("s");

    public static final String MESSAGE_CONSTRAINTS =
            "System type must be p (pets) or s (schedule).";

    private final String cliArg;

    DisplaySystemType(String cliArg) {
        this.cliArg = cliArg;
    }

    /**
     * Returns the corresponding {@code DisplaySystemType} based on the {@code cliArg} passed in.
     */
    public static DisplaySystemType fromCliArg(String cliArg) throws IllegalArgumentException {
        for (DisplaySystemType type : DisplaySystemType.values()) {
            if (type.cliArg.equals(cliArg)) {
                return type;
            }
        }
        throw new IllegalArgumentException(cliArg);
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
