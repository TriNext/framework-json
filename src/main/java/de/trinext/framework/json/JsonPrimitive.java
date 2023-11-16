package de.trinext.framework.json;

/**
 * The classification for a {@link JsonElement} that does not contain other {@link JsonElement}s.
 *
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
abstract sealed class JsonPrimitive<V>
        extends JsonElement<V>
        permits JsonBool, JsonNumber, JsonString
{

    // ==== CONSTRUCTORS ===================================================== //

    JsonPrimitive(V value) {
        super(value);
    }

    static JsonPrimitive<?> tryFrom(Object obj) {
        return switch (obj) {
            case Boolean val -> JsonBool.from(val);
            case Number val -> JsonNumber.from(val);
            case CharSequence val -> JsonString.from(val);
            default -> throw new IllegalArgumentException("Received instance of unexpected type \"" + obj.getClass() + "\". Pass a json-primitive instead.");
        };
    }

}