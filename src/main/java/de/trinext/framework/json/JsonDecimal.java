package de.trinext.framework.json;

import java.math.BigDecimal;

import util.UnexpectedGsonTypeException;

import static de.trinext.framework.json.GsonPrimitiveTypeName.JSON_DECIMAL_TYPE;


/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class JsonDecimal extends JsonNumber<BigDecimal> {

    // ==== CONSTRUCTORS ===================================================== //

    private JsonDecimal(BigDecimal value) {
        super(value);
    }

    // ==== METHODS ========================================================== //

    @Override
    public String toString() {
        return getValue().toPlainString();
    }

    // ==== STATIC FUNCTIONS ================================================= //

    public static JsonDecimal from(float value) {
        return from(new BigDecimal(value));
    }

    public static JsonDecimal from(BigDecimal value) {
        return new JsonDecimal(value);
    }

    public static JsonDecimal from(double value) {
        return from(new BigDecimal(value));
    }

    /** @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore. */
    @Deprecated
    static JsonDecimal from(com.google.gson.JsonPrimitive jPrim) {
        if (!jPrim.isNumber()) // Allow Ints too
            throw new UnexpectedGsonTypeException(jPrim, JSON_DECIMAL_TYPE);
        return from(jPrim.getAsBigDecimal());
    }

}