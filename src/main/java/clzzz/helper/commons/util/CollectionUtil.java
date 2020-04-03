package clzzz.helper.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;

/**
 * Utility methods related to Collections
 */
public class CollectionUtil {

    /** @see #requireAllNonNull(Collection) */
    public static void requireAllNonNull(Object... items) {
        requireNonNull(items);
        Stream.of(items).forEach(Objects::requireNonNull);
    }

    /**
     * Throws NullPointerException if {@code items} or any element of {@code items} is null.
     */
    public static void requireAllNonNull(Collection<?> items) {
        requireNonNull(items);
        items.forEach(Objects::requireNonNull);
    }

    /**
     * Returns true if {@code items} contain any elements that are non-null.
     */
    public static boolean isAnyNonNull(Object... items) {
        return items != null && Arrays.stream(items).anyMatch(Objects::nonNull);
    }

    /**
     * Returns an {@code ObservableList} consisting of the results of
     * applying {@code mapper} to the elements of {@code originalList}.
     * The new {@code ObservableList} has all the listeners that {@code originalList} has.
     * Implementation heavily adapted from https://stackoverflow.com/a/32297235/13075103
     * with slight modifications.
     */
    public static <R, T> ObservableList<R> map(ObservableList<T> originalList, Function<T, R> mapper) {
        return new TransformationList<R, T>(originalList) {
            @Override
            public int getSourceIndex(int index) {
                return index;
            }

            @Override
            public int getViewIndex(int index) {
                return index;
            }

            @Override
            public R get(int index) {
                T element = getSource().get(index);
                return mapper.apply(element);
            }

            @Override
            public int size() {
                return getSource().size();
            }

            @Override
            protected void sourceChanged(Change<? extends T> change) {
                fireChange(new Change<R>(this) {
                    @Override
                    public boolean wasAdded() {
                        return change.wasAdded();
                    }

                    @Override
                    public boolean wasRemoved() {
                        return change.wasRemoved();
                    }

                    @Override
                    public boolean wasReplaced() {
                        return change.wasReplaced();
                    }

                    @Override
                    public boolean wasUpdated() {
                        return change.wasUpdated();
                    }

                    @Override
                    public boolean wasPermutated() {
                        return change.wasPermutated();
                    }

                    @Override
                    public int getPermutation(int i) {
                        return change.getPermutation(i);
                    }

                    @Override
                    protected int[] getPermutation() {
                        // This method is only called by the superclass methods
                        // wasPermutated() and getPermutation(int), which are
                        // both overriden by this class. There is no other way
                        // this method can be called.
                        throw new AssertionError("Unreachable code");
                    }

                    @Override
                    public List<R> getRemoved() {
                        return change.getRemoved()
                                .stream()
                                .map(mapper)
                                .collect(Collectors.toList());
                    }

                    @Override
                    public int getFrom() {
                        return change.getFrom();
                    }

                    @Override
                    public int getTo() {
                        return change.getTo();
                    }

                    @Override
                    public boolean next() {
                        return change.next();
                    }

                    @Override
                    public void reset() {
                        change.reset();
                    }
                });
            }

        };
    }
}
