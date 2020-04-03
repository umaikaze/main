package w154.helper.logic.commands.pet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static w154.helper.logic.commands.CommandTestUtil.assertFindPetCommandSuccess;
import static w154.helper.testutil.pet.TypicalPets.CARL;
import static w154.helper.testutil.pet.TypicalPets.ELLE;
import static w154.helper.testutil.pet.TypicalPets.FIONA;
import static w154.helper.testutil.pet.TypicalPets.getTypicalPetTracker;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import w154.helper.model.Model;
import w154.helper.model.ModelManager;
import w154.helper.model.UserPrefs;
import w154.helper.model.pet.NameContainsKeywordsPredicate;
import w154.helper.commons.core.Messages;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPetCommand}.
 */
public class FindPetCommandTest {
    private Model model = new ModelManager(getTypicalPetTracker(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalPetTracker(), new UserPrefs());

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
        String expectedMessage = String.format(Messages.MESSAGE_PETS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindPetCommand command = new FindPetCommand(predicate);
        expectedModel.updateFilteredPetList(predicate);
        assertFindPetCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPetList());
    }

    @Test
    public void execute_multipleKeywords_multiplePetsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PETS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Carl Elle Fiona");
        FindPetCommand command = new FindPetCommand(predicate);
        expectedModel.updateFilteredPetList(predicate);
        assertFindPetCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPetList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
