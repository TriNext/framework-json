package de.trinext.framework.json;

/**
 * The classification for a {@link JsonElement} that does not contain other {@link JsonElement}s.
 *
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
abstract sealed class JsonPrimitive<V>
        extends JsonElement<V>
        permits JsonBool, JsonNumber, JsonString, JsonNull
{

    // ==== CONSTRUCTORS ===================================================== //

    JsonPrimitive(V value) {
        super(value);
    }

    static JsonPrimitive<?> from(Object obj) {
        return switch (obj) {
            case null -> JsonNull.NULL;
            case Boolean val -> JsonBool.from((boolean) val);
            case Number val -> JsonNumber.from(val);
            default -> JsonString.from(obj.toString());
        };
    }

}