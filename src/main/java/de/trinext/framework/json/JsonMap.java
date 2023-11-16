package de.trinext.framework.json;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.JsonObject;

/**
 * The json representation of a mutable, ordered, string key-value storage of {@link JsonElement}s.
 *
 * @author Dennis Woithe
 * @see JsonObject gson equivalent
 * @see Map java equivalent
 */
@SuppressWarnings({"unused", "WeakerAccess", "CyclicClassDependency"})
public final class JsonMap
        extends JsonContainer<SequencedMap<String, JsonElement<?>>>
        implements Iterable<Entry<String, JsonElement<?>>>
{

    // ==== CONSTRUCTORS ===================================================== //

    /** Creates an empty JsonObject. */
    public JsonMap() {
        this(new LinkedHashMap<>());
    }

    /** Creates an empty JsonObject. */
    public JsonMap(SequencedMap<String, JsonElement<?>> fields) {
        super(fields);
    }

    /**
     * Add a JsonObject as a field.
     *
     * @param fieldName has to be a unique name
     * @param objFieldValue an inner builder function
     */
    @SuppressWarnings("BoundedWildcard")
    public JsonMap addObj(String fieldName, Function<JsonMap, JsonMap> objFieldValue) throws JsonFieldAlreadyExistsException {
        return add(fieldName, objFieldValue.apply(new JsonMap()));
    }

    /**
     * Add anything as a field.
     *
     * @param fieldName has to be a unique name
     * @param value can be any object
     */
    public JsonMap add(String fieldName, Object value) throws JsonFieldAlreadyExistsException {
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
    public JsonMap addArr(
            String fieldName,
            Object... arrFieldValue
    ) throws JsonFieldAlreadyExistsException {
        return add(fieldName, arrFieldValue);
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

    // ==== STATIC FUNCTIONS ================================================= //

    public Optional<JsonElement<?>> tryGet(String fieldName) {
        return Optional.ofNullable(getValue().get(fieldName));
    }

}