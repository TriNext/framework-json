package de.trinext.framework.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.google.gson.JsonElement;

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

}