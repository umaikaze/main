package clzzz.helper.model.foodcollection;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import clzzz.helper.model.pet.Food;
import clzzz.helper.model.pet.Pet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A FoodCollectionList represents a list of unique Food Collections, where Food Collections are uniquely identified
 * through FoodCollection#name. FoodCollectionList wraps around an internal ObservableList of FoodCollection and it
 * mainly interacts with Food object or a list of Food objects.
 */
public class FoodCollectionList {

    private final ObservableList<FoodCollection> internalList = FXCollections.observableArrayList();
    private final ObservableList<FoodCollection> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Creates a FoodCollectionList that contains a given list of foods
     * @param pets The list of foods to be used to create the food collection list.
     */
    public FoodCollectionList(List<Pet> pets) {
        addPetList(pets);
    }

    /**
     * Checks if The if the type of the Food object input already exists in the food collection list.
     * @param toCheck The food item to be checked.
     * @return Returns true if it exists.
     */
    public boolean contains(Food toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(t-> t.isSameType(toCheck));
    }

    /**
     * Gets the food collection in the list whose food collection name is of the given input.
     * Note that the caller of the function has to ensure that the input name given already
     * exists in the FoodCollectionList.
     * @param name The name of the Food Collection to be checked.
     * @return The FoodCollection with the matching name.
     */
    public FoodCollection getFoodCollection(String name) {
        requireNonNull(name);
        return internalList.stream()
                .filter(fc -> fc.getName().equals(name))
                .findFirst().get();
    }

    /**
     * Adds a food item into the food collection list.
     * @param food The food to be added.
     */
    public void addFood(Food food, Pet pet) {
        if (contains(food)) {
            FoodCollection foodCollection = getFoodCollection(food.foodName);
            foodCollection.addFoodToCollection(food, pet);
        } else {
            internalList.add(FoodCollection.generateFoodCollection(food, pet));
        }
    }

    /**
     * Adds a list of food items into the food collection list.
     * @param pets The list of food to be added.
     */
    public void addPetList(List<Pet> pets) {
        for (Pet pet:pets) {
            Set<Food> foodList = pet.getFoodList();
            for (Food food:foodList) {
                addFood(food, pet);
            }
        }
    }

    /**
     * Updates the food collection list by replacing the items originally in the list with the given list of foods.
     * @param pets The list of pets used to replace the original list.
     */
    public void update(List<Pet> pets) {
        internalList.setAll(new FoodCollectionList(pets).asUnmodifiableObservableList());
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<FoodCollection> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }
}

