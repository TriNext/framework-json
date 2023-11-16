package de.trinext.framework.json;

/**
 * The json representation of a string of characters.
 *
 * @author Dennis Woithe
 * @see com.google.gson.JsonPrimitive#JsonPrimitive(String) gson equivalent
 * @see String java equivalent
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class JsonString extends JsonPrimitive<String> {

    // ==== CONSTRUCTORS ===================================================== //

    private JsonString(CharSequence value) {
        super(value.toString());
    }

    static JsonString from(CharSequence charSeq) {
        return new JsonString(charSeq);
    }

    @Override
    public String toString() {
        return "\"" + getValue() + "\"";
    }

}