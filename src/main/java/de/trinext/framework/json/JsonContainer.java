package de.trinext.framework.json;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

/**
 * The classification for a {@link JsonElement} that contains other {@link JsonElement}s.
 *
 * @param <V> The type of container
 *
 * @author Dennis Woithe
 */
public abstract sealed class JsonContainer<V> extends JsonElement<V> permits JsonList, JsonMap {

    JsonContainer(@Nullable V value) {
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

}