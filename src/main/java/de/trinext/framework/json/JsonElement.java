package de.trinext.framework.json;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import de.trinext.framework.json.JsonPathFinder.JsonPathFormatException;

import static de.trinext.framework.json.JsonPathFinder.THROW_PATH_FORMAT_EXCPT;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @param <V> the type of the wrapped value
 *
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess", "ClassReferencesSubclass"})
public abstract sealed class JsonElement<V> permits JsonContainer, JsonPrimitive {

    /**
     * The internal value of this element.
     * <p>
     * See the generic type parameter {@code V} for the type of the value:
     * <ul>
     *     <li>If {@code this} is a {@link JsonNull}, {@link #value} is {@code null}.</li>
     *     <li>If {@code this} is a {@link JsonBool}, {@link #value} is a {@link Boolean}.</li>
     *     <li>If {@code this} is a {@link JsonInteger}, {@link #value} is a {@link BigInteger}.</li>
     *     <li>If {@code this} is a {@link JsonDecimal}, {@link #value} is a {@link BigDecimal}.</li>
     *     <li>If {@code this} is a {@link JsonString}, {@link #value} is a {@link String}.</li>
     *     <li>If {@code this} is a {@link JsonList}, {@link #value} is a {@link List} of {@link JsonElement}s.</li>
     *     <li>If {@code this} is a {@link JsonMap}, {@link #value} is a {@link Map} of {@link String}s to {@link JsonElement}s.</li>
     * </ul>
     */
    final V value;

    // ==== CONSTRUCTORS ===================================================== //

    JsonElement(V value) {
        this.value = value;
    }

    // ==== METHODS ========================================================== //

    /** Returns the name of the type of this JsonElement. */
    public final String typeName() {
        return getClass().getSimpleName();
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public final boolean equals(Object obj) {
        return obj != null
                && getClass() == obj.getClass()
                && Objects.equals(value, ((JsonElement<?>) obj).value);
    }

    /** Returns the json-string-representation of this element. */
    @Override
    public abstract String toString();

    @SuppressWarnings("TypeMayBeWeakened")
    private static final IllegalArgumentException //

            JSON_PATH_NULL_EXCEPTION = new IllegalArgumentException("\"jsonPath\" must not be null"),
            FORMATTER_NULL_EXCEPTION = new IllegalArgumentException("\"formatter\" must not be null"),
            MAPPER_NULL_EXCEPTION = new IllegalArgumentException("\"mapper-function\" must not be null"),
            ELEM_CLASS_NULL_EXCEPTION = new IllegalArgumentException("\"elemClass\" must not be null"),
            ENUM_TYPE_NULL_EXCEPTION = new IllegalArgumentException("target class \"enumType\" must not be null"),
            CONVERSION_CLASS_NULL_EXCEPTION = new IllegalArgumentException("target class \"cls\" must not be null");


    // ==== GETTERS ========================================================== //

    /**
     * Returns whether the passed {@code jsonPath} points to a valid element.
     * (If you intend to use the element at the path, use {@link #tryGetPath(String)}
     * instead and check for its existence with {@link Optional#isPresent()}).
     *
     * <ul>
     *     <li>If this is not a {@link JsonContainer}, returns false.</li>
     * </ul>
     *
     * @throws JsonPathFormatException if the path is malformed.
     */
    public final boolean findPath(String jsonPath) {
        return tryGetPath(jsonPath).isPresent();
    }

    /**
     * Removes the element at the passed {@code jsonPath}.
     * <ul>
     *     <li>If this is not a {@link JsonContainer}, returns false.</li>
     * </ul>
     *
     * @throws JsonPathFormatException if the path is malformed.
     */
    @SuppressWarnings("All")
    public final boolean removePath(String jsonPath) {
        if (jsonPath == null)
            throw JSON_PATH_NULL_EXCEPTION;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Converts the value of {@code this} into something else.
     *
     * @param converter the mapping-function.
     * @param cls the {@link Class} of the target value.
     *
     * @throws IllegalArgumentException if the conversion fails.
     * @see Json#CANT_CONVERT
     */
    protected <T> T convert(Function<V, T> converter, Class<T> cls) {
        try {
            return converter.apply(value);
        } catch (RuntimeException r) {
            throw (IllegalArgumentException) Json.CANT_CONVERT.apply(this, cls).initCause(r);
        }
    }

    // ==== GETTERS ========================================================== //

    // ==== PRIMITIVE ==== //

    /**
     * Tries to return this element as a byte using {@link JsonNumber#getAsByte()}.
     * If this is not a {@link JsonNumber}, returns {@link Optional#empty()}.
     */
    public final Optional<Byte> tryGetAsByte() {
        return tryGetAsNumber().map(Number::byteValue);
    }

    /**
     * Tries to return this element as a short using {@link JsonNumber#getAsShort()}.
     * If this is not a {@link JsonNumber}, returns {@link Optional#empty()}.
     */
    public final Optional<Short> tryGetAsShort() {
        return tryGetAsNumber().map(Number::shortValue);
    }

    /**
     * Returns this element as an {@link OptionalInt} using {@link JsonNumber#getAsInt()}.
     * If this is not a {@link JsonNumber}, returns {@link Optional#empty()}.
     */
    public final OptionalInt tryGetAsInt() {
        var nr = tryGetAsNumber();
        return nr.map(Number::intValue)
                .map(OptionalInt::of)
                .orElseGet(OptionalInt::empty);
    }

    /**
     * Returns this element as a {@link OptionalLong} using {@link JsonNumber#getAsLong()}.
     * If this is not a {@link JsonNumber}, returns {@link Optional#empty()}.
     */
    public final OptionalLong tryGetAsLong() {
        var nr = tryGetAsNumber();
        return nr.map(Number::longValue)
                .map(OptionalLong::of)
                .orElseGet(OptionalLong::empty);
    }

    /**
     * Tries to return this element as a float using {@link JsonNumber#getAsFloat()}.
     * If this is not a {@link JsonNumber}, returns {@link Optional#empty()}.
     */
    public final Optional<Float> tryGetAsFloat() {
        return tryGetAsNumber().map(Number::floatValue);
    }

    /**
     * Returns this element as a {@link OptionalDouble} using {@link JsonNumber#getAsDouble()}.
     * If this is not a {@link JsonNumber}, returns {@link Optional#empty()}.
     */
    public final OptionalDouble tryGetAsDouble() {
        var nr = tryGetAsNumber();
        return nr.map(Number::doubleValue)
                .map(OptionalDouble::of)
                .orElseGet(OptionalDouble::empty);
    }

    /**
     * Tries to return this element as a boolean.
     * If this is not a {@link JsonBool}, returns {@link Optional#empty()}.
     */
    @SuppressWarnings("InstanceofThis")
    public final Optional<Boolean> tryGetAsBool() {
        return this instanceof JsonBool j
               ? Optional.of(j.getAsBool())
               : Optional.empty();
    }

    /**
     * Tries to return this element as a char using {@link JsonString#getAsChar()}.
     * If this is not a {@link JsonString}, returns {@link Optional#empty()}.</li>
     *
     * @throws IllegalArgumentException if the string is not of length 1.
     * @see Json#CANT_CONVERT
     */
    public final Optional<Character> tryGetAsChar() {
        return this instanceof JsonString jStr
               ? Optional.of(jStr.getAsChar())
               : Optional.empty();
    }

    // ==== OBJECT =========================================================== //

    /**
     * Tries to return this element as a {@link Number}.
     * If this is not a {@link JsonNumber}, returns {@link Optional#empty()}.
     */
    public final Optional<Number> tryGetAsNumber() {
        return this instanceof JsonNumber<?> jNr
               ? Optional.of(jNr.getAsNumber())
               : Optional.empty();
    }

    /**
     * Tries to return this element as a {@link String}.
     * If this is not a {@link JsonString}, returns {@link Optional#empty()}.
     */
    @SuppressWarnings("InstanceofThis")
    public final Optional<String> tryGetAsString() {
        return this instanceof JsonString j
               ? Optional.of(j.getAsString())
               : Optional.empty();
    }

    /**
     * Tries to return this element as a {@link BigInteger}.
     * <ul>
     *     <li>If this is not a {@link JsonNumber}, returns {@link Optional#empty()}.</li>
     *     <li>If this is a {@link JsonDecimal}, the value gets truncated to a {@link BigInteger} using the contract of {@link BigDecimal#toBigInteger()}.</li>
     * </ul>
     */
    @SuppressWarnings("InstanceofThis")
    public final Optional<BigInteger> tryGetAsBigInt() {
        return this instanceof JsonNumber<?> jNr
               ? Optional.of(jNr.getAsBigInt())
               : Optional.empty();
    }

    /**
     * Tries to return this element as a {@link BigDecimal}.
     * <ul>
     *     <li>If this is not a {@link JsonNumber}, returns {@link Optional#empty()}.</li>
     * </ul>
     */
    @SuppressWarnings("ClassReferencesSubclass")
    public final Optional<BigDecimal> tryGetAsBigDec() {
        return this instanceof JsonNumber<?> jNr
               ? Optional.of(jNr.getAsBigDec())
               : Optional.empty();
    }


    // ==== DATETIME =========================================================== //

    /**
     * Tries to return this element as a {@link LocalDate}.
     *
     * <ul>
     *     <li>If this is not a {@link JsonString}, returns {@link Optional#empty()}.</li>
     *     <li>If the string doesn't match the passed {@link DateTimeFormatter}, returns {@link Optional#empty()}.</li>
     *     <li>If the string matches the formatter, but the value is not a valid date, returns {@link Optional#empty()}.</li>
     * </ul>
     */
    public final Optional<LocalDate> tryGetAsDate(DateTimeFormatter formatter) {
        if (formatter == null)
            throw FORMATTER_NULL_EXCEPTION;
        try {
            return tryGetAsString().map(s -> LocalDate.parse(s, formatter));
        } catch (DateTimeParseException parseException) {
            return Optional.empty();
        }
    }

    /**
     * Tries to return this element as a {@link LocalTime}.
     *
     * <ul>
     *     <li>If this is not a {@link JsonString}, returns {@link Optional#empty()}.</li>
     *     <li>If the string doesn't match the passed {@link DateTimeFormatter}, returns {@link Optional#empty()}.</li>
     *     <li>If the string matches the formatter, but the value is not a valid time, returns {@link Optional#empty()}.</li>
     * </ul>
     */
    public final Optional<LocalTime> tryGetAsTime(DateTimeFormatter formatter) {
        if (formatter == null)
            throw FORMATTER_NULL_EXCEPTION;
        try {
            return tryGetAsString().map(s -> LocalTime.parse(s, formatter));
        } catch (DateTimeParseException parseException) {
            return Optional.empty();
        }
    }

    /**
     * Tries to return this element as a {@link LocalDateTime}.
     *
     * <ul>
     *     <li>If this is not a {@link JsonString}, returns {@link Optional#empty()}.</li>
     *     <li>If the string doesn't match the passed {@link DateTimeFormatter}, returns {@link Optional#empty()}.</li>
     *     <li>If the string matches the formatter, but the value is not a valid date-time, returns {@link Optional#empty()}.</li>
     * </ul>
     */
    public final Optional<LocalDateTime> tryGetAsDateTime(DateTimeFormatter formatter) {
        if (formatter == null)
            throw FORMATTER_NULL_EXCEPTION;
        try {
            return tryGetAsString().map(s -> LocalDateTime.parse(s, formatter));
        } catch (DateTimeParseException parseException) {
            return Optional.empty();
        }
    }


    // ==== CLASS/ENUM =========================================================== //

    /**
     * Tries to return this element as an enum-constant of the passed class.
     *
     * <ul>
     *     <li>If this is not a {@link JsonString}, returns {@link Optional#empty()}.</li>
     *     <li>If the string doesn't match any enum-constant of the passed class, returns {@link Optional#empty()}.</li>
     * </ul>
     */
    public final <E extends Enum<E>> Optional<E> tryGetAsEnum(Class<E> enumType) {
        if (enumType == null)
            throw ENUM_TYPE_NULL_EXCEPTION;
        return tryGetAsString().map(s -> Enum.valueOf(enumType, s));
    }

    /**
     * Tries to return this element as an instance of the passed class.
     *
     * @see Json#instanceFromTree(JsonElement, Class)
     */
    public final <T> Optional<T> tryGetAsObj(Class<? extends T> cls) {
        if (cls == null)
            throw CONVERSION_CLASS_NULL_EXCEPTION;
        return tryGetAsObj(e -> Json.instanceFromTree(e, cls));
    }

    @SuppressWarnings("BoundedWildcard")
    public final <T> Optional<T> tryGetAsObj(Function<JsonElement<?>, ? extends T> mapper) {
        if (mapper == null)
            throw MAPPER_NULL_EXCEPTION;
        return Optional.ofNullable(mapper.apply(this));
    }

    // ==== COLLECTION/STREAM ======================================================== //

    @SuppressWarnings({"InstanceofThis", "BoundedWildcard"})
    public final <T> Optional<Stream<T>> tryGetAsStreamOf(Function<JsonElement<?>, ? extends T> mapper) {
        if (mapper == null)
            throw MAPPER_NULL_EXCEPTION;
        return this instanceof JsonList jList
               ? Optional.of(jList.stream().map(mapper))
               : Optional.empty();
    }

    public final <T> Optional<List<T>> tryGetAsListOf(Function<JsonElement<?>, ? extends T> mapper) {
        return tryGetAsStreamOf(mapper).map(stream -> stream.collect(toList()));
    }

    public final <T> Optional<Set<T>> tryGetAsSetOf(Function<JsonElement<?>, ? extends T> mapper) {
        return tryGetAsStreamOf(mapper).map(stream -> stream.collect(toSet()));
    }

    /**
     * Tries to return this as a {@link Stream} of elements of a certain class.
     *
     * @param elemCls the class of the elements in the stream. Uses {@link Json#instanceFromTree(JsonElement, Class)} to convert the elements.
     *
     * @see Json#CANT_CONVERT
     */
    public final <T> Optional<Stream<T>> tryGetAsStreamOf(Class<? extends T> elemCls) {
        if (elemCls == null)
            throw ELEM_CLASS_NULL_EXCEPTION;
        return tryGetAsStreamOf(e -> Json.instanceFromTree(e, elemCls));
    }

    public final <T> Optional<List<T>> tryGetAsListOf(Class<? extends T> elemCls) {
        return tryGetAsStreamOf(elemCls).map(stream -> stream.collect(toList()));
    }

    public final <T> Optional<Set<T>> tryGetAsSetOf(Class<? extends T> elemCls) {
        return tryGetAsStreamOf(elemCls).map(stream -> stream.collect(toSet()));
    }


    // ==== ARRAY ======================================================== //

    /**
     * Tries to return this element as an array of a certain class.
     * <ul>
     *     <li>If this is not a {@link JsonList}, returns {@link Optional#empty()}.</li>
     * </ul>
     *
     * @param mapper a function that converts the {@link JsonElement}s in the {@link JsonList} to objects of a certain class.
     * @param elemCls the class of the elements in the array.
     */
    public final <T> Optional<T[]> tryGetAsArrayOf(Function<JsonElement<?>, ? extends T> mapper, Class<? extends T> elemCls) {
        if (elemCls == null)
            throw ELEM_CLASS_NULL_EXCEPTION;
        return tryGetAsStreamOf(mapper).map(stream -> stream.toArray(length -> (T[]) Array.newInstance(elemCls, length)));
    }

    /**
     * Tries to return this element as an array of the passed class.
     *
     * @param elemCls the class of the elements in the array.
     *
     * @throws IllegalArgumentException If something goes wrong during the conversion.
     * @see Json#instanceFromTree(JsonElement, Class)
     * @see Json#CANT_CONVERT
     */
    public final <T> Optional<T[]> tryGetAsArrayOf(Class<? extends T> elemCls) {
        return tryGetAsStreamOf(elemCls).map(stream -> stream.toArray(length -> (T[]) Array.newInstance(elemCls, length)));
    }

    /**
     * Tries to return this element as a {@code byte[]}.
     * <ul>
     *     <li>If this is a {@link JsonList} of {@link JsonNumber}s, returns the list as a byte array.</li>
     *     <li>Otherwise, returns {@link Optional#empty()}.</li>
     *     <li>If the value doesn't fit into a byte, it gets truncated according to the
     *     <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.</li>
     * </ul>
     * <p>
     * TODO: Specify behavior if some elements are not {@link JsonNumber}s.
     */
    public final Optional<byte[]> tryGetAsByteArray() {
        return tryGetAsArrayOf(Byte.class).map(bytes -> {
            var res = new byte[bytes.length];
            for (var i = 0; i < bytes.length; i++)
                res[i] = bytes[i];
            return res;
        });
    }

    /**
     * Tries to return this element as a {@code short[]}.
     * <ul>
     *     <li>If this is a {@link JsonList} of {@link JsonNumber}s, returns the list as a short array.</li>
     *     <li>Otherwise, returns {@link Optional#empty()}.</li>
     *     <li>If the value doesn't fit into a short, it gets truncated according to the
     *     <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.</li>
     * </ul>
     * <p>
     * TODO: Specify behavior if some elements are not {@link JsonNumber}s.
     */
    public final Optional<short[]> tryGetAsShortArray() {
        return tryGetAsArrayOf(Short.class).map(shorts -> {
            var res = new short[shorts.length];
            for (var i = 0; i < shorts.length; i++)
                res[i] = shorts[i];
            return res;
        });
    }

    /**
     * Tries to return this element as an {@code int[]}.
     * <ul>
     *     <li>If this is a {@link JsonList} of {@link JsonNumber}s, returns the list as an int array.</li>
     *     <li>Otherwise, returns {@link Optional#empty()}.</li>
     *     <li>If the value doesn't fit into an int, it gets truncated according to the
     *     <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.</li>
     * </ul>
     * <p>
     * TODO: Specify behavior if some elements are not {@link JsonNumber}s.
     */
    public final Optional<int[]> tryGetAsIntArray() {
        return tryGetAsStreamOf(Integer.class).map(stream -> stream.mapToInt(Integer::intValue).toArray());
    }

    /**
     * Tries to return this element as a {@code long[]}.
     * <ul>
     *     <li>If this is a {@link JsonList} of {@link JsonNumber}s, returns the list as a long array.</li>
     *     <li>Otherwise, returns {@link Optional#empty()}.</li>
     *     <li>If the value doesn't fit into a long, it gets truncated according to the
     *     <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.</li>
     * </ul>
     * <p>
     * TODO: Specify behavior if some elements are not {@link JsonNumber}s.
     */
    public final Optional<long[]> tryGetAsLongArray() {
        return tryGetAsStreamOf(Long.class).map(stream -> stream.mapToLong(Long::longValue).toArray());
    }

    /**
     * Tries to return this element as a {@code float[]}.
     * <ul>
     *     <li>If this is a {@link JsonList} of {@link JsonNumber}s, returns the list as a float array.</li>
     *     <li>Otherwise, returns {@link Optional#empty()}.</li>
     *     <li>If the value doesn't fit into a float, it gets truncated according to the
     *     <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.</li>
     * </ul>
     * <p>
     * TODO: Specify behavior if some elements are not {@link JsonNumber}s.
     */
    public final Optional<float[]> tryGetAsFloatArray() {
        return tryGetAsArrayOf(Float.class).map(floats -> {
            var res = new float[floats.length];
            for (var i = 0; i < floats.length; i++)
                res[i] = floats[i];
            return res;
        });
    }

    /**
     * Tries to return this element as a {@code double[]}.
     * <ul>
     *     <li>If this is a {@link JsonList} of {@link JsonNumber}s, returns the list as a double array.</li>
     *     <li>Otherwise, returns {@link Optional#empty()}.</li>
     *     <li>If the value doesn't fit into a double, it gets truncated according to the
     *     <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.</li>
     * </ul>
     * <p>
     * TODO: Specify behavior if some elements are not {@link JsonNumber}s.
     */
    public final Optional<double[]> tryGetAsDoubleArray() {
        return tryGetAsStreamOf(Double.class).map(stream -> stream.mapToDouble(Double::doubleValue).toArray());
    }

    /**
     * Tries to return this element as a {@code boolean[]}.
     * <ul>
     *     <li>
     *         If this is a {@link JsonList} of {@link JsonBool}s, returns the list as a boolean array.
     *         Uses {@link JsonBool#getAsBool()} to convert the {@link JsonBool}s to booleans.
     *     </li>
     *     <li>Otherwise, returns {@link Optional#empty()}.</li>
     * </ul>
     *
     * @throws IllegalArgumentException If something goes wrong during the conversion.
     * @see Json#CANT_CONVERT
     */
    public final Optional<boolean[]> tryGetAsBooleanArray() {
        return tryGetAsArrayOf(Boolean.class).map(bools -> {
            var res = new boolean[bools.length];
            for (var i = 0; i < bools.length; i++)
                res[i] = bools[i];
            return res;
        });
    }

    /**
     * Tries to return this element as a {@code char[]}.
     * <ul>
     *     <li>
     *         If this is a {@link JsonString}, returns the string as a char array.
     *         Uses {@link JsonString#getAsString()} and {@link String#toCharArray()} to convert the string to a char array.
     *     </li>
     *     <li>
     *         If this is a {@link JsonList} of {@link JsonString}s of length 1, returns the list as a char array.
     *         Uses {@link JsonString#getAsChar()} to convert the strings to chars.
     *     </li>
     *     <li>If this is neither, returns {@link Optional#empty()}.</li>
     * </ul>
     *
     * @throws IllegalArgumentException If something goes wrong during the conversion.
     * @see Json#CANT_CONVERT
     */
    public final Optional<char[]> tryGetAsCharArray() {
        return switch (this) {
            case JsonString jStr -> Optional.of(jStr.value.toCharArray());
            case JsonList jList -> tryGetAsArrayOf(Character.class).map(chars -> {
                var res = new char[chars.length];
                for (var i = 0; i < chars.length; i++)
                    res[i] = chars[i];
                return res;
            });
            default -> Optional.empty();
        };
    }

    // ==== PATH >> PRIMITIVE ======================================================== //

    /**
     * Tries to return the element at the passed {@code jsonPath}.
     * <ul>
     *     <li>If this is not a {@link JsonContainer}, returns {@link Optional#empty()}.</li>
     * </ul>
     *
     * @throws JsonPathFormatException if the path is malformed.
     */
    @SuppressWarnings("InstanceofThis")
    public final Optional<JsonElement<?>> tryGetPath(String jsonPath) {
        if (jsonPath == null)
            throw JSON_PATH_NULL_EXCEPTION;
        return this instanceof JsonContainer<?> jCon
               ? new JsonPathFinder(jCon, jsonPath, THROW_PATH_FORMAT_EXCPT).find()
               : Optional.empty();
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as a byte.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsByte()
     */
    public final Optional<Byte> tryGetPathAsByte(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetAsByte);
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as a short.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsShort()
     */
    public final Optional<Short> tryGetPathAsShort(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetAsShort);
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as an {@link OptionalInt}.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsInt()
     */
    public final OptionalInt tryGetPathAsInt(String jsonPath) {
        return tryGetPath(jsonPath)
                .map(JsonElement::tryGetAsInt)
                .orElseGet(OptionalInt::empty);
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link OptionalLong}.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsLong()
     */
    public final OptionalLong tryGetPathAsLong(String jsonPath) {
        return tryGetPath(jsonPath)
                .map(JsonElement::tryGetAsLong)
                .orElseGet(OptionalLong::empty);
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as a float.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsFloat()
     */
    public final OptionalDouble tryGetPathAsDouble(String jsonPath) {
        return tryGetPath(jsonPath)
                .map(JsonElement::tryGetAsDouble)
                .orElseGet(OptionalDouble::empty);
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link OptionalDouble}.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsDouble()
     */
    public final Optional<Float> tryGetPathAsFloat(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetAsFloat);
    }

    // ==== PATH >> OBJECT =========================================================== //

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link Number}.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsNumber()
     */
    public final Optional<Number> tryGetPathAsNumber(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetAsNumber);
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link String}.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsString()
     */
    public final Optional<String> tryGetPathAsString(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetAsString);
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link BigInteger}.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsBigInt()
     */
    public final Optional<BigInteger> tryGetPathAsBigInt(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetAsBigInt);
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link BigDecimal}.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsBigDec()
     */
    public final Optional<BigDecimal> tryGetPathAsBigDec(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetAsBigDec);
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link Boolean}.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsBool()
     */
    public final Optional<Boolean> tryGetPathAsBool(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetAsBool);
    }

    // ==== PATH >> DATETIME =========================================================== //

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link LocalDate}.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsDate(DateTimeFormatter)
     */
    public final Optional<LocalDate> tryGetPathAsDate(String jsonPath, DateTimeFormatter formatter) {
        return tryGetPathAsString(jsonPath).flatMap(s -> tryGetAsDate(formatter));
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link LocalTime}.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsTime(DateTimeFormatter)
     */
    public final Optional<LocalTime> tryGetPathAsTime(String jsonPath, DateTimeFormatter formatter) {
        return tryGetPathAsString(jsonPath).flatMap(s -> tryGetAsTime(formatter));
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link LocalDateTime}.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsDateTime(DateTimeFormatter)
     */
    public final Optional<LocalDateTime> tryGetPathAsDateTime(String jsonPath, DateTimeFormatter formatter) {
        if (formatter == null)
            throw FORMATTER_NULL_EXCEPTION;
        return tryGetPathAsString(jsonPath).flatMap(s -> tryGetAsDateTime(formatter));
    }

    // ==== PATH >> CLASS/ENUM =================== //

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link Enum}.
     *
     * @param enumType the class of the enum to return.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsEnum(Class)
     */
    public final <E extends Enum<E>> Optional<E> tryGetPathAsEnum(String jsonPath, Class<E> enumType) {
        if (enumType == null)
            throw ENUM_TYPE_NULL_EXCEPTION;
        return tryGetPathAsString(jsonPath).map(e -> Enum.valueOf(enumType, e));
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as an object of the passed class.
     *
     * @param cls the class of the object to return.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsObj(Class)
     */
    public final <T> Optional<T> tryGetPathAsObj(String jsonPath, Class<? extends T> cls) {
        if (cls == null)
            throw CONVERSION_CLASS_NULL_EXCEPTION;
        return tryGetPathAsObj(jsonPath, e -> Json.instanceFromTree(e, cls));
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as an object of the passed class.
     *
     * @param mapper a function that converts the element at the path to an object of the passed class.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsObj(Function)
     */
    @SuppressWarnings("BoundedWildcard")
    public final <T> Optional<T> tryGetPathAsObj(String jsonPath, Function<JsonElement<?>, ? extends T> mapper) {
        if (mapper == null)
            throw MAPPER_NULL_EXCEPTION;
        return tryGetPath(jsonPath).map(mapper);
    }

    // ==== PATH >> COLLECTION/STREAM =================== //

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link Stream}.
     * <ul>
     *     <li>If this is not a {@link JsonList}, returns {@link Optional#empty()}.</li>
     * </ul>
     *
     * @param elemCls the class of the elements in the stream.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsStreamOf(Function)
     */
    public final <T> Optional<Stream<T>> tryGetPathAsStreamOf(String jsonPath, Class<? extends T> elemCls) {
        if (elemCls == null)
            throw ELEM_CLASS_NULL_EXCEPTION;
        return tryGetPathAsStreamOf(jsonPath, e -> Json.instanceFromTree(e, elemCls));
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link Stream}.
     * <ul>
     *     <li>If the element at the path is not a {@link JsonList}, returns {@link Optional#empty()}.</li>
     * </ul>
     *
     * @param mapper a function that converts the {@link JsonElement}s in the stream to objects of a certain class.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsStreamOf(Function)
     */
    public final <T> Optional<Stream<T>> tryGetPathAsStreamOf(String jsonPath, Function<JsonElement<?>, ? extends T> mapper) {
        if (mapper == null)
            throw MAPPER_NULL_EXCEPTION;
        return tryGetPath(jsonPath).flatMap(e -> e.tryGetAsStreamOf(mapper));
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link List}.
     * <ul>
     *     <li>If the element at the path is not a {@link JsonList}, returns {@link Optional#empty()}.</li>
     * </ul>
     *
     * @param elemCls the class of the elements in the list.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsListOf(Class)
     */
    public final <T> Optional<List<T>> tryGetPathAsListOf(String jsonPath, Class<? extends T> elemCls) {
        return tryGetPathAsStreamOf(jsonPath, elemCls).map(stream -> stream.collect(toList()));
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link List}.
     * <ul>
     *     <li>IIf the element at the path is not a {@link JsonList}, returns {@link Optional#empty()}.</li>
     * </ul>
     *
     * @param mapper a function that converts the {@link JsonElement}s in the list to objects of a certain class.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsListOf(Function)
     */
    public final <T> Optional<List<T>> tryGetPathAsListOf(String jsonPath, Function<JsonElement<?>, ? extends T> mapper) {
        return tryGetPathAsStreamOf(jsonPath, mapper).map(stream -> stream.collect(toList()));
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link Set}.
     * <ul>
     *     <li>If the element at the path is not a {@link JsonList}, returns {@link Optional#empty()}.</li>
     *     <li>If the list contains duplicate elements, the returned set will contain only one of each element.</li>
     * </ul>
     *
     * @param elemCls the class of the elements in the set.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsSetOf(Class)
     */
    public final <T> Optional<Set<T>> tryGetPathAsSetOf(String jsonPath, Class<? extends T> elemCls) {
        return tryGetPathAsStreamOf(jsonPath, elemCls).map(stream -> stream.collect(toSet()));
    }

    /**
     * Tries to return the element at the passed {@code jsonPath} as a {@link Set}.
     * <ul>
     *     <li>If the element at the path is not a {@link JsonList}, returns {@link Optional#empty()}.</li>
     *     <li>If the list contains duplicate elements, the returned set will contain only one of each element.</li>
     * </ul>
     *
     * @param mapper a function that converts the {@link JsonElement}s in the set to objects of a certain class.
     *
     * @throws JsonPathFormatException if the path is malformed.
     * @see #tryGetPath(String)
     * @see #tryGetAsSetOf(Function)
     */
    public final <T> Optional<Set<T>> tryGetPathAsSetOf(String jsonPath, Function<JsonElement<?>, ? extends T> mapper) {
        return tryGetPathAsStreamOf(jsonPath, mapper).map(stream -> stream.collect(toSet()));
    }

}