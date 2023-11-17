package de.trinext.framework.json;

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

    public final boolean findPath(String jsonPath) {
        return tryGetPath(jsonPath).isPresent();
    }

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