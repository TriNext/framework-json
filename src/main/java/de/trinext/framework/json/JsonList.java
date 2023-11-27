package de.trinext.framework.json;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The json representation of a mutable, ordered, numerically indexed, linear collection of {@link JsonElement}s.
 *
 * @author Dennis Woithe
 * @see List java equivalent
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class JsonList
        extends JsonContainer<List<JsonElement<?>>>
        implements Iterable<JsonElement<?>>
{

    // ==== CONSTRUCTORS ===================================================== //

    public JsonList() {
        super(new ArrayList<>());
    }

    public JsonList(Object... elems) {
        super(Arrays.stream(elems)
                .map(Json::treeFromInstance)
                .collect(Collectors.toList())
        );
    }

    public JsonList(Collection<?> elems) {
        super(elems.stream()
                .map(Json::treeFromInstance)
                .collect(Collectors.toList())
        );
    }

    /** @return the amount of elements in this array. */
    public int size() {
        return value.size();
    }

    /** @return whether this array is empty. */
    public boolean isEmpty() {
        return value.isEmpty();
    }

    // ==== METHODS ========================================================== //

    @SuppressWarnings("BoundedWildcard")
    public JsonList addObj(Function<JsonMap, JsonMap> elem) {
        return add(elem.apply(new JsonMap()));
    }

    public JsonList add(Object elem) {
        value.add(Json.treeFromInstance(elem));
        return this;
    }

    public JsonList addArr(Object... elems) {
        return add(elems);
    }

    public JsonList addAll(Iterable<?> it) {
        it.forEach(this::add);
        return this;
    }

    public boolean contains(Object elem) {
        return value.contains(Json.treeFromInstance(elem));
    }

    public boolean removeAt(int idx) {
        return value.remove(idx) != null;
    }

    public void clear() {
        value.clear();
    }

    @Override
    public Iterator<JsonElement<?>> iterator() {
        return value.iterator();
    }

    public Stream<JsonElement<?>> stream() {
        return value.stream();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Optional<JsonElement<?>> tryGet(int idx) {
        return Optional.ofNullable(value.get(idx));
    }

}