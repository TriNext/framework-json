package de.trinext.framework.json;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The json representation of a potentially infinitely big integer.
 *
 * @author Dennis Woithe
 * @see BigInteger java equivalent
 */
@SuppressWarnings({"unused", "WeakerAccess", "OverloadedMethodsWithSameNumberOfParameters"})
public final class JsonInteger extends JsonNumber<BigInteger> {

    // ==== CONSTRUCTORS ===================================================== //

    private JsonInteger(BigInteger value) {
        super(value);
    }

    public static JsonInteger from(byte value) {
        return from((long) value);
    }

    public static JsonInteger from(short value) {
        return from((long) value);
    }

    public static JsonInteger from(int value) {
        return from((long) value);
    }

    public static JsonInteger from(long value) {
        return from(BigInteger.valueOf(value));
    }

    public static JsonInteger from(BigInteger value) {
        return new JsonInteger(value);
    }

    /** Returns this number as a {@link BigInteger}. */
    @Override
    public BigInteger getAsBigInt() {
        return value;
    }

    @Override
    public BigDecimal getAsBigDec() {
        return convert(BigDecimal::new, BigDecimal.class);
    }

    /**
     * Always returns {@code false} as this method is only relevant for
     * instances of {@link JsonNumber} without a more concretely specified subtype.
     */
    @Override
    public boolean hasDecimalPlaces() {
        return false;
    }

    @Override
    public String toString() {
        return value.toString(10);
    }

}