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

    /**
     * Turns a float into a {@link JsonDecimal}.
     *
     * @param value the float to be converted.
     * @return the {@link JsonDecimal} representing the float.
     */
    public static JsonDecimal from(float value) {
        return from(new BigDecimal(value));
    }

    /**
     * Turns a BigDecimal into a {@link JsonDecimal}.
     *
     * @param value the BigDecimal to be converted.
     * @return the {@link JsonDecimal} representing the BigDecimal.
     */
    public static JsonDecimal from(BigDecimal value) {
        return new JsonDecimal(value);
    }

    /**
     * Turns a double into a {@link JsonDecimal}.
     *
     * @param value the double to be converted.
     * @return the {@link JsonDecimal} representing the double.
     */
    public static JsonDecimal from(double value) {
        return from(new BigDecimal(value));
    }


    /**
     * Returns the string representation of this {@link JsonDecimal}.
     *
     * @return the string value of this {@link JsonDecimal}.
     */
    @Override
    public String toString() {
        return value.toPlainString();
    }

}