package clzzz.helper.model.slot;

import java.util.List;
import java.util.function.Predicate;

import clzzz.helper.commons.util.StringUtil;

/**
 * Tests that a {@code Slot}'s {@code Pet}'s {@code Name} matches the given name exactly.
 */
public class SlotPetNamePredicate implements Predicate<Slot> {
    private final List<String> petName;

    public SlotPetNamePredicate(List<String> petName) {
        this.petName = petName;
    }

    @Override
    public boolean test(Slot slot) {
        return petName.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(slot.getPet().getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SlotPetNamePredicate // instanceof handles nulls
                && petName.equals(((SlotPetNamePredicate) other).petName)); // state check
    }
}
