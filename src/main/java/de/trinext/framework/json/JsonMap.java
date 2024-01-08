package de.trinext.framework.json;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.trinext.framework.util.lang.ClassChecker;
import de.trinext.framework.util.lang.ClassHelper;

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

    /** Creates an empty JsonMap. */
    public JsonMap() {
        this(new LinkedHashMap<>());
    }

    @SuppressWarnings("BoundedWildcard")
    public JsonMap(Map<String, JsonElement<?>> fields) {
        super(new LinkedHashMap<>(fields));
    }

    /**
     * Creates a JsonMap from the given object.
     *
     * @param obj an object or record instance.
     * @throws IllegalArgumentException if the given object is null, an array, enum or primitive
     */
    public static JsonMap from(Object obj) {
        if (obj == null)
            throw new IllegalArgumentException("obj can not be null!");
        var objCls = obj.getClass();
        var map = new JsonMap();
        new ClassChecker<>(objCls, IllegalArgumentException::new)
                .isNotEnum().isNotArray().isNotPrimitive()
                .map(ClassHelper::allFieldsOf)
                .filter(f -> !f.isSynthetic())
                .peek(f -> f.setAccessible(true))
                .forEach(f -> {
                    try {
                        map.add(f.getName(), f.get(obj));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        return map;
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

    public boolean contains(String key) {
        if (key == null)
            throw new IllegalArgumentException("key can not be null!");
        return value.containsKey(Objects.requireNonNull(key));
    }

    public boolean removeKey(String key) {
        return value.remove(key) != null;
    }

    public JsonMap addList(String key, Object... values) throws JsonFieldAlreadyExistsException {
        return add(key, JsonList.from(values));
    }

    public JsonMap addList(String key, byte[] values) throws JsonFieldAlreadyExistsException {
        return add(key, JsonList.from(values));
    }

    public JsonMap addList(String key, short[] values) throws JsonFieldAlreadyExistsException {
        return add(key, JsonList.from(values));
    }

    public JsonMap addList(String key, int[] values) throws JsonFieldAlreadyExistsException {
        return add(key, JsonList.from(values));
    }

    public JsonMap addList(String key, long[] values) throws JsonFieldAlreadyExistsException {
        return add(key, JsonList.from(values));
    }

    public JsonMap addList(String key, float[] values) throws JsonFieldAlreadyExistsException {
        return add(key, JsonList.from(values));
    }

    public JsonMap addList(String key, double[] values) throws JsonFieldAlreadyExistsException {
        return add(key, JsonList.from(values));
    }

    public JsonMap addList(String key, boolean[] values) throws JsonFieldAlreadyExistsException {
        return add(key, JsonList.from(values));
    }

    public JsonMap addList(String key, char[] values) throws JsonFieldAlreadyExistsException {
        return add(key, JsonList.from(values));
    }

    public JsonMap addList(String key, Iterable<?> values) throws JsonFieldAlreadyExistsException {
        return add(key, JsonList.from(values));
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

    public Optional<JsonElement<?>> tryGet(String fieldName) {
        return Optional.ofNullable(value.get(fieldName));
    }

}