package de.trinext.framework.json;

import com.google.gson.*;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "CyclicClassDependency", "WeakerAccess"})
public final class Json {

    private Json() { throw new AssertionError(); }

    /** Converts a JSON-{@link String} to a tree of {@link JsonElement}s. */
    public static JsonElement<?> treeFromString(String jsonString) {
        return new JsonTextParser(jsonString).parse();
    }

    /** Converts any {@link Object}-instance to a tree of {@link JsonElement}s. */
    public static JsonElement<?> treeFromInstance(Object obj) {
        return obj instanceof JsonElement<?> jsonElement
               ? jsonElement
               : new JsonObjectParser(obj).parse();
    }

    public static <T> T instanceFromTree(JsonElement<?> jElem, Class<T> cls) {
        return new Gson().fromJson(jElem.toString(), cls);
    }

}