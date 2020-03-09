package seedu.address.model.pet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PetBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Collections.singletonList("Kiruya"));
        assertTrue(predicate.test(new PetBuilder().withName("Kiruya Garfield").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Kiruya", "Garfield"));
        assertTrue(predicate.test(new PetBuilder().withName("Kiruya Garfield").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Garfield", "Momochi"));
        assertTrue(predicate.test(new PetBuilder().withName("Kiruya Momochi").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Kiruya", "Garfield"));
        assertTrue(predicate.test(new PetBuilder().withName("Kiruya Garfield").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PetBuilder().withName("Kiruya").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Momochi"));
        assertFalse(predicate.test(new PetBuilder().withName("Kiruya Garfield").build()));

        // Keywords match date of birth and species, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("2-9-1998", "Cat"));
        assertFalse(predicate.test(new PetBuilder().withName("Kiruya").withGender(Gender.FEMALE)
                .withDateOfBirth("2-9-1998").withSpecies("Cat").build()));
    }
}
