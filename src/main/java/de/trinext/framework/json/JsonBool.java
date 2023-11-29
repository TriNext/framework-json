package de.trinext.framework.json;


/**
 * The json representation of a boolean value.
 *
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class JsonBool extends JsonPrimitive<Boolean> {

    public static final JsonBool TRUE = new JsonBool(true), FALSE = new JsonBool(false);

    private JsonBool(boolean value) {
        super(value);
    }

    public static JsonBool from(boolean value) {
        return value ? TRUE : FALSE;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}