package de.trinext.framework.json;

import java.util.Objects;

/**
 * The json representation of no value.
 *
 * @author Dennis Woithe
 * @see com.google.gson.JsonNull gson equivalent
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class JsonNull extends JsonElement<Void> {

    // ==== CONSTANTS ======================================================== //

    public static final JsonNull NULL = new JsonNull();

    // ==== CONSTRUCTORS ===================================================== //

    /** Hidden singleton constructor */
    private JsonNull() {
        super(null);
    }

    // ==== METHODS ========================================================== //

    @Override
    public com.google.gson.JsonElement toGsonElem() {
        return com.google.gson.JsonNull.INSTANCE;
    }

    @Override
    public String toString() {
        return Objects.toString(null);
    }

}