package de.trinext.framework.json;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The classification for any {@link JsonElement} that represents a number.
 *
 * @param <N> the type of the number, either {@link BigDecimal} or {@link BigInteger}.
 *
 * @author Dennis Woithe
 * @see Number java equivalent
 */
@SuppressWarnings("WeakerAccess")
public abstract sealed class JsonNumber<N extends Number>
        extends JsonPrimitive<N>
        permits JsonInteger, JsonDecimal
{

    // ==== CONSTRUCTORS ===================================================== //

    JsonNumber(N value) {
        super(value);
    }

    /**
     * Converts any {@link Number} to a {@link JsonNumber}.
     *
     * @throws NumberFormatException if the passed value is a self-implementation of {@link Number},
     * which overwrites {@link #toString()} so that it can no longer be read by {@link BigDecimal#BigDecimal(String)}.
     */
    public static JsonNumber<?> from(Number value) {
        return switch (value) {
            case Byte integer -> JsonInteger.from((byte) integer);
            case Short integer -> JsonInteger.from((short) integer);
            case Integer integer -> JsonInteger.from((int) integer);
            case Long integer -> JsonInteger.from((long) integer);
            case BigInteger integer -> JsonInteger.from(integer);
            case Float decimal -> JsonDecimal.from((float) decimal);
            case Double decimal -> JsonDecimal.from((double) decimal);
            case BigDecimal decimal -> JsonDecimal.from(decimal);
            default -> JsonDecimal.from(new BigDecimal(value.toString()));
        };
    }

    /**
     * Returns this number as a byte.
     * <ul>
     *     <li>If the value doesn't fit into a byte, it gets truncated according to the
     *     <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.</li>
     * </ul>
     */
    public final byte getAsByte() {
        return value.byteValue();
    }

    /**
     * Returns this number as a short.
     * <ul>
     *     <li>If the value doesn't fit into a short, it gets truncated according to the
     *     <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.</li>
     * </ul>
     */
    public final short getAsShort() {
        return value.shortValue();
    }

    /**
     * Returns this number as an int.
     * <ul>
     *     <li>If the value doesn't fit into an int, it gets truncated according to the
     *     <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.</li>
     * </ul>
     */
    public final int getAsInt() {
        return value.intValue();
    }

    /**
     * Returns this number as a long.
     * <ul>
     *     <li>If the value doesn't fit into a long, it gets truncated according to the
     *     <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.</li>
     * </ul>
     */
    public final long getAsLong() {
        return value.longValue();
    }

    /**
     * Returns this number as a float.
     * <ul>
     *     <li>If the value doesn't fit into a float, it gets truncated according to the
     *     <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.</li>
     * </ul>
     */
    public final float getAsFloat() {
        return value.floatValue();
    }

    /**
     * Returns this number as a double.
     * <ul>
     *     <li>If the value doesn't fit into a double, it gets truncated according to the
     *     <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.</li>
     * </ul>
     */
    public final double getAsDouble() {
        return value.doubleValue();
    }

    /**
     * Returns this number as a {@link BigInteger}.
     * <ul>
     *     <li>If this is a {@link JsonDecimal}, the value gets truncated to a {@link BigInteger} using the contract of {@link BigDecimal#toBigInteger()}.</li>
     * </ul>
     */
    public abstract BigInteger getAsBigInt();

    /**
     * Returns this number as a {@link BigDecimal}.
     */
    public abstract BigDecimal getAsBigDec();

    /**
     * Returns this number as a {@link Number}.
     */
    public final Number getAsNumber() {
        return value;
    }

    /** Returns whether this number has decimal places other than {@code 0}. */
    public abstract boolean hasDecimalPlaces();

}