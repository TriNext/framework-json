package de.trinext.framework.json;

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

}