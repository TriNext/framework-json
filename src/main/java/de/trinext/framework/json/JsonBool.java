package de.trinext.framework.json;


/**
 * The json representation of a boolean value.
 *
 * @author Dennis Woithe
 * @see com.google.gson.JsonPrimitive#JsonPrimitive(Boolean) gson equivalent
 */
@SuppressWarnings({"unused", "WeakerAccess", "WeakerAccess"})
public final class JsonBool extends JsonPrimitive<Boolean> {

    public static final JsonBool TRUE = new JsonBool(true), FALSE = new JsonBool(false);

    // ==== CONSTANTS ========================================================>>

    private JsonBool(boolean value) {
        super(value);
    }

    // METHODS ========================================================>>

    public static JsonBool from(boolean value) {
        return value ? TRUE : FALSE;
    }

    @Override
    public String toString() {
        return getValue().toString();
    }

}