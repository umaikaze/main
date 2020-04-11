package clzzz.helper.logic.commands.pet;

import static clzzz.helper.logic.parser.CliSyntax.PREFIX_DOB;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_FOODLIST;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_GENDER;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_NAME;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_SPECIES;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import clzzz.helper.commons.core.Messages;
import clzzz.helper.commons.core.index.Index;
import clzzz.helper.commons.util.CollectionUtil;
import clzzz.helper.logic.commands.Command;
import clzzz.helper.logic.commands.CommandResult;
import clzzz.helper.logic.commands.exceptions.CommandException;
import clzzz.helper.model.Model;
import clzzz.helper.model.pet.DateOfBirth;
import clzzz.helper.model.pet.Food;
import clzzz.helper.model.pet.Gender;
import clzzz.helper.model.pet.Name;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.pet.Species;
import clzzz.helper.model.pet.Tag;

/**
 * Edits the details of an existing pet in the pet tracker.
 */
public class EditPetCommand extends Command {

    public static final String COMMAND_WORD = "editpet";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the pets identified "
            + "by the index number used in the displayed pets list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_DOB + "DATE OF BIRTH] "
            + "[" + PREFIX_SPECIES + "SPECIES] "
            + "[" + PREFIX_FOODLIST + "LIST OF FOOD AND AMOUNT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GENDER + "female "
            + PREFIX_DOB + "01/02/2013";

    public static final String MESSAGE_EDIT_PET_SUCCESS = "Edited Pet: %1$s\n";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PET = "This pet already exists in the pet store helper.";
    public static final String MESSAGE_EMPTY_FOODLIST = "The list of food should not be empty.";

    private final Index index;
    private final EditPetDescriptor editPetDescriptor;
    private final String warningMessage;

    /**
     * @param index of the pet in the filtered pet list to edit
     * @param editPetDescriptor details to edit the pet with
     */
    public EditPetCommand(Index index, EditPetDescriptor editPetDescriptor, String warningMessage) {
        requireNonNull(index);
        requireNonNull(editPetDescriptor);

        this.index = index;
        this.editPetDescriptor = new EditPetDescriptor(editPetDescriptor);
        this.warningMessage = warningMessage;
    }

    /**
     * Creates and returns a {@code Pet} with the details of {@code petToEdit}
     * edited with {@code editPetDescriptor}.
     */
    private static Pet createEditedPet(Pet petToEdit, EditPetDescriptor editPetDescriptor) {
        assert petToEdit != null;

        Name updatedName = editPetDescriptor.getName().orElse(petToEdit.getName());
        Gender updatedGender = editPetDescriptor.getGender().orElse(petToEdit.getGender());
        DateOfBirth updatedDateOfBirth = editPetDescriptor.getDateOfBirth().orElse(petToEdit.getDateOfBirth());
        Species updatedSpecies = editPetDescriptor.getSpecies().orElse(petToEdit.getSpecies());
        Set<Food> updatedFoodList = editPetDescriptor.getFoodList().orElse(petToEdit.getFoodList());
        Set<Tag> updatedTags = editPetDescriptor.getTags().orElse(petToEdit.getTags());

        return new Pet(updatedName, updatedGender, updatedDateOfBirth, updatedSpecies, updatedFoodList, updatedTags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Pet> lastShownList = model.getFilteredPetList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
        }

        Pet petToEdit = lastShownList.get(index.getZeroBased());
        Pet editedPet = createEditedPet(petToEdit, editPetDescriptor);

        if (!petToEdit.isSamePet(editedPet) && model.hasPet(editedPet)) {
            throw new CommandException(MESSAGE_DUPLICATE_PET);
        }

        model.setPet(petToEdit, editedPet);
        model.updateFilteredPetList(Model.PREDICATE_SHOW_ALL_PETS);
        return new CommandResult(String.format(MESSAGE_EDIT_PET_SUCCESS, editedPet) + warningMessage);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPetCommand)) {
            return false;
        }

        // state check
        EditPetCommand e = (EditPetCommand) other;
        return index.equals(e.index)
                && editPetDescriptor.equals(e.editPetDescriptor)
                && warningMessage.equals(e.warningMessage);
    }

    /**
     * Stores the details to edit the pet with. Each non-empty field value will replace the
     * corresponding field value of the pet.
     */
    public static class EditPetDescriptor {
        private Name name;
        private Gender gender;
        private DateOfBirth dateOfBirth;
        private Species species;
        private Set<Food> foodList;
        private Set<Tag> tags;

        public EditPetDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPetDescriptor(EditPetDescriptor toCopy) {
            setName(toCopy.name);
            setGender(toCopy.gender);
            setDateOfBirth(toCopy.dateOfBirth);
            setSpecies(toCopy.species);
            setFoodList(toCopy.foodList);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, gender, dateOfBirth, species, foodList, tags);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<DateOfBirth> getDateOfBirth() {
            return Optional.ofNullable(dateOfBirth);
        }

        public void setDateOfBirth(DateOfBirth dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public Optional<Species> getSpecies() {
            return Optional.ofNullable(species);
        }

        public void setSpecies(Species species) {
            this.species = species;
        }

        public Optional<Set<Food>> getFoodList() {
            return (foodList != null) ? Optional.of(Collections.unmodifiableSet(foodList)) : Optional.empty();
        }

        public void setFoodList(Set<Food> foodList) {
            this.foodList = (foodList != null) ? new HashSet<>(foodList) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPetDescriptor)) {
                return false;
            }

            // state check
            EditPetDescriptor e = (EditPetDescriptor) other;

            return getName().equals(e.getName())
                    && getGender().equals(e.getGender())
                    && getDateOfBirth().equals(e.getDateOfBirth())
                    && getSpecies().equals(e.getSpecies())
                    && getFoodList().equals(e.getFoodList())
                    && getTags().equals(e.getTags());
        }
    }
}
