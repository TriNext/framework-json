package de.trinext.framework.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;
import java.util.Optional;

/**
 * The classification for a {@link JsonElement} that contains other {@link JsonElement}s.
 *
 * @param <V> The type of container
 *
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract sealed class JsonContainer<V> extends JsonElement<V> permits JsonList, JsonMap {

    JsonContainer(V value) {
        super(value);
    }

    /**
     * Tries to return the element referenced by the jsonPath.
     *
     * @see JsonPathFinder for the description of json paths.
     */
    public final Optional<JsonElement<?>> tryGetPath(String jsonPath) {
        return new JsonPathFinder(this, jsonPath).find();
    }

    /**
     * Checks whether the passed jsonPath is present in the Object.
     * @param jsonPath the path to the element to check.
     * @return true if the jsonPath is present in the Object, false otherwise.
     */
    public final boolean findPath(String jsonPath) {
        return tryGetPath(jsonPath).isPresent();
    }

    /**
     * Removes the element referenced by the jsonPath from a JsonList or JsonMap.
     * @param jsonPath the path to the element to remove.
     * @return true if the element was removed, returns false if the jsonPath isn't present in the Object.
     */
    @SuppressWarnings("ClassReferencesSubclass")
    public final boolean removePath(String jsonPath) {
        var finder = new JsonPathFinder(this, jsonPath);
        if (finder.find().isPresent()) {
            var parent = finder.elemPath();
            var strPath = finder.stringPath();
            var lastPathElem = strPath[strPath.length - 1];
            return switch ((JsonContainer<?>) parent[strPath.length - 2]) {
                case JsonList jList -> jList.removeAt(Integer.parseInt(lastPathElem));
                case JsonMap jMap -> jMap.removeKey(lastPathElem);
            };
        }
        return false;
    }

}