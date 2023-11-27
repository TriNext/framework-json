package de.trinext.framework.json;


import java.math.BigDecimal;

/**
 * The json representation of a potentially infinitely precise decimal number.
 *
 * @author Dennis Woithe
 * @see BigDecimal java equivalent
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class JsonDecimal extends JsonNumber<BigDecimal> {

    // ==== CONSTRUCTORS ===================================================== //

    private JsonDecimal(BigDecimal value) {
        super(value);
    }

    // METHODS ========================================================>>

    public static JsonDecimal from(float value) {
        return from(new BigDecimal(value));
    }

    public static JsonDecimal from(BigDecimal value) {
        return new JsonDecimal(value);
    }

    public static JsonDecimal from(double value) {
        return from(new BigDecimal(value));
    }

    @Override
    public String toString() {
        return value.toPlainString();
    }

}