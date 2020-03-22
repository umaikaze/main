package seedu.address.model.pet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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

    public FoodCollectionList(List<Food> foods) {
        addFoodList(foods);
    }

    public boolean contains(Food toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(t-> t.isSameType(toCheck));
    }

    public FoodCollection getFoodCollection(String name) {
        requireNonNull(name);
        List<FoodCollection> foodCollectionsWithMatchingName = internalList.stream()
                .filter(fc -> fc.getFoodCollectionName().equals(name))
                .collect(Collectors.toList());
        return foodCollectionsWithMatchingName.get(0);
    }

    public void addFood(Food food) {
        if (contains(food)) {
            FoodCollection foodCollection = getFoodCollection(food.foodName);
            foodCollection.addFoodToCollection(food);
        } else {
            internalList.add(FoodCollection.GenerateFoodCollection(food));
        }
    }

    public void addFoodList(List<Food> foods) {
        foods.forEach(this::addFood);
    }

    public void update(List<Food> foods) {
        internalList.setAll(new FoodCollectionList(foods).internalList);
    }

    public ObservableList<FoodCollection> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }
}
