package de.trinext.framework.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.google.gson.JsonElement;
import util.GsonHelper;

/**
 * The classification for any {@link JsonElement} that represents a number.
 *
 * @param <N> the type of the number, either {@link BigDecimal} or {@link BigInteger}.
 *
 * @author Dennis Woithe
 * @see com.google.gson.JsonPrimitive#JsonPrimitive(Number) gson equivalent
 * @see Number java equivalent
 */
@SuppressWarnings({"WeakerAccess", "CyclicClassDependency"})
public abstract sealed class JsonNumber<N extends Number>
        extends JsonPrimitive<N>
        permits JsonInteger, JsonDecimal
{

    // ==== CONSTRUCTORS ===================================================== //

    JsonNumber(N value) {
        super(value);
    }

    // METHODS ========================================================>>

    /** @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore. */
    @Deprecated
    static JsonNumber<?> from(com.google.gson.JsonPrimitive jPrim) {
        return GsonHelper.gsonNrIsInt(jPrim)
               ? JsonInteger.from(jPrim)
               : JsonDecimal.from(jPrim);
    }

    // ==== STATIC FUNCTIONS ================================================= //

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

    @Override
    public JsonElement toGsonElem() {
        return new com.google.gson.JsonPrimitive(getValue());
    }

}