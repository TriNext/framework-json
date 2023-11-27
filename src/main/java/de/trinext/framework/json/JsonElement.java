package de.trinext.framework.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.gson.JsonSyntaxException;

import static de.trinext.framework.json.JsonPathFinder.NO_FLAGS;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @param <V> the type of the wrapped value
 *
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess", "ClassReferencesSubclass"})
public abstract sealed class JsonElement<V> permits JsonContainer, JsonPrimitive, JsonNull {

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

    @Override
    public abstract String toString();

    // ==== GETTERS ========================================================== //

    public final boolean findPath(String jsonPath) {
        return tryGetPath(jsonPath).isPresent();
    }

    @SuppressWarnings("All")
    public final boolean removePath(String jsonPath) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // ==== GETTERS ========================================================== //

    // ==== PRIMITIVE ==== //

    public final OptionalInt tryGetAsInt() {
        try {
            return switch (this) {
                case JsonInteger jInt -> OptionalInt.of(jInt.value.intValueExact());
                case JsonDecimal jDec -> OptionalInt.of(jDec.value.intValueExact());
                default -> OptionalInt.empty();
            };
        } catch (ArithmeticException ae) {
            return OptionalInt.empty();
        }
    }

    public final OptionalLong tryGetAsLong() {
        try {
            return switch (this) {
                case JsonInteger jInt -> OptionalLong.of(jInt.value.longValueExact());
                case JsonDecimal jDec -> OptionalLong.of(jDec.value.longValueExact());
                default -> OptionalLong.empty();
            };
        } catch (ArithmeticException ae) {
            return OptionalLong.empty();
        }
    }

    public final OptionalDouble tryGetAsDouble() {
        var nr = tryGetAsNumber();
        return nr.map(number -> OptionalDouble.of(number.doubleValue()))
                .orElseGet(OptionalDouble::empty);
    }

    // ==== OBJECT =========================================================== //

    public final <E extends Enum<E>> Optional<E> tryGetAsEnum(Class<E> enumType) {
        return tryGetAsString().map(s -> Enum.valueOf(enumType, s));
    }

    public final Optional<Number> tryGetAsNumber() {
        return switch (this) {
            case JsonInteger jInt -> Optional.of(jInt.value);
            case JsonDecimal jDec -> Optional.of(jDec.value);
            default -> Optional.empty();
        };
    }

    @SuppressWarnings("InstanceofThis")
    public final Optional<String> tryGetAsString() {
        return this instanceof JsonString j
               ? Optional.of(j.value) : Optional.empty();
    }

    @SuppressWarnings("InstanceofThis")
    public final Optional<BigInteger> tryGetAsBigInt() {
        return this instanceof JsonInteger j
               ? Optional.of(j.value) : Optional.empty();
    }

    @SuppressWarnings("ClassReferencesSubclass")
    public final Optional<BigDecimal> tryGetAsBigDec() {
        return switch (this) {
            case JsonDecimal j -> Optional.of(j.value);
            case JsonInteger j -> Optional.of(new BigDecimal(j.value));
            default -> Optional.empty();
        };
    }

    @SuppressWarnings("InstanceofThis")
    public final Optional<Boolean> tryGetAsBool() {
        return this instanceof JsonBool j
               ? Optional.of(j.value) : Optional.empty();
    }

    public final Optional<LocalDate> tryGetAsDate(DateTimeFormatter formatter) {
        return tryGetAsString().map(s -> LocalDate.parse(s, formatter));
    }

    public final Optional<LocalTime> tryGetAsTime(DateTimeFormatter formatter) {
        return tryGetAsString().map(s -> LocalTime.parse(s, formatter));
    }

    public final Optional<LocalDateTime> tryGetAsDateTime(DateTimeFormatter formatter) {
        return tryGetAsString().map(s -> LocalDateTime.parse(s, formatter));
    }

    public final <T> Optional<T> tryGetAsObj(Class<? extends T> cls) {
        try {
            return Optional.of(Json.instanceFromTree(this, cls));
        } catch (JsonSyntaxException jse) {
            return Optional.empty();
        }
    }

    // ==== COLLECTION/STREAM ======================================================== //

    @SuppressWarnings({"InstanceofThis", "BoundedWildcard"})
    public final <T> Optional<Stream<T>> tryGetAsStreamOf(Function<JsonElement<?>, ? extends T> mapper) {
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

    public final <T> Optional<Stream<T>> tryGetAsStreamOf(Class<? extends T> elemCls) {
        return tryGetAsStreamOf(e -> Json.instanceFromTree(e, elemCls));
    }

    public final <T> Optional<List<T>> tryGetAsListOf(Class<? extends T> elemCls) {
        return tryGetAsStreamOf(elemCls).map(stream -> stream.collect(toList()));
    }

    public final <T> Optional<Set<T>> tryGetAsSetOf(Class<? extends T> elemCls) {
        return tryGetAsStreamOf(elemCls).map(stream -> stream.collect(toSet()));
    }

    // ==== PATH >> PRIMITIVE ======================================================== //

    @SuppressWarnings("InstanceofThis")
    public final Optional<JsonElement<?>> tryGetPath(String jsonPath) {
        return this instanceof JsonContainer<?> jCon
               ? new JsonPathFinder(jCon, jsonPath, NO_FLAGS).find()
               : Optional.empty();
    }

    public final OptionalInt tryGetPathAsInt(String jsonPath) {
        var res = tryGetPath(jsonPath);
        return res.isPresent() ? res.get().tryGetAsInt() : OptionalInt.empty();
    }

    public final OptionalLong tryGetPathAsLong(String jsonPath) {
        var res = tryGetPath(jsonPath);
        return res.isPresent() ? res.get().tryGetAsLong() : OptionalLong.empty();
    }

    public final OptionalDouble tryGetPathAsDouble(String jsonPath) {
        var res = tryGetPath(jsonPath);
        return res.isPresent() ? res.get().tryGetAsDouble() : OptionalDouble.empty();
    }

    // ==== PATH >> OBJECT =========================================================== //

    public final Optional<Number> tryGetPathAsNumber(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetAsNumber);
    }

    public final Optional<String> tryGetPathAsString(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetAsString);
    }

    public final Optional<BigInteger> tryGetPathAsBigInt(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetAsBigInt);
    }

    public final Optional<BigDecimal> tryGetPathAsBigDec(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetAsBigDec);
    }

    public final Optional<Boolean> tryGetPathAsBool(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetAsBool);
    }

    public final Optional<LocalDate> tryGetPathAsDate(String jsonPath, DateTimeFormatter formatter) {
        return tryGetPathAsString(jsonPath).flatMap(s -> tryGetAsDate(formatter));
    }

    public final Optional<LocalTime> tryGetPathAsTime(String jsonPath, DateTimeFormatter formatter) {
        return tryGetPathAsString(jsonPath).flatMap(s -> tryGetAsTime(formatter));
    }

    public final Optional<LocalDateTime> tryGetPathAsDateTime(String jsonPath, DateTimeFormatter formatter) {
        return tryGetPathAsString(jsonPath).flatMap(s -> tryGetAsDateTime(formatter));
    }

    // ==== PATH >> OBJECT =================== //

    public final <E extends Enum<E>> Optional<E> tryGetPathAsEnum(String jsonPath, Class<E> enumType) {
        return tryGetPathAsString(jsonPath).map(e -> Enum.valueOf(enumType, e));
    }

    public final <T> Optional<T> tryGetPathAsObj(String jsonPath, Class<? extends T> cls) {
        return tryGetPathAsObj(jsonPath, e -> Json.instanceFromTree(e, cls));
    }

    public final <T> Optional<T> tryGetPathAsObj(String jsonPath, Function<? super JsonElement<?>, ? extends T> mapper) {
        return tryGetPath(jsonPath).map(mapper);
    }

    // ==== PATH >> COLLECTION/STREAM =================== //

    public final <T> Optional<Stream<T>> tryGetPathAsStreamOf(String jsonPath, Class<? extends T> elemCls) {
        return tryGetPathAsStreamOf(jsonPath, e -> Json.instanceFromTree(e, elemCls));
    }

    public final <T> Optional<Stream<T>> tryGetPathAsStreamOf(String jsonPath, Function<JsonElement<?>, ? extends T> mapper) {
        return tryGetPath(jsonPath).flatMap(e -> e.tryGetAsStreamOf(mapper));
    }

    public final <T> Optional<List<T>> tryGetPathAsListOf(String jsonPath, Class<? extends T> elemCls) {
        return tryGetPathAsStreamOf(jsonPath, elemCls).map(stream -> stream.collect(toList()));
    }

    public final <T> Optional<List<T>> tryGetPathAsListOf(String jsonPath, Function<JsonElement<?>, ? extends T> mapper) {
        return tryGetPathAsStreamOf(jsonPath, mapper).map(stream -> stream.collect(toList()));
    }

    public final <T> Optional<Set<T>> tryGetPathAsSetOf(String jsonPath, Class<? extends T> elemCls) {
        return tryGetPathAsStreamOf(jsonPath, elemCls).map(stream -> stream.collect(toSet()));
    }

    public final <T> Optional<Set<T>> tryGetPathAsSetOf(String jsonPath, Function<JsonElement<?>, ? extends T> mapper) {
        return tryGetPathAsStreamOf(jsonPath, mapper).map(stream -> stream.collect(toSet()));
    }

}