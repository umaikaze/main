package seedu.address.logic.commands.pet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.pet.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.pet.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.pet.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.pet.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.pet.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.general.Command;
import seedu.address.logic.commands.general.CommandResult;
import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.logic.commands.pet.EditPetCommand;
import seedu.address.model.PetTracker;
import seedu.address.model.Model;
import seedu.address.model.PshModel;
import seedu.address.model.pet.NameContainsKeywordsPredicate;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.Gender;
import seedu.address.testutil.pet.EditPetDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class PshCommandTestUtil {

    public static final String VALID_NAME_COCO = "Coco";
    public static final String VALID_NAME_GARFIELD = "Garfield";
    public static final Gender VALID_GENDER_COCO = Gender.FEMALE;
    public static final Gender VALID_GENDER_GARFIELD = Gender.MALE;
    public static final String VALID_DOB_COCO = "2-2-2015";
    public static final String VALID_DOB_GARFIELD = "19-6-1978";
    public static final String VALID_SPECIES_COCO = "dog";
    public static final String VALID_SPECIES_GARFIELD = "cat";
    public static final String VALID_TAG_FAT = "fat";
    public static final String VALID_TAG_HYPER = "hyper";
    public static final String VALID_TAG_LAZY = "lazy";

    public static final String NAME_DESC_COCO = " " + PREFIX_NAME + VALID_NAME_COCO;
    public static final String NAME_DESC_GARFIELD = " " + PREFIX_NAME + VALID_NAME_GARFIELD;
    public static final String GENDER_DESC_COCO = " " + PREFIX_GENDER + VALID_GENDER_COCO;
    public static final String GENDER_DESC_GARFIELD = " " + PREFIX_GENDER + VALID_GENDER_GARFIELD;
    public static final String DOB_DESC_COCO = " " + PREFIX_DOB + VALID_DOB_COCO;
    public static final String DOB_DESC_GARFIELD = " " + PREFIX_DOB + VALID_DOB_GARFIELD;
    public static final String SPECIES_DESC_COCO = " " + PREFIX_SPECIES + VALID_SPECIES_COCO;
    public static final String SPECIES_DESC_GARFIELD = " " + PREFIX_SPECIES + VALID_SPECIES_GARFIELD;
    public static final String TAG_DESC_HYPER = " " + PREFIX_TAG + VALID_TAG_HYPER;
    public static final String TAG_DESC_LAZY = " " + PREFIX_TAG + VALID_TAG_LAZY;
    public static final String TAG_DESC_FAT = " " + PREFIX_TAG + VALID_TAG_FAT;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_GENDER_DESC = " " + PREFIX_GENDER + "femali"; // only "male" and "female" allowed
    public static final String INVALID_DOB_DESC = " " + PREFIX_DOB + "07/04/00"; // must follow d-M-yyyy format
    public static final String INVALID_SPECIES_DESC = " " + PREFIX_SPECIES; // empty string not allowed for species
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditPetCommand.EditPetDescriptor DESC_COCO;
    public static final EditPetCommand.EditPetDescriptor DESC_GARFIELD;

    static {
        DESC_COCO = new EditPetDescriptorBuilder().withName(VALID_NAME_COCO)
                .withGender(VALID_GENDER_COCO.toString()).withDateOfBirth(VALID_DOB_COCO).withSpecies(VALID_SPECIES_COCO)
                .withTags(VALID_TAG_HYPER).build();
        DESC_GARFIELD = new EditPetDescriptorBuilder().withName(VALID_NAME_GARFIELD)
                .withGender(VALID_GENDER_GARFIELD.toString()).withDateOfBirth(VALID_DOB_GARFIELD).withSpecies(VALID_SPECIES_GARFIELD)
                .withTags(VALID_TAG_LAZY, VALID_TAG_FAT).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, PshModel actualModel, CommandResult expectedCommandResult,
                                            PshModel expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, PshModel, CommandResult, PshModel)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, PshModel actualModel, String expectedMessage,
                                            PshModel expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered pet list and selected pet in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, PshModel actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        PetTracker expectedPetTracker = new PetTracker(actualModel.getPetTracker());
        List<Pet> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPetList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedPetTracker, actualModel.getPetTracker());
        assertEquals(expectedFilteredList, actualModel.getFilteredPetList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the pet at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPetAtIndex(PshModel model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPetList().size());

        Pet pet = model.getFilteredPetList().get(targetIndex.getZeroBased());
        final String[] splitName = pet.getName().fullName.split("\\s+");
        model.updateFilteredPetList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPetList().size());
    }

}
