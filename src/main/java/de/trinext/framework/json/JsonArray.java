package de.trinext.framework.json;

import java.util.*;
import java.util.stream.*;

import de.trinext.framework.json.paths.JsonElementIterable;

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
    JsonArray() {
        super(new ArrayList<>());
    }

    /** @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore. */
    @Deprecated @SuppressWarnings("TypeMayBeWeakened") //
    JsonArray(com.google.gson.JsonArray jArr) {
        super(StreamSupport.stream(jArr.spliterator(), false)
                .map(Json::treeFromGsonTree)
                .collect(Collectors.toList()));
    }

    // ==== METHODS ========================================================== //

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

    @Override
    public String toString() {
        return getValue().toString();
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

}