package de.trinext.framework.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.google.gson.JsonElement;
import util.GsonHelper;

// INNER TYPES ========================================================>>

/**
 * @param <N> is the type of backing {@link Number}
 *
 * @author Dennis Woithe
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