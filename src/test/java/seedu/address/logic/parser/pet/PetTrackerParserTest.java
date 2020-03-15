package seedu.address.logic.parser.pet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.pet.PshTypicalIndexes.INDEX_FIRST_PET;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.pet.AddPetCommand;
import seedu.address.logic.commands.pet.DeletePetCommand;
import seedu.address.logic.commands.pet.EditPetCommand;
import seedu.address.logic.commands.pet.FindPetCommand;
import seedu.address.logic.commands.pet.ListCommand;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.model.pet.NameContainsKeywordsPredicate;
import seedu.address.model.pet.Pet;
import seedu.address.testutil.PetBuilder;
import seedu.address.testutil.pet.EditPetDescriptorBuilder;
import seedu.address.testutil.pet.PetUtil;


public class PetTrackerParserTest {

    private final PetTrackerParser parser = new PetTrackerParser();

    @Test
    public void parseCommand_add() throws Exception {
        Pet pet = new PetBuilder().build();
        AddPetCommand command = (AddPetCommand) parser.parseCommand(PetUtil.getAddPetCommand(pet));
        assertEquals(new AddPetCommand(pet), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeletePetCommand command = (DeletePetCommand) parser.parseCommand(
                DeletePetCommand.COMMAND_WORD + " " + INDEX_FIRST_PET.getOneBased());
        assertEquals(new DeletePetCommand(INDEX_FIRST_PET), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Pet pet = new PetBuilder().build();
        EditPetCommand.EditPetDescriptor descriptor = new EditPetDescriptorBuilder(pet).build();
        EditPetCommand command = (EditPetCommand) parser.parseCommand(EditPetCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PET.getOneBased() + " " + PetUtil.getEditPetDescriptorDetails(descriptor));
        assertEquals(new EditPetCommand(INDEX_FIRST_PET, descriptor), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindPetCommand command = (FindPetCommand) parser.parseCommand(
                FindPetCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPetCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
