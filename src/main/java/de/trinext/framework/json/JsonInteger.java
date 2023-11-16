package de.trinext.framework.json;

import java.math.BigInteger;

/**
 * The json representation of a potentially infinitely big integer.
 *
 * @author Dennis Woithe
 * @see com.google.gson.JsonPrimitive#JsonPrimitive(Number) gson equivalent
 * @see BigInteger java equivalent
 */
@SuppressWarnings({"unused", "WeakerAccess", "OverloadedMethodsWithSameNumberOfParameters"})
public final class JsonInteger extends JsonNumber<BigInteger> {

    // ==== CONSTRUCTORS ===================================================== //

    private JsonInteger(BigInteger value) {
        super(value);
    }

    // METHODS ========================================================>>

    public static JsonInteger from(byte value) {
        return from((long) value);
    }

    // ==== STATIC FUNCTIONS ================================================= //

    public static JsonInteger from(long value) {
        return from(BigInteger.valueOf(value));
    }

    public static JsonInteger from(BigInteger value) {
        return new JsonInteger(value);
    }

    public static JsonInteger from(short value) {
        return from((long) value);
    }

    public static JsonInteger from(int value) {
        return from((long) value);
    }

    @Override
    public String toString() {
        return getValue().toString(10);
    }

}