package de.trinext.framework.json;

import util.UnexpectedGsonTypeException;

import static de.trinext.framework.json.GsonPrimitiveTypeName.JSON_STRING_TYPE;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class JsonString extends JsonPrimitive<CharSequence> {

    // ==== CONSTRUCTORS ===================================================== //

    private JsonString(CharSequence value) {
        super(value);
    }

    // ==== METHODS ========================================================== //

    @Override
    public com.google.gson.JsonPrimitive toGsonElem() {
        return new com.google.gson.JsonPrimitive(getValue().toString());
    }

    @Override
    public String toString() {
        return "\"" + getValue() + "\"";
    }

    // ==== STATIC FUNCTIONS ================================================= //

    /** @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore. */
    @Deprecated
    static JsonString from(com.google.gson.JsonPrimitive jPrim) {
        if (!jPrim.isString())
            throw new UnexpectedGsonTypeException(jPrim, JSON_STRING_TYPE);
        return new JsonString(jPrim.getAsString());
    }

    static JsonString from(CharSequence charSeq) {
        return new JsonString(charSeq);
    }

}