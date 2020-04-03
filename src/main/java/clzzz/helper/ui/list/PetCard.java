package clzzz.helper.ui.list;

import clzzz.helper.model.pet.Pet;
import clzzz.helper.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Pet}.
 */
public class PetCard extends UiPart<Region> {

    private static final String FXML = "list/PetCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Pet pet;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label gender;
    @FXML
    private Label dateOfBirth;
    @FXML
    private Label species;
    @FXML
    private FlowPane foodList;
    @FXML
    private FlowPane tags;

    public PetCard(Pet pet, int displayedIndex) {
        super(FXML);
        this.pet = pet;
        id.setText(displayedIndex + ". ");
        name.setText(pet.getName().toString());
        gender.setText(pet.getGender().toString());
        dateOfBirth.setText(pet.getDateOfBirth().toString());
        species.setText(pet.getSpecies().toString());
        pet.getFoodList()
                .stream()
                .forEach(food -> {
                    Label foodLabel = new Label(food.toString());
                    foodList.getChildren().add(foodLabel);
                });
        pet.getTags()
                .stream()
                .sorted()
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    tags.getChildren().add(tagLabel);
                });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PetCard)) {
            return false;
        }

        // state check
        PetCard card = (PetCard) other;
        return id.getText().equals(card.id.getText())
                && pet.equals(card.pet);
    }
}
