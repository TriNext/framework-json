package de.trinext.framework.json;

import com.google.gson.*;
import util.UnexpectedGsonTypeError;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings("unused")
public final class Json {

    private static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .create();

    // ==== CONSTRUCTORS ===================================================== //

    private Json() { }

    // ==== STATIC FUNCTIONS ================================================= //

    @SuppressWarnings("WeakerAccess")
    public static JsonElement<?> treeFromString(CharSequence jsonInput) {
        return treeFromGsonTree(gsonTreeFromString(jsonInput.toString()));
    }

    /** @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore. */
    @Deprecated
    static JsonElement<?> treeFromGsonTree(com.google.gson.JsonElement jElem) {
        return switch (jElem) {
            case com.google.gson.JsonObject jObj -> new JsonObject(jObj);
            case com.google.gson.JsonArray jArr -> new JsonArray(jArr);
            case com.google.gson.JsonNull jNull -> JsonNull.NULL;
            case com.google.gson.JsonPrimitive jPrim -> JsonPrimitive.from(jPrim);
            default -> throw new UnexpectedGsonTypeError(jElem, com.google.gson.JsonElement.class);
        };
    }

    /** @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore. */
    @Deprecated
    static com.google.gson.JsonElement gsonTreeFromString(CharSequence jsonInput) {
        return JsonParser.parseString(jsonInput.toString());
    }

    /**
     * Converts any Object into a tree of {@link JsonElement}s.
     *
     * @see JsonPrimitive#from for definite primitive types
     */
    @SuppressWarnings("WeakerAccess")
    public static JsonElement<?> treeFromInstance(Object value) {
        return value instanceof JsonElement<?> jElem
               ? jElem
               : treeFromGsonTree(gsonTreeFromInstance(value));
    }

    /** @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore. */
    @Deprecated
    static com.google.gson.JsonElement gsonTreeFromInstance(Object value) {
        return new Gson().toJsonTree(value);
    }

}