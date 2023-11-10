package de.trinext.framework.json;

import java.util.Objects;

/**
 * @param <V> the type of the wrapped value
 *
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract sealed class JsonElement<V> permits JsonContainer, JsonPrimitive, JsonNull {

    private final V value;

    // ==== CONSTRUCTORS ===================================================== //

    JsonElement(V value) {
        this.value = value;
    }

    // ==== METHODS ========================================================== //

    /** Returns the name of the type of this JsonElement. */
    public final String typeName() {
        return getClass().getSimpleName();
    }

    /**
     * Converts this into the respective {@link com.google.gson.JsonElement}.
     *
     * @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore.
     */
    @Deprecated
    abstract com.google.gson.JsonElement toGsonElem();

    @Override
    public final int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public final boolean equals(Object obj) {
        return obj != null
                && getClass() == obj.getClass()
                && Objects.equals(value, ((JsonElement<?>) obj).value);
    }

    @Override
    public abstract String toString();

    /** Returns the wrapped value of this JsonElement. */
    public final V getValue() {
        return value;
    }

}