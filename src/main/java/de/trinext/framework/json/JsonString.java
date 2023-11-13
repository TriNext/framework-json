package de.trinext.framework.json;

import util.UnexpectedGsonTypeException;

import static de.trinext.framework.json.GsonPrimitiveTypeName.JSON_STRING_TYPE;

/**
 * The json representation of a string of characters.
 *
 * @author Dennis Woithe
 * @see com.google.gson.JsonPrimitive#JsonPrimitive(String) gson equivalent
 * @see String java equivalent
 */
@SuppressWarnings({"unused", "WeakerAccess", "CyclicClassDependency"})
public final class JsonString extends JsonPrimitive<String> {

    // ==== CONSTRUCTORS ===================================================== //

    private JsonString(CharSequence value) {
        super(value.toString());
    }

    // METHODS ========================================================>>

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

    // ==== STATIC FUNCTIONS ================================================= //

    @Override
    public com.google.gson.JsonPrimitive toGsonElem() {
        return new com.google.gson.JsonPrimitive(getValue());
    }

    @Override
    public String toString() {
        return "\"" + getValue() + "\"";
    }

}