package de.trinext.framework.json;

import util.UnexpectedGsonTypeException;

import static de.trinext.framework.json.GsonPrimitiveTypeName.JSON_BOOLEAN_TYPE;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class JsonBool extends JsonPrimitive<Boolean> {

    // ==== CONSTANTS ======================================================== //

    public static final JsonBool TRUE = new JsonBool(true), FALSE = new JsonBool(false);

    // ==== CONSTRUCTORS ===================================================== //

    private JsonBool(boolean value) {
        super(value);
    }

    // ==== METHODS ========================================================== //

    @Override
    public com.google.gson.JsonPrimitive toGsonElem() {
        return new com.google.gson.JsonPrimitive(getValue());
    }

    @Override
    public String toString() {
        return getValue().toString();
    }

    // ==== STATIC FUNCTIONS ================================================= //

    public static JsonBool from(boolean value) {
        return value ? TRUE : FALSE;
    }

    /** @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore. */
    @Deprecated
    public static JsonBool from(com.google.gson.JsonPrimitive jPrim) {
        if (!jPrim.isBoolean())
            throw new UnexpectedGsonTypeException(jPrim, JSON_BOOLEAN_TYPE);
        return jPrim.getAsBoolean() ? TRUE : FALSE;
    }


}