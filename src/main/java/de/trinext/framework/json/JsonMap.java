package de.trinext.framework.json;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The json representation of a mutable, ordered, string key-value storage of {@link JsonElement}s.
 *
 * @author Dennis Woithe
 * @see Map java equivalent
 */
@SuppressWarnings({"unused", "WeakerAccess"})
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
     * @param fieldValue can be any object
     */
    public JsonMap add(String fieldName, Object fieldValue) throws JsonFieldAlreadyExistsException {
        if (value.containsKey(Objects.requireNonNull(fieldName)))
            throw new JsonFieldAlreadyExistsException(fieldName);
        value.put(fieldName, Json.treeFromInstance(fieldValue));
        return this;
    }

    /** Returns true if this object has a field with the passed name. */
    public boolean contains(String fieldName) {
        return value.containsKey(Objects.requireNonNull(fieldName));
    }

    public boolean removeKey(String key) {
        return value.remove(key) != null;
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
        return value.entrySet()
                .stream()
                .map(entry -> "\"" + entry.getKey() + "\":" + entry.getValue())
                .collect(Collectors.joining(", ", "{", "}"));
    }

    public Stream<Entry<String, JsonElement<?>>> stream() {
        return value.entrySet().stream();
    }

    @Override
    public Iterator<Entry<String, JsonElement<?>>> iterator() {
        return value.entrySet().iterator();
    }

    // ==== STATIC FUNCTIONS ================================================= //

    public Optional<JsonElement<?>> tryGet(String fieldName) {
        return Optional.ofNullable(value.get(fieldName));
    }


}