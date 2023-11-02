package de.trinext.framework.json;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.trinext.framework.json.paths.JsonElementIterable;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class JsonObject
        extends JsonElement<SequencedMap<String, JsonElement<?>>>
        implements Iterable<Entry<String, JsonElement<?>>>, JsonElementIterable
{

    // ==== CONSTRUCTORS ===================================================== //

    /** Creates an empty JsonObject. */
    JsonObject() {
        super(new LinkedHashMap<>());
    }

    /** Creates an empty JsonObject. */
    JsonObject(SequencedMap<String, JsonElement<?>> fields) {
        super(fields);
    }

    // ==== METHODS ========================================================== //

    /**
     * Add a JsonObject as a field.
     *
     * @param fieldName has to be a unique name
     * @param objFieldValue an inner builder function
     */
    @SuppressWarnings("BoundedWildcard")
    public JsonObject addObj(
            String fieldName,
            Function<JsonObject, JsonObject> objFieldValue
    ) throws JsonFieldAlreadyExistsException {
        return add(fieldName, objFieldValue.apply(new JsonObject()));
    }

    /**
     * Add anything as a field.
     *
     * @param fieldName has to be a unique name
     * @param value can be any object
     */
    public JsonObject add(
            String fieldName,
            Object value
    ) throws JsonFieldAlreadyExistsException {
        if (getValue().containsKey(Objects.requireNonNull(fieldName)))
            throw new JsonFieldAlreadyExistsException(fieldName);
        getValue().put(fieldName, Json.treeFromInstance(value));
        return this;
    }

    /** Returns true if this object has a field with the passed name. */
    public boolean contains(String fieldName) {
        return getValue().containsKey(Objects.requireNonNull(fieldName));
    }

    /**
     * Add a JsonArray as a field.
     *
     * @param fieldName has to be a unique name
     * @param arrFieldValue an array of values
     */
    public JsonObject addArr(
            String fieldName,
            Object... arrFieldValue
    ) throws JsonFieldAlreadyExistsException {
        return add(fieldName, arrFieldValue);
    }

    @Override
    public com.google.gson.JsonObject toGsonElem() {
        var res = new com.google.gson.JsonObject();
        for (var entry : this)
            res.add(entry.getKey(), entry.getValue().toGsonElem());
        return res;
    }

    @Override
    public String toString() {
        return getValue().entrySet()
                .stream()
                .map(entry -> "\"" + entry.getKey() + "\":" + entry.getValue())
                .collect(Collectors.joining(", ", "{", "}"));
    }

    public Stream<Entry<String, JsonElement<?>>> stream() {
        return getValue().entrySet().stream();
    }

    @Override
    public Iterator<Entry<String, JsonElement<?>>> iterator() {
        return getValue().entrySet().iterator();
    }

    @Override
    public Optional<JsonElement<?>> tryGet(String jsonPath) {
        return jsonPath.matches(".*[^.]")
               ? Optional.ofNullable(getValue().get(jsonPath))
               : JsonElementIterable.super.tryGet(jsonPath);
    }

    // ==== STATIC FUNCTIONS ================================================= //

    /** @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore. */
    @Deprecated //
    static JsonObject from(com.google.gson.JsonObject jObj) {
        return new JsonObject(new LinkedHashMap<>(jObj.asMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        entry -> Json.treeFromGsonTree(entry.getValue())
                ))));
    }

}