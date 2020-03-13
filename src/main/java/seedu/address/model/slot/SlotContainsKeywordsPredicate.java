package seedu.address.model.slot;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Slot}'s {@code Name} or {@code LocalDateTime} matches any of the keywords given.
 */
public class SlotContainsKeywordsPredicate implements Predicate<Slot> {
    private final List<String> keywords;

    public SlotContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Slot slot) {
        if (keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(slot.getPet().getName().fullName, keyword))) {
            return true;
        } else {
            //TODO Allow search with datetime too
            throw new UnsupportedOperationException("Not implemented yet!");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SlotContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SlotContainsKeywordsPredicate) other).keywords)); // state check
    }
}
