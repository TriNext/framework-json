package de.trinext.framework.json;

import com.google.gson.*;
import util.UnexpectedGsonTypeError;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "CyclicClassDependency"})
public final class Json {

    private static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .create();

    private Json() { throw new AssertionError(); }


    // METHODS ========================================================>>

    @SuppressWarnings("WeakerAccess")
    public static JsonElement<?> treeFromString(CharSequence jsonInput) {
        return treeFromGsonTree(gsonTreeFromString(jsonInput.toString()));
    }

    /** @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore. */
    @Deprecated
    static JsonElement<?> treeFromGsonTree(com.google.gson.JsonElement jElem) {
        return switch (jElem) {
            case JsonObject jObj -> JsonMap.from(jObj);
            case JsonArray jArr -> JsonList.from(jArr);
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
     * @see JsonPrimitive#tryFrom(Object) for known primitive types.
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

    /**
     * Converts a {@link JsonElement} into an instance of the given class.
     */
    static <T, V> T instanceFromTree(JsonElement<V> jElem, Class<? extends T> cls) {
        return GSON.fromJson(jElem.toGsonElem(), cls);
    }

}