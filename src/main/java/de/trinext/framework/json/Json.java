package de.trinext.framework.json;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.charset.Charset;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collector;

import de.trinext.framework.util.lang.ReflectionHelper;

import static de.trinext.framework.json.JsonBool.FALSE;
import static de.trinext.framework.json.JsonBool.TRUE;
import static de.trinext.framework.json.JsonNull.NULL;
import static java.util.HexFormat.isHexDigit;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class Json {

    /** The Exception-provider if a conversion fails. */
    public static final BiFunction<JsonElement<?>, Class<?>, IllegalArgumentException> CANT_CONVERT =
            (e, c) -> new IllegalArgumentException("Can't convert " + e + " to " + c + ".");

    /** The Exception-provider if a conversion fails for a specific index. */
    public static final BiFunction<Integer, Class<?>, IllegalArgumentException> CANT_CONVERT_AT_IDX =
            (i, c) -> new IllegalArgumentException("Can't convert element at index " + i + " to " + c + ".");

    public static final BiFunction<String, Integer, IllegalArgumentException> UNEXPECTED_CHAR_AT =
            (s, i) -> new IllegalArgumentException("Expected " + s + " at index " + i);

    public static final Function<Integer, IllegalArgumentException> UNEXPECTED_END_AT =
            (i) -> new IllegalArgumentException("Unexpected end of string at index " + i + ".");

    private Json() { throw new AssertionError(); }

    /**
     * Converts a JSON-{@link String} to a tree of {@link JsonElement}s.
     *
     * @return {@link JsonNull} if the string is empty.
     *
     * @throws IllegalArgumentException if the string is null or not valid JSON.
     */
    public static JsonElement<?> treeFromString(String jsonString) {
        // An implementation of the diagram from https://www.json.org/json-en.html.
        @SuppressWarnings({"MagicCharacter", "HardCodedStringLiteral", "DuplicateStringLiteralInspection"}) //
        final class JsonTreeFromString {

            private final String str;
            private int idx = 0;

            JsonTreeFromString(String jsonString) {
                this.str = jsonString;
            }

            private boolean charLeft() {
                return idx < str.length();
            }

            private char peek() {
                if (charLeft())
                    return str.charAt(idx);
                throw UNEXPECTED_END_AT.apply(idx);
            }

            private char read() {
                if (charLeft())
                    return str.charAt(idx++);
                throw UNEXPECTED_END_AT.apply(idx);
            }

            private String read(int i) {
                var s = str.substring(idx, idx + i);
                idx += i;
                return s;
            }

            private String readDigits() {
                var fstIdx = idx;
                while (charLeft() && "0123456789".indexOf(peek()) != -1)
                    read();
                if (idx == fstIdx)
                    throw UNEXPECTED_CHAR_AT.apply("a digit (0-9)", idx);
                return str.substring(fstIdx, idx);
            }

            private void readSpace() {
                while (charLeft() && Character.isWhitespace(peek()))
                    read(); // space
            }

            private JsonElement<?> parse() {
                readSpace();
                var val = switch (str.charAt(idx)) {
                    case '[' -> parseJsonList();
                    case '{' -> parseJsonMap();
                    case '"' -> parseJsonString();
                    case 't' -> parseJsonBoolTrue();
                    case 'f' -> parseJsonBoolFalse();
                    case 'n' -> parseJsonNull();
                    default -> parseJsonNumber();
                };
                readSpace();
                return val;
            }

            private JsonMap parseJsonMap() {
                read(); // {
                var map = new JsonMap();
                readSpace();
                while (peek() != '}') {
                    var key = parseJsonString();
                    readSpace();
                    if (peek() != ':')
                        throw UNEXPECTED_CHAR_AT.apply("':'", idx);
                    read(); // :
                    var value = parse();
                    map.add(key.getAsString(), value);
                }
                read(); // }
                return map;
            }

            @SuppressWarnings("OverlyLongMethod")
            private JsonString parseJsonString() {
                read(); // "
                var sb = new StringBuilder();
                while (str.charAt(idx) != '"') {
                    if (peek() == '\\') {
                        read(); // \
                        switch (peek()) { // @formatter:off
                            case '"'  -> { sb.append('"');  read(); }
                            case '\\' -> { sb.append('\\'); read(); }
                            case '/'  -> { sb.append('/');  read(); }
                            case 'b'  -> { sb.append('\b'); read(); }
                            case 'f'  -> { sb.append('\f'); read(); }
                            case 'n'  -> { sb.append('\n'); read(); }
                            case 'r'  -> { sb.append('\r'); read(); }
                            case 't'  -> { sb.append('\t'); read(); }
                            case 'u'  ->   sb.append(parseUnicode());
                            default -> throw new IllegalArgumentException("Invalid escape sequence at index " + idx + ": '\\" + peek() + "'.");
                        } // @formatter:on
                    } else
                        sb.append(read());
                }
                read(); // "
                return JsonString.from(sb.toString());
            }

            @SuppressWarnings({"NumericCastThatLosesPrecision", "MagicNumber"})
            private char parseUnicode() {
                read(); // u
                var sb = new StringBuilder();
                for (var i = 0; i < 4; i++) {
                    if (!isHexDigit(peek()))
                        throw new IllegalArgumentException("Invalid unicode escape sequence at index " + idx + ": '\\u" + sb + "'.");
                    sb.append(read());
                }
                // https://stackoverflow.com/a/16769863/18197654
                return (char) Integer.parseInt(sb.toString(), 16);
            }

            private JsonList parseJsonList() {
                read(); // [
                var list = new JsonList();
                readSpace();
                while (peek() != ']') {
                    list.add(parse());
                    if (peek() == ',')
                        read(); // ,
                    else if (peek() != ']')
                        throw UNEXPECTED_CHAR_AT.apply("',' or ']'", idx);
                }
                return list;
            }

            private JsonBool parseJsonBoolTrue() {
                if ("true".equals(read(4)))
                    return TRUE;
                throw UNEXPECTED_CHAR_AT.apply("'true'", idx);
            }

            private JsonBool parseJsonBoolFalse() {
                if ("false".equals(read(5)))
                    return FALSE;
                throw UNEXPECTED_CHAR_AT.apply("'false'", idx);
            }

            private JsonNull parseJsonNull() {
                if ("null".equals(read(4)))
                    return NULL;
                throw UNEXPECTED_CHAR_AT.apply("'null'", idx);
            }

            private JsonNumber<?> parseJsonNumber() {
                var sb = new StringBuilder();
                var isDecimal = false;
                if (peek() == '-')
                    sb.append(read()); // -
                sb.append(readDigits()); // int
                if (charLeft() && peek() == '.') { // Fraction
                    sb.append(read()); // .
                    sb.append(readDigits());
                    isDecimal = true;
                }
                if (charLeft() && (peek() == 'e' || peek() == 'E')) { // Exponent
                    sb.append(read()); // e
                    if (peek() == '+' || peek() == '-')
                        sb.append(read()); // +/-
                    sb.append(readDigits());
                    isDecimal = true;
                }
                JsonNumber<?> res = isDecimal
                                    ? JsonDecimal.from(new BigDecimal(sb.toString()))
                                    : JsonInteger.from(new BigInteger(sb.toString()));
                return isDecimal && !res.hasDecimalPlaces()
                       ? JsonInteger.from(res.getAsBigInt())
                       : res;
            }

        }
        if (jsonString == null)
            throw new IllegalArgumentException("Can't convert null to JsonElement.");
        return jsonString.isBlank()
               ? NULL
               : new JsonTreeFromString(jsonString).parse();
    }

    /** Converts any instance to a tree of {@link JsonElement}s. */
    public static JsonElement<?> treeFromInstance(Object obj) {
        return switch (obj) {
            case null -> NULL;
            case JsonElement<?> jElem -> jElem;

            case Boolean bl -> JsonBool.from((boolean) bl);
            case Number nr -> JsonNumber.from(nr);

            case Character ch -> JsonString.from((char) ch);
            case CharSequence chs -> JsonString.from(chs);
            case Pattern pattern -> JsonString.from(pattern.pattern());
            case UUID uuid -> JsonString.from(uuid.toString());
            case Enum<?> enm -> JsonString.from(enm.name());

            case Iterable<?> iter -> JsonList.from(iter);

            default -> obj.getClass().isArray()
                       ? JsonList.from(obj)
                       : JsonMap.from(obj);
        };
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
     * <h4>Conversion of {@link JsonNull}</h4>
     * Converts {@link JsonNull} to {@code null} with the following exceptions:
     * <ul style="margin-top: 0; margin-bottom: 0">
     *     <li>{@link Optional#empty()} for {@link Optional}.</li>
     *     <li>{@link OptionalDouble#empty()} for {@link OptionalDouble}.</li>
     *     <li>{@link OptionalInt#empty()} for {@link OptionalInt}.</li>
     *     <li>{@link OptionalLong#empty()} for {@link OptionalLong}.</li>
     *     <li>
     *         If a primitive class gets passed directly or as a field-type of the passed class,
     *         {@code JsonNull} will result in an {@link IllegalArgumentException}.
     *     </li>
     * </ul>
     *
     * <h4>Conversion of {@link JsonNumber}</h4>
     * <ul style="margin-top: 0; margin-bottom: 0">
     *     <li>
     *         Possibly lossy conversion of {@link JsonNumber} to the primitive wrapper types.
     *         Uses the contract of {@link JsonNumber#getAsByte()}, {@link JsonNumber#getAsShort()},
     *         {@link JsonNumber#getAsInt()}, {@link JsonNumber#getAsLong()},
     *         {@link JsonNumber#getAsFloat()} and {@link JsonNumber#getAsDouble()}.
     *     </li>
     *     <li>
     *         Possible lossy conversion of {@link JsonNumber} to {@link BigInteger}.
     *         Uses the contract of {@link JsonNumber#getAsBigInt()}.
     *     </li>
     *     <li>
     *         Lossless conversion of {@link JsonInteger} to {@link BigInteger}.
     *         Uses the contract of {@link JsonInteger#getAsBigInt()}.
     *     </li>
     *     <li>
     *         Lossless conversion of {@link JsonNumber} to {@link BigDecimal}
     *         Uses the contract of {@link JsonNumber#getAsBigDec()}.
     *     </li>
     * </ul>
     *
     * <h4>Conversion of {@link JsonString}</h4>
     * <ul style="margin-top: 0; margin-bottom: 0">
     *     <li>
     *         Conversion of {@link JsonString} to {@link String}.
     *         Uses the contract of {@link JsonString#getAsString()}.
     *     </li>
     *     <li>
     *         Conversion of {@link JsonString} to {@link Character} if the length is 1.
     *         Uses the contract of {@link JsonString#getAsChar()}.
     *     </li>
     *     <li>
     *         Conversion of {@link JsonString} to subclasses of {@link Enum} using {@link Enum#valueOf(Class, String)}.
     *         Uses the contract of {@link JsonString#getAsEnum(Class)}.
     *     </li>
     *     <li>
     *         Conversion of {@link JsonString} to {@link UUID} using {@link UUID#fromString(String)}.
     *         Uses the contract of {@link JsonString#getAsUUID()}.
     *     </li>
     *     <li>
     *         Conversion of {@link JsonString} to {@link Pattern} using {@link Pattern#compile(String)}.
     *         Uses the contract of {@link JsonString#getAsPattern()}.
     *     </li>
     * </ul>
     *
     * <h4>Conversion of {@link JsonList}</h4>
     * <ul style="margin-top: 0; margin-bottom: 0">
     *     <li>
     *         If the passed {@code cls} is an array type, this function and its contract get invoked for the respective element type.
     *     </li>
     * </ul>
     *
     * <hr>
     * <h3>Missing keys</h3>
     * If {@code jElem} is a {@link JsonMap}, that doesn't contain a key for a field of the passed class,
     * that field is treated as if {@link JsonNull} was passed.
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
     *         Passing such a type will result in an {@link IllegalArgumentException}.
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
     *             <li>{@link List}, {@link Collection} and {@link Iterable} gets instantiated as {@link ArrayList}.</li>
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
    @SuppressWarnings("OverlyCoupledMethod")
    public static <T> T instanceFromTree(JsonElement<?> jElem, Class<T> cls) {
        @SuppressWarnings({"MethodWithMultipleReturnPoints", "ChainOfInstanceofChecks"}) //
        final class JsonInstanceFromTree {

            static <T> T parse(JsonElement<?> jElem, Class<T> cls) {
                return (T) switch (jElem) {
                    case JsonNull jNull -> parseJsonNull(cls);
                    case JsonNumber<?> jNr -> parseJsonNumber(jNr, cls);
                    case JsonBool jBool -> parseJsonBool(jBool, cls);
                    case JsonString jStr -> parseJsonString(jStr, cls);
                    case JsonList jList -> parseJsonList(jList, cls);
                    case JsonMap jMap -> parseJsonMap(jMap, cls);
                };
            }

            @SuppressWarnings({"SimplifiableIfStatement", "ReturnOfNull"})
            private static Object parseJsonNull(Class<?> cls) {
                if (cls.isPrimitive())
                    throw CANT_CONVERT.apply(NULL, cls);
                if (cls == Optional.class)
                    return Optional.empty();
                if (cls == OptionalDouble.class)
                    return OptionalDouble.empty();
                if (cls == OptionalInt.class)
                    return OptionalInt.empty();
                if (cls == OptionalLong.class)
                    return OptionalLong.empty();
                return null;
            }

            private static Object parseJsonNumber(JsonNumber<?> jNr, Class<?> cls) {
                // DECIMAL
                if (cls == float.class || cls == Float.class)
                    return jNr.getAsFloat();
                if (cls == double.class || cls == Double.class)
                    return jNr.getAsDouble();
                if (cls == BigDecimal.class || cls == Number.class)
                    return jNr.getAsBigDec();
                if (cls == String.class)
                    return jNr.toString();

                // INTEGER
                if (cls == byte.class || cls == Byte.class)
                    return jNr.getAsByte();
                if (cls == short.class || cls == Short.class)
                    return jNr.getAsShort();
                if (cls == int.class || cls == Integer.class)
                    return jNr.getAsInt();
                if (cls == long.class || cls == Long.class)
                    return jNr.getAsLong();
                if (cls == BigInteger.class)
                    return jNr.getAsBigInt();

                throw CANT_CONVERT.apply(jNr, cls);
            }

            private static Object parseJsonBool(JsonBool jBool, Class<?> cls) {
                if (cls == boolean.class || cls == Boolean.class)
                    return jBool.getAsBool();
                throw CANT_CONVERT.apply(jBool, cls);
            }

            @SuppressWarnings({"rawtypes", "unchecked"})
            private static Object parseJsonString(JsonString jStr, Class<?> cls) {
                if (cls == char.class || cls == Character.class)
                    return jStr.getAsChar();
                if (cls == String.class || cls == CharSequence.class)
                    return jStr.getAsString();
                if (cls.isEnum())
                    return jStr.getAsEnum((Class) cls);
                if (cls == UUID.class)
                    return jStr.getAsUUID();
                if (cls == Pattern.class)
                    return jStr.getAsPattern();
                throw CANT_CONVERT.apply(jStr, cls);
            }

            @SuppressWarnings("OverlyLongMethod")
            private static Object parseJsonList(JsonList jList, Class<?> cls) {
                if (cls.isArray()) {
                    var componentType = cls.componentType();
                    if (componentType.isPrimitive()) {
                        if (componentType == byte.class)
                            return jList.getAsByteArray();
                        if (componentType == short.class)
                            return jList.getAsShortArray();
                        if (componentType == int.class)
                            return jList.getAsIntArray();
                        if (componentType == long.class)
                            return jList.getAsLongArray();
                        if (componentType == float.class)
                            return jList.getAsFloatArray();
                        if (componentType == double.class)
                            return jList.getAsDoubleArray();
                        if (componentType == boolean.class)
                            return jList.getAsBoolArray();
                        if (componentType == char.class)
                            return jList.getAsCharArray();
                    }
                    return jList.getAsArrayOf(componentType);
                }
                throw CANT_CONVERT.apply(jList, cls);
            }

            @SuppressWarnings("DuplicateStringLiteralInspection")
            private static Object parseJsonMap(JsonMap jsonMap, Class<?> cls) {
                BiFunction<Class<?>, String, Object> fieldGetter = (type, name) -> instanceFromTree(jsonMap.tryGet(name).orElse(NULL), type);
                try {
                    return cls.isRecord()
                           ? ReflectionHelper.constructRecord(cls, recordComponent -> fieldGetter.apply(recordComponent.getType(), recordComponent.getName()))
                           : ReflectionHelper.forceConstructClass(cls, field -> fieldGetter.apply(field.getType(), field.getName()));
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Couldn't convert " + JsonMap.class + " to record " + cls + ".\nThe constructor is inaccessible", e);
                } catch (InvocationTargetException e) {
                    throw new IllegalStateException("Couldn't convert " + JsonMap.class + " to record " + cls + ".\nThe constructor threw an Exception.", e);
                }
            }

        }
        return JsonInstanceFromTree.parse(jElem, cls);
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
                (jsonString) -> instanceFromString(jsonString, cls)
        );
    }

    /**
     * A {@link Collector} that collects {@link JsonElement}s to a {@link JsonList}.
     */
    public static Collector<JsonElement<?>, JsonList, JsonList> collector() {
        return Collector.of(JsonList::new, JsonList::add, JsonList::addAll);
    }

}