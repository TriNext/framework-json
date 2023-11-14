package de.trinext.framework.json;

import java.math.BigInteger;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The json representation of a potentially infinitely big integer.
 *
 * @author Dennis Woithe
 * @see com.google.gson.JsonPrimitive#JsonPrimitive(Number) gson equivalent
 * @see BigInteger java equivalent
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

    @Override
    public String toString() {
        return getValue().toString(10);
    }

}