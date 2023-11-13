package de.trinext.framework.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.JsonSyntaxException;

/**
 * @param <V> the type of the wrapped value
 *
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract sealed class JsonElement<V> permits JsonContainer, JsonPrimitive, JsonNull {

    private final V value;

    // ==== CONSTRUCTORS ===================================================== //

    JsonElement(V value) {
        this.value = value;
    }

    // ==== METHODS ========================================================== //

    /** Returns the name of the type of this JsonElement. */
    public final String typeName() {
        return getClass().getSimpleName();
    }

    /**
     * Converts this into the respective {@link com.google.gson.JsonElement}.
     *
     * @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore.
     */
    @Deprecated
    abstract com.google.gson.JsonElement toGsonElem();

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

    /** Returns the wrapped value of this JsonElement. */
    public final V getValue() {
        return value;
    }

    // ==== PRIMITIVE ======================================================== //

    /** @return {@link OptionalInt} whether this is a {@link JsonInteger} which can be represented as an {@code int}. */
    @SuppressWarnings({"ClassReferencesSubclass", "InstanceofThis"})
    public final OptionalInt tryGetInt() {
        try {
            return this instanceof JsonInteger j
                   ? OptionalInt.of(j.getValue().intValueExact())
                   : OptionalInt.empty();
        } catch (ArithmeticException ae) {
            return OptionalInt.empty();
        }
    }

    /** @return {@link OptionalLong} whether this is a {@link JsonInteger} which can be represented as a {@code long}. */
    @SuppressWarnings({"ClassReferencesSubclass", "InstanceofThis"})
    public final OptionalLong tryGetLong() {
        try {
            return this instanceof JsonInteger j
                   ? OptionalLong.of(j.getValue().longValueExact())
                   : OptionalLong.empty();
        } catch (ArithmeticException ae) {
            return OptionalLong.empty();
        }
    }

    /** @return {@link OptionalDouble} whether this is a {@link JsonNumber} which can be represented as a {@code double}. */
    public final OptionalDouble tryGetDouble() {
        var nr = tryGetNumber();
        return nr.map(number -> OptionalDouble.of(number.doubleValue()))
                .orElseGet(OptionalDouble::empty);
    }

    // ==== OBJECT =========================================================== //

    /** @return {@link Enum} whether this is a {@link JsonString} with the representing name. */
    public final <E extends Enum<E>> Optional<E> tryGetEnum(Class<E> enumType) {
        return tryGetString().map(s -> Enum.valueOf(enumType, s));
    }

    /** @return {@link Optional<Number>} whether this is a {@link JsonNumber}. */
    @SuppressWarnings({"ClassReferencesSubclass", "InstanceofThis"})
    public final Optional<Number> tryGetNumber() {
        return this instanceof JsonNumber<?> j
               ? Optional.of(j.getValue())
               : Optional.empty();
    }

    /** @return {@link Optional<String>} whether this is a {@link JsonString}. */
    @SuppressWarnings({"ClassReferencesSubclass", "InstanceofThis"})
    public final Optional<String> tryGetString() {
        return this instanceof JsonString j
               ? Optional.of(j.getValue()) : Optional.empty();
    }

    /** @return {@link Optional<BigInteger>} whether this is a {@link JsonInteger}. */
    @SuppressWarnings({"ClassReferencesSubclass", "InstanceofThis"})
    public final Optional<BigInteger> tryGetBigInt() {
        return this instanceof JsonInteger j
               ? Optional.of(j.getValue()) : Optional.empty();
    }

    /** @return {@link Optional<BigDecimal>} whether this is a {@link JsonNumber}. */
    @SuppressWarnings("ClassReferencesSubclass")
    public final Optional<BigDecimal> tryGetBigDec() {
        return switch (this) {
            case JsonDecimal j -> Optional.of(j.getValue());
            case JsonInteger j -> Optional.of(new BigDecimal(j.getValue()));
            default -> Optional.empty();
        };
    }

    /** @return {@link Optional<Boolean>} whether this is a {@link JsonBool}. */
    @SuppressWarnings({"ClassReferencesSubclass", "InstanceofThis"})
    public final Optional<Boolean> tryGetBool() {
        return this instanceof JsonBool j
               ? Optional.of(j.getValue()) : Optional.empty();
    }

    // ==== CONTAINER ======================================================== //

    /** @return {@link Optional<T> whether this is a {@link JsonMap} that represents the passed class. */
    public final <T> Optional<T> tryGetObj(Class<? extends T> cls) {
        try {
            return Optional.of(Json.instanceFromTree(this, cls));
        } catch (JsonSyntaxException jse) {
            return Optional.empty();
        }
    }

    /** @return {@link Optional<Stream>} whether this is a {@link JsonList} which elements represent the passed class. */
    @SuppressWarnings({"ClassReferencesSubclass", "InstanceofThis"})
    public final <T> Optional<Stream<T>> tryGetStreamOf(Class<? extends T> elemCls) {
        return this instanceof JsonList l
               ? Optional.of(l.stream().map(e -> Json.instanceFromTree(e, elemCls)))
               : Optional.empty();
    }

    /** @return {@link Optional<List>} (mutable) whether this is a {@link JsonList} which elements represent the passed class. */
    public final <T> Optional<List<T>> tryGetListOf(Class<? extends T> elemCls) {
        return tryGetStreamOf(elemCls).map(stream -> stream.collect(Collectors.toList()));
    }

    /** @return {@link Optional<Set>} (mutable) whether this is a {@link JsonList} which elements represent the passed class. */
    public final <T> Optional<Set<T>> tryGetSetOf(Class<? extends T> elemCls) {
        return tryGetStreamOf(elemCls).map(stream -> stream.collect(Collectors.toSet()));
    }

}