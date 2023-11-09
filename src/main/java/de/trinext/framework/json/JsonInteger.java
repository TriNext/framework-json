package de.trinext.framework.json;

import java.math.BigInteger;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import util.GsonHelper;
import util.UnexpectedGsonTypeException;

import static de.trinext.framework.json.GsonPrimitiveTypeName.JSON_INTEGER_TYPE;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess", "WeakerAccess", "CyclicClassDependency", "OverloadedMethodsWithSameNumberOfParameters"})
public final class JsonInteger extends JsonNumber<BigInteger> {

    // ==== CONSTRUCTORS ===================================================== //

    @Contract(pure = true)
    private JsonInteger(BigInteger value) {
        super(value);
    }

    // METHODS ========================================================>>

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static JsonInteger from(byte value) {
        return from((long) value);
    }

    // ==== STATIC FUNCTIONS ================================================= //

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static JsonInteger from(long value) {
        return from(BigInteger.valueOf(value));
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static JsonInteger from(BigInteger value) {
        return new JsonInteger(value);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static JsonInteger from(short value) {
        return from((long) value);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static JsonInteger from(int value) {
        return from((long) value);
    }

    /** @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore. */
    @Deprecated
    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static JsonInteger from(com.google.gson.JsonPrimitive jPrim) {
        if (!GsonHelper.gsonNrIsInt(jPrim))
            throw new UnexpectedGsonTypeException(jPrim, JSON_INTEGER_TYPE);
        return from(jPrim.getAsBigInteger());
    }

    @Override
    public String toString() {
        return getValue().toString(10);
    }

}