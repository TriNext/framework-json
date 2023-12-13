package de.trinext.framework.json;

import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.charset.Charset;
import java.util.stream.Collector;

import com.google.gson.Gson;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
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

    /**
     * A {@link BodyHandler} that converts the response body of a json-response to a tree of {@link JsonElement}s.
     */
    public static BodyHandler<JsonElement<?>> treeBodyHandler() {
        return treeBodyHandler(Charset.defaultCharset());
    }

    /**
     * A {@link BodyHandler} that converts the response body of a json-response to an instance of the given class.
     */
    public static <T> BodyHandler<T> instanceBodyHandler(Class<? extends T> cls) {
        return instanceBodyHandler(Charset.defaultCharset(), cls);
    }


    /**
     * A {@link BodyHandler} that converts the response body of a json-response to a tree of {@link JsonElement}s.
     *
     * @param responseBodyCharset The charset to use for the response body.
     */
    public static BodyHandler<JsonElement<?>> treeBodyHandler(Charset responseBodyCharset) {
        return (responseInfo) -> BodySubscribers.mapping(
                BodySubscribers.ofString(responseBodyCharset),
                Json::treeFromString
        );
    }

    /**
     * A {@link BodyHandler} that converts the response body of a json-response to an instance of the given class.
     *
     * @param responseBodyCharset The charset to use for the response body.
     * @param cls The class to convert the json to.
     */
    public static <T> BodyHandler<T> instanceBodyHandler(Charset responseBodyCharset, Class<? extends T> cls) {
        return (responseInfo) -> BodySubscribers.mapping(
                BodySubscribers.ofString(responseBodyCharset),
                (jsonString) -> new Gson().fromJson(jsonString, cls)
        );
    }

    /**
     * A {@link Collector} that collects {@link JsonElement}s to a {@link JsonList}.
     */
    public static Collector<JsonElement<?>, JsonList, JsonList> collector() {
        return Collector.of(JsonList::new, JsonList::add, JsonList::addAll);
    }

}