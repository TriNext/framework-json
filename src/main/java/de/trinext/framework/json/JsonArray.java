package de.trinext.framework.json;

import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

import de.trinext.framework.json.paths.JsonElementIterable;
import util.GsonHelper;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class JsonArray
        extends JsonElement<List<JsonElement<?>>>
        implements Iterable<JsonElement<?>>, JsonElementIterable
{

    // ==== CONSTRUCTORS ===================================================== //

    /** Creates an empty JsonArray. */
    JsonArray(Object... elems) {
        super(Arrays.stream(elems).map(Json::treeFromInstance).collect(Collectors.toList()));
    }

    public int size() {
        return getValue().size();
    }

    public boolean isEmpty() {
        return getValue().isEmpty();
    }

    /** @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore. */
    @Deprecated
    static JsonArray from(com.google.gson.JsonArray jArr) {
        // TODO: Optimize performance
        return new JsonArray(GsonHelper.arrayToStream(jArr)
                .map(Json::treeFromGsonTree)
                .collect(Collectors.toList()));
    }

    // ==== METHODS ========================================================== //

    @SuppressWarnings("BoundedWildcard")
    public JsonArray addObj(Function<JsonObject, JsonObject> elem) throws JsonFieldAlreadyExistsException {
        return add(elem.apply(new JsonObject()));
    }


    public JsonArray add(Object elem) throws JsonFieldAlreadyExistsException {
        getValue().add(Json.treeFromInstance(elem));
        return this;
    }

    public boolean contains(Object elem) {
        return getValue().contains(Json.treeFromInstance(elem));
    }


    public JsonArray addArr(Object... elems) throws JsonFieldAlreadyExistsException {
        return add(elems);
    }

    @Override
    public Iterator<JsonElement<?>> iterator() {
        return getValue().iterator();
    }

    @Override
    public com.google.gson.JsonArray toGsonElem() {
        return stream()
                .map(JsonElement::toGsonElem)
                .collect(
                        com.google.gson.JsonArray::new,
                        com.google.gson.JsonArray::add,
                        com.google.gson.JsonArray::addAll
                );
    }

    public Stream<JsonElement<?>> stream() {
        return getValue().stream();
    }

    @Override
    public Optional<JsonElement<?>> tryGet(String jsonPath) {
        return jsonPath.matches("\\d+")
               ? tryGet(Integer.parseInt(jsonPath))
               : JsonElementIterable.super.tryGet(jsonPath);
    }

    public Optional<JsonElement<?>> tryGet(int idx) {
        return Optional.ofNullable(getValue().get(idx));
    }

    @Override
    public String toString() {
        return getValue().toString();
    }

}