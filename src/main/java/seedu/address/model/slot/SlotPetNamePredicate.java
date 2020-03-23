package seedu.address.model.slot;

/**
 * Tests that a {@code Slot}'s {@code Pet}'s {@code Name} matches the given name exactly.
 */
public class SlotPetNamePredicate extends SlotPredicate {
    private final String petName;

    public SlotPetNamePredicate(String petName) {
        this.petName = petName;
    }

    @Override
    public boolean test(Slot slot) {
        return petName.equals(slot.getPet().getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SlotPetNamePredicate // instanceof handles nulls
                && petName.equals(((SlotPetNamePredicate) other).petName)); // state check
    }
}
