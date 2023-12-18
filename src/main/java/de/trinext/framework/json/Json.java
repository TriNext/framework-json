package de.trinext.framework.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.charset.Charset;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.regex.Pattern;
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

    /** Converts any instance to a tree of {@link JsonElement}s. */
    public static JsonElement<?> treeFromInstance(Object obj) {
        return obj instanceof JsonElement<?> jsonElement
               ? jsonElement
               : new JsonObjectParser(obj).parse();
    }

    /**
     * Converts a JSON-{@link String} to an instance of the specified class, enum or record.
     *
     * @see #treeFromString(String)
     * @see #instanceFromTree(JsonElement, Class)
     */
    public static <T> T instanceFromString(String jsonString, Class<T> cls) {
        return instanceFromTree(treeFromString(jsonString), cls);
    }

    /**
     * Converts tree of {@link JsonElement}s into a concrete instance of the specified class, enum or record without calling any constructor.
     * <hr>
     * <h3>Conversion of non-POJOs</h3>
     *
     * <h4>Conversion of {@link JsonBool}</h4>
     * <ul style="margin-top: 0; margin-bottom: 0">
     *     <li>Conversion of {@link JsonBool} to {@link Boolean}.</li>
     * </ul>
     *
     * <h4>Conversion of {@link JsonNumber}</h4>
     * <ul style="margin-top: 0; margin-bottom: 0">
     *     <li>
     *         Possibly lossy conversion of {@link JsonNumber} to the primitive wrapper types.
     *         If the value doesn't fit, it gets truncated according to the
     *         <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.
     *     </li>
     *     <li>
     *         Possibly lossy conversion of {@link JsonNumber} to {@link BigInteger}
     *         If the value is a decimal, it gets truncated according to the contract of {@link BigDecimal#toBigInteger()}.
     *     </li>
     *     <li>Lossless conversion of {@link JsonNumber} to {@link BigDecimal}</li>
     * </ul>
     *
     * <h4>Conversion of {@link JsonString}</h4>
     * <ul style="margin-top: 0; margin-bottom: 0">
     *     <li>Conversion of {@link JsonString} to {@link String}.</li>
     *     <li>
     *         Lossless conversion of {@link JsonString} to {@link Character} if the length is 1.
     *         If the length is not 1, a {@link IllegalStateException} is thrown.
     *     </li>
     *     <li>
     *         Conversion of {@link JsonString} to subclasses of {@link Enum} using {@link Enum#valueOf(Class, String)}.
     *         If the enum constant doesn't exist, a {@link IllegalStateException} is thrown.
     *     </li>
     *     <li>Conversion of {@link JsonString} to {@link UUID} using {@link UUID#fromString(String)}.</li>
     *     <li>Conversion of {@link JsonString} to {@link Pattern} using {@link Pattern#compile(String)}.</li>
     *
     *     <li>
     *         Conversion of {@link JsonList} to arrays.
     *         If the passed {@code cls} is an array type, this function and its contract get invoked for the respective element type.
     *     </li>
     * </ul>
     *
     * <h4>Conversion of {@link JsonNull}</h4>
     * <ul style="margin-top: 0; margin-bottom: 0">
     *     <li>Returns {@code null} for {@link JsonNull} irregardless of the passed class.</li>
     * </ul>
     *
     * <hr>
     * <h3>Missing keys</h3>
     * If {@code jElem} is a {@link JsonMap}, that doesn't contain a key for a field of the passed class,
     * that field is set to its default value:
     * <p>
     * See <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-4.html#jls-4.12.5">the specification for default values.</a>
     * <ul style="margin-top: 0; margin-bottom: 0">
     *     <li>0 for all primitive numeric types.</li>
     *     <li>{@code false} for {@code boolean}.</li>
     *     <li>{@code '\0'} for {@code char}.</li>
     *     <li>
     *         {@code null} for all reference types (including primitive wrapper types and {@link String}) with the following exceptions:
     *         <ul style="margin-top: 0; margin-bottom: 0">
     *             <li>{@link Collections#emptyList()} for subclasses of {@link List}.</li>
     *             <li>{@link Collections#emptySet()} for subclasses of {@link Set}.</li>
     *             <li>{@link Collections#emptyMap()} for subclasses of {@link Map}.</li>
     *             <li>{@link Optional#empty()} for {@link Optional}.</li>
     *             <li>{@link OptionalDouble#empty()} for {@link OptionalDouble}.</li>
     *             <li>{@link OptionalInt#empty()} for {@link OptionalInt}.</li>
     *             <li>{@link OptionalLong#empty()} for {@link OptionalLong}.</li>
     *             <li>{@link Optional#empty()} for {@link Optional}.</li>
     *         </ul>
     *     </li>
     * </ul>
     *
     * <hr>
     * <h3>Too many keys</h3>
     * <p>
     *     If {@code jElem} is a {@link JsonMap}, that contains a key which doesn't correspond to a field of the passed class,
     *     nothing gets thrown, following the "tolerant reader pattern".
     * </p>
     *
     * <hr>
     * <h3>Passing of types that require generic parameters</h3>
     * <ul>
     *     <li>
     *         Because of <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-4.html#jls-4.6">type erasure</a>,
     *         this function is incapable of instantiating types that directly require generic parameters such as {@link List} or {@link Map}.
     *         </li>
     *     <li>If the passed {@code cls} contains fields (recursively) that require generic parameters, these can be instantiated.</li>
     * </ul>
     *
     * <hr>
     * <h3>Passing of incompatible classes</h3>
     * <ul>
     *     <li>If the passed {@code cls} is a primitive type, it is converted to its wrapper type.</li>
     *     <li>
     *         If the passed {@code cls} is {@code abstract} or an {@code interface}, an {@link IllegalArgumentException} is thrown with the following exceptions:
     *         <ul style="margin-top: 0; margin-bottom: 0">
     *             <li>{@link Number} gets instantiated as {@link BigDecimal}.</li>
     *             <li>{@link CharSequence} gets instantiated as {@link String}.</li>
     *         </ul>
     *         and for fields also:
     *         <ul style="margin-top: 0; margin-bottom: 0">
     *             <li>{@link List} gets instantiated as {@link ArrayList}.</li>
     *             <li>{@link Set} gets instantiated as {@link HashSet}.</li>
     *             <li>{@link SequencedSet} gets instantiated as {@link LinkedHashSet}.</li>
     *             <li>{@link Map} gets instantiated as {@link HashMap}.</li>
     *             <li>{@link SequencedMap} gets instantiated as {@link LinkedHashMap}.</li>
     *         </ul>
     *     </li>
     * </ul>
     * <hr>
     * <h3>Passing of inaccessible classes</h3>
     * <ul style="margin-top: 0; margin-bottom: 0">
     *     <li>Throws an {@link IllegalAccessError} if {@code cls} isn't opened by it's module.</li>
     *     <li>If the the module is accessible, this function is capable of instantiating private classes.</li>
     *     <li>This function is capable of modifying private fields.</li>
     *     <li>Instantiating non static inner classes is currently not supported.</li>
     * </ul>
     * <p>
     * TODO: Add support for {@link Date} and subclasses of {@link TemporalAccessor}.
     */
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