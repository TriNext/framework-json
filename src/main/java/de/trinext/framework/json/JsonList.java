package de.trinext.framework.json;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.JsonArray;
import util.GsonHelper;

/**
 * The json representation of a mutable, ordered, numerically indexed, linear collection of {@link JsonElement}s.
 *
 * @author Dennis Woithe
 * @see JsonArray gson equivalent
 * @see List java equivalent
 */
@SuppressWarnings({"unused", "WeakerAccess", "deprecation"})
public final class JsonList
        extends JsonContainer<List<JsonElement<?>>>
        implements Iterable<JsonElement<?>>
{

    // ==== CONSTRUCTORS ===================================================== //

    /** Creates an empty JsonArray. */
    JsonList(Object... elems) {
        super(Arrays.stream(elems).map(Json::treeFromInstance).collect(Collectors.toList()));
    }

// METHODS ========================================================>>

    /** @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore. */
    @Deprecated
    static JsonList from(JsonArray jArr) {
        return new JsonList(GsonHelper.arrayToStream(jArr)
                .map(Json::treeFromGsonTree)
                .collect(Collectors.toList()));
    }

    /** @return the amount of elements in this array. */
    public int size() {
        return getValue().size();
    }

    /** @return whether this array is empty. */
    public boolean isEmpty() {
        return getValue().isEmpty();
    }

    // ==== METHODS ========================================================== //

    @SuppressWarnings("BoundedWildcard")
    public JsonList addObj(Function<JsonMap, JsonMap> elem) throws JsonFieldAlreadyExistsException {
        return add(elem.apply(new JsonMap()));
    }


    public JsonList add(Object elem) throws JsonFieldAlreadyExistsException {
        getValue().add(Json.treeFromInstance(elem));
        return this;
    }

    public boolean contains(Object elem) {
        return getValue().contains(Json.treeFromInstance(elem));
    }


    public JsonList addArr(Object... elems) throws JsonFieldAlreadyExistsException {
        return add(elems);
    }

    @Override
    public Iterator<JsonElement<?>> iterator() {
        return getValue().iterator();
    }

    @Override
    public JsonArray toGsonElem() {
        return stream()
                .map(JsonElement::toGsonElem)
                .collect(
                        JsonArray::new,
                        JsonArray::add,
                        JsonArray::addAll
                );
    }

    public Stream<JsonElement<?>> stream() {
        return getValue().stream();
    }

    @Override
    public String toString() {
        return getValue().toString();
    }

    public Optional<JsonElement<?>> tryGet(int idx) {
        return Optional.ofNullable(getValue().get(idx));
    }

}