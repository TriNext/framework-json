package de.trinext.framework.json;

import java.util.Objects;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract sealed class JsonElement<V> permits JsonArray, JsonObject, JsonPrimitive, JsonNull {

    private final V value;

    // ==== CONSTRUCTORS ===================================================== //

    JsonElement(V value) {
        this.value = value;
    }

    // ==== METHODS ========================================================== //

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

    public abstract String toString();

    public final V getValue() {
        return value;
    }

}