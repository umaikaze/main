package clzzz.helper.model.pet;

import static clzzz.helper.commons.util.AppUtil.checkArgument;

/**
 * Represents a Pet's gender in the pet store helper.
 * Guarantees: Only two valid constants: FEMALE and MALE.
 */
public enum Gender {
    FEMALE ("FEMALE"),
    MALE ("MALE");

    public static final String MESSAGE_CONSTRAINTS = "Gender should be either male or female";

    private String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param gender A valid gender.
     */
    Gender(String gender) {
        checkArgument(isValidGender(gender), MESSAGE_CONSTRAINTS);
        this.value = gender;
    }

    public static boolean isValidGender(String test) {
        return (test.equalsIgnoreCase("MALE")) || (test.equalsIgnoreCase("FEMALE"));
    }

    @Override
    public String toString() {
        return value.substring(0, 1) + value.substring(1).toLowerCase();
    }

}
