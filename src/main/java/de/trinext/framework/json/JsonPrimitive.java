package de.trinext.framework.json;

import util.UnexpectedGsonTypeError;

/**
 * The classification for a {@link JsonElement} that does not contain other {@link JsonElement}s.
 *
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess", "CyclicClassDependency"})
abstract sealed class JsonPrimitive<V>
        extends JsonElement<V>
        permits JsonBool, JsonNumber, JsonString
{

    // ==== CONSTRUCTORS ===================================================== //

    JsonPrimitive(V value) {
        super(value);
    }


    // METHODS ========================================================>>

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

    static JsonPrimitive<?> tryFrom(Object obj) {
        return switch (obj) {
            case Boolean val -> JsonBool.from(val);
            case Number val -> JsonNumber.from(val);
            case CharSequence val -> JsonString.from(val);
            default -> throw new IllegalArgumentException("Received instance of unexpected type \"" + obj.getClass() + "\". Pass a json-primitive instead.");
        };
    }

}