package de.trinext.framework.json.paths;

import java.util.*;

import de.trinext.framework.json.*;

/**
 * @author Dennis Woithe
 */
public sealed interface JsonElementIterable permits JsonObject, JsonArray {

    // ==== METHODS ========================================================== //

    /**
     * Tries to return the element referenced by the jsonPath.
     *
     * @see JsonElementIterable for the description of json paths.
     */
    @SuppressWarnings("UnnecessaryJavaDocLink")
    default Optional<JsonElement<?>> tryGet(String jsonPath) {
        try {
            var iter = jsonElemIterator(jsonPath);
            var elem = (JsonElement<?>) this;
            while (iter.hasNext())
                elem = iter.next();
            return Optional.ofNullable(elem);
        } catch (NoSuchElementException ignored) {
            return Optional.empty();
        }
    }

    /**
     * The returned {@link Iterator} makes it possible to iterate over json paths.
     *
     * @see JsonElementIterable for the description of json paths.
     */
    @SuppressWarnings("UnnecessaryJavaDocLink")
    default JsonElementIterator jsonElemIterator(String jsonPath) {
        return new JsonElementIterator(this, jsonPath);
    }

}