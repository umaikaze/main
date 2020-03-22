package seedu.address.model.pet;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of pets that enforces uniqueness between its elements and does not allow nulls.
 * A pet is considered unique by comparing using {@code Pet#isSamePet(Pet)}. As such, adding and updating of
 * pets uses Pet#isSamePet(Pet) for equality so as to ensure that the pet being added or updated is
 * unique in terms of identity in the UniquePetList. However, the removal of a pet uses Pet#equals(Object) so
 * as to ensure that the pet with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Pet#isSamePet(Pet)
 */
public class FoodCollectionList {

    private final ObservableList<FoodCollection> internalList = FXCollections.observableArrayList();
    private final ObservableList<FoodCollection> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Creates a FoodCollectionList that contains a given list of foods
     * @param foods The list of foods to be used to create the food collection list.
     */
    public FoodCollectionList(List<Food> foods) {
        addFoodList(foods);
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
     * Gets the food collection in the list whose food collection name is of the given input. name
     * is assumed to exist.
     * @param name The name of the Food Collection to be checked.
     * @return The FoodCollection with the matching name.
     */
    public FoodCollection getFoodCollection(String name) {
        requireNonNull(name);
        List<FoodCollection> foodCollectionsWithMatchingName = internalList.stream()
                .filter(fc -> fc.getFoodCollectionName().equals(name))
                .collect(Collectors.toList());
        return foodCollectionsWithMatchingName.get(0);
    }

    /**
     * Adds a food item into the food collection list.
     * @param food The food to be added.
     */
    public void addFood(Food food) {
        if (contains(food)) {
            FoodCollection foodCollection = getFoodCollection(food.foodName);
            foodCollection.addFoodToCollection(food);
        } else {
            internalList.add(FoodCollection.generateFoodCollection(food));
        }
    }

    /**
     * Adds a list of food items into the food collection list.
     * @param foods The list of food to be added.
     */
    public void addFoodList(List<Food> foods) {
        foods.forEach(this::addFood);
    }

    /**
     * Updates the food collection list by replacing the items originally in the list with the given list of foods.
     * @param foods The list of food used to replace the original list.
     */
    public void update(List<Food> foods) {
        internalList.setAll(new FoodCollectionList(foods).internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<FoodCollection> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }
}
