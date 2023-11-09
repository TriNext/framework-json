package de.trinext.framework.json.paths;

import java.util.Iterator;
import java.util.Optional;

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
    public default Optional<JsonElement<?>> tryGetPath(String jsonPath) {
        return jsonElemIterator(jsonPath).find();
    }

    /**
     * The returned {@link Iterator} makes it possible to iterate over json paths.
     *
     * @see JsonElementIterable for the description of json paths.
     */
    @SuppressWarnings("UnnecessaryJavaDocLink")
    default JsonPathFinder jsonElemIterator(String jsonPath) {
        return new JsonPathFinder(this, jsonPath);
    }

}