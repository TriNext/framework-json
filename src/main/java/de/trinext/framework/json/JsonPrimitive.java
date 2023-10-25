package de.trinext.framework.json;

import util.UnexpectedGsonTypeError;

/**
 * @author Dennis Woithe
 */
abstract sealed class JsonPrimitive<V>
        extends JsonElement<V>
        permits JsonBool, JsonNumber, JsonString
{

    // ==== CONSTRUCTORS ===================================================== //

    JsonPrimitive(V value) {
        super(value);
    }

    // ==== STATIC FUNCTIONS ================================================= //

    /** @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore. */
    @Deprecated
    static JsonPrimitive<?> from(com.google.gson.JsonPrimitive jPrim) {
        if (jPrim.isBoolean())
            return JsonBool.from(jPrim);
        if (jPrim.isNumber())
            return JsonNumber.from(jPrim);
        if (jPrim.isString())
            return JsonString.from(jPrim);
        throw new UnexpectedGsonTypeError(jPrim, com.google.gson.JsonPrimitive.class);
    }

}