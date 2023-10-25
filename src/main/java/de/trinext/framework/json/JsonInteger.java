package de.trinext.framework.json;

import java.math.BigInteger;

import util.GsonHelper;
import util.UnexpectedGsonTypeException;

import static de.trinext.framework.json.GsonPrimitiveTypeName.JSON_INTEGER_TYPE;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class JsonInteger extends JsonNumber<BigInteger> {

    // ==== CONSTRUCTORS ===================================================== //

    private JsonInteger(BigInteger value) {
        super(value);
    }

    // ==== METHODS ========================================================== //

    @Override
    public String toString() {
        return getValue().toString(10);
    }

    // ==== STATIC FUNCTIONS ================================================= //

    public static JsonInteger from(byte value) {
        return from((long) value);
    }

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

    /** @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore. */
    @Deprecated
    static JsonInteger from(com.google.gson.JsonPrimitive jPrim) {
        if (!GsonHelper.gsonNrIsInt(jPrim))
            throw new UnexpectedGsonTypeException(jPrim, JSON_INTEGER_TYPE);
        return from(jPrim.getAsBigInteger());
    }

}