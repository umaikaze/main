package seedu.address.logic.commands.pet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.PshMessages.MESSAGE_PETS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.pet.PshCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPets.CARL;
import static seedu.address.testutil.TypicalPets.ELLE;
import static seedu.address.testutil.TypicalPets.FIONA;
import static seedu.address.testutil.TypicalPets.getTypicalPetTracker;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.PshModel;
import seedu.address.model.PshModelManager;
import seedu.address.model.PshUserPrefs;
import seedu.address.model.pet.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPetCommand}.
 */
public class FindPetCommandTest {
    private PshModel model = new PshModelManager(getTypicalPetTracker(), new PshUserPrefs());
    private PshModel expectedModel = new PshModelManager(getTypicalPetTracker(), new PshUserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindPetCommand findFirstCommand = new FindPetCommand(firstPredicate);
        FindPetCommand findSecondCommand = new FindPetCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPetCommand findFirstCommandCopy = new FindPetCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different pet -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPetFound() {
        String expectedMessage = String.format(MESSAGE_PETS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindPetCommand command = new FindPetCommand(predicate);
        expectedModel.updateFilteredPetList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPetList());
    }

    @Test
    public void execute_multipleKeywords_multiplePetsFound() {
        String expectedMessage = String.format(MESSAGE_PETS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindPetCommand command = new FindPetCommand(predicate);
        expectedModel.updateFilteredPetList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPetList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
