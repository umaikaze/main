package seedu.address.model.pet;

/**
 * Represents a Pet's gender in the pet shop helper.
 * Guarantees: Only two valid constants: FEMALE and MALE.
 */
public enum Gender {
    FEMALE ("female"),
    MALE ("male");

    private String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param gender A valid gender.
     */
    Gender(String gender) {
        this.value = gender;
    }

    @Override
    public String toString() {
        return value;
    }

}
