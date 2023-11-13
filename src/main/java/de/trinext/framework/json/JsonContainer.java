package de.trinext.framework.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

/**
 * The classification for a {@link JsonElement} that contains other {@link JsonElement}s.
 *
 * @param <V> The type of container
 *
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract sealed class JsonContainer<V> extends JsonElement<V> permits JsonList, JsonMap {

    JsonContainer(@Nullable V value) {
        super(value);
    }

    /**
     * Tries to return the element referenced by the jsonPath.
     *
     * @see JsonPathFinder for the description of json paths.
     */
    public final Optional<JsonElement<?>> tryGetPath(String jsonPath) {
        return new JsonPathFinder(this, jsonPath).find();
    }

    // ==== PRIMITIVE ======================================================== //

    /** @return {@link OptionalInt} whether the target of the passed path is a {@link JsonInteger} which can be represented as an {@code int}. */
    public final OptionalInt tryGetPathAsInt(String jsonPath) {
        var res = tryGetPath(jsonPath);
        return res.isPresent() ? res.get().tryGetInt() : OptionalInt.empty();
    }

    /** @return {@link OptionalLong} whether the target of the passed path is a {@link JsonInteger} which can be represented as a {@code long}. */
    public final OptionalLong tryGetPathAsLong(String jsonPath) {
        var res = tryGetPath(jsonPath);
        return res.isPresent() ? res.get().tryGetLong() : OptionalLong.empty();
    }

    /** @return {@link OptionalDouble} whether the target of the passed path is a {@link JsonDecimal} which can be represented as a {@code double}. */
    public final OptionalDouble tryGetPathAsDouble(String jsonPath) {
        var res = tryGetPath(jsonPath);
        return res.isPresent() ? res.get().tryGetDouble() : OptionalDouble.empty();
    }

    // ==== OBJECT =========================================================== //


    /** @return {@link Optional<Enum>} whether the target of the passed path is a {@link JsonString} which can be represented as an {@link Enum}. */
    public final <E extends Enum<E>> Optional<E> tryGetPathAsEnum(String jsonPath, Class<E> enumType) {
        return tryGetPath(jsonPath).flatMap(e -> e.tryGetEnum(enumType));
    }

    public final Optional<Number> tryGetPathAsNumber(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetNumber);
    }

    public final Optional<String> tryGetPathAsString(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetString);
    }

    public final Optional<BigInteger> tryGetPathAsBigInt(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetBigInt);
    }

    public final Optional<BigDecimal> tryGetPathAsBigDec(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetBigDec);
    }

    public final Optional<Boolean> tryGetPathAsBool(String jsonPath) {
        return tryGetPath(jsonPath).flatMap(JsonElement::tryGetBool);
    }

    // ==== CONTAINER ======================================================== //

    public final <T> Optional<T> tryGetPathAsObj(String jsonPath, Class<? extends T> cls) {
        return tryGetPath(jsonPath).flatMap(e -> e.tryGetObj(cls));
    }

    public final <T> Optional<Stream<T>> tryGetPathAsStreamOf(String jsonPath, Class<? extends T> elemCls) {
        return tryGetPath(jsonPath).flatMap(e -> e.tryGetStreamOf(elemCls));
    }

    public final <T> Optional<List<T>> tryGetPathAsListOf(String jsonPath, Class<? extends T> elemCls) {
        return tryGetPath(jsonPath).flatMap(e -> e.tryGetListOf(elemCls));
    }

    public final <T> Optional<Set<T>> tryGetPathAsSetOf(String jsonPath, Class<? extends T> elemCls) {
        return tryGetPath(jsonPath).flatMap(e -> e.tryGetSetOf(elemCls));
    }

}