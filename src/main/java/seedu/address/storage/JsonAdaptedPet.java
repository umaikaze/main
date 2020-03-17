package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.pet.DateOfBirth;
import seedu.address.model.pet.Food;
import seedu.address.model.pet.Gender;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.Species;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Pet}.
 */
class JsonAdaptedPet {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Pet's %s field is missing!";

    private final String name;
    private final String gender;
    private final String dateOfBirth;
    private final String species;
    private final List<JsonAdaptedFood> foodList = new ArrayList<>();
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPet} with the given pet details.
     */
    @JsonCreator
    /* ignore reading in food from json for now
    public JsonAdaptedPet(@JsonProperty("name") String name, @JsonProperty("gender") String gender,
                          @JsonProperty("dateOfBirth") String dateOfBirth, @JsonProperty("species") String species,
                          @JsonProperty("foodList") List<JsonAdaptedFood> foodList,
                          @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        */

    public JsonAdaptedPet(@JsonProperty("name") String name, @JsonProperty("gender") String gender,
                @JsonProperty("dateOfBirth") String dateOfBirth, @JsonProperty("species") String species,
                @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.species = species;
        /* ignore reading in food from json for now
        if (foodList != null) {
            this.foodList.addAll(foodList);
        }
        */
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Pet} into this class for Jackson use.
     */
    public JsonAdaptedPet(Pet source) {
        name = source.getName().fullName;
        gender = source.getGender().toString();
        dateOfBirth = source.getDateOfBirth().toString();
        species = source.getSpecies().toString();
        foodList.addAll(source.getFoodList().stream()
                .map(JsonAdaptedFood::new)
                .collect(Collectors.toList()));
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted pet object into the model's {@code Pet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted pet.
     */
    public Pet toModelType() throws IllegalValueException {
        final List<Tag> petTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            petTags.add(tag.toModelType());
        }

        final List<Food> petFoods = new ArrayList<>();
        for (JsonAdaptedFood food : foodList) {
            petFoods.add(food.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = Gender.valueOf(gender.toUpperCase()); // TODO: refactor gender

        if (dateOfBirth == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateOfBirth.class.getSimpleName()));
        }
        if (!DateOfBirth.isValidDateOfBirth(dateOfBirth)) {
            throw new IllegalValueException(DateOfBirth.MESSAGE_CONSTRAINTS);
        }
        final DateOfBirth modelDateOfBirth = new DateOfBirth(dateOfBirth);

        if (species == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Species.class.getSimpleName()));
        }
        if (!Species.isValidSpecies(species)) {
            throw new IllegalValueException(Species.MESSAGE_CONSTRAINTS);
        }
        final Species modelSpecies = new Species(species);

        final Set<Tag> modelTags = new HashSet<>(petTags);
        final Set<Food> modelFoods = new HashSet<>(petFoods);
        return new Pet(modelName, modelGender, modelDateOfBirth, modelSpecies, modelFoods, modelTags);
    }

}
