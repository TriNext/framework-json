package de.trinext.framework.json;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * The json representation of a mutable, ordered, numerically indexed, linear collection of {@link JsonElement}s.
 *
 * @author Dennis Woithe
 * @see List java equivalent
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class JsonList
        extends JsonContainer<List<JsonElement<?>>>
        implements Iterable<JsonElement<?>>
{

    // ==== CONSTRUCTORS ===================================================== //

    /** Creates an empty JsonList. */
    public JsonList() {
        super(new ArrayList<>());
    }

    private JsonList(int capacity) {
        super(new ArrayList<>(capacity));
    }

    /** @deprecated use {@link #from(Object[])} instead. */
    @Deprecated
    public JsonList(Object... elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    /** @deprecated use {@link #from(byte[])} instead. */
    @Deprecated
    public JsonList(byte[] elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    /** @deprecated use {@link #from(short[])} instead. */
    @Deprecated
    public JsonList(short[] elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    /** @deprecated use {@link #from(int[])} instead. */
    @Deprecated
    public JsonList(int[] elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    /** @deprecated use {@link #from(long[])} instead. */
    @Deprecated
    public JsonList(long[] elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    /** @deprecated use {@link #from(float[])} instead. */
    @Deprecated
    public JsonList(float[] elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    /** @deprecated use {@link #from(double[])} instead. */
    @Deprecated
    public JsonList(double[] elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    /** @deprecated use {@link #from(boolean[])} instead. */
    @Deprecated
    public JsonList(boolean[] elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    /** @deprecated use {@link #from(char[])} instead. */
    @Deprecated
    public JsonList(char[] elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    /** @deprecated use {@link #from(Iterable)} instead. */
    @Deprecated
    public JsonList(Iterable<?> elems) {
        super(elems instanceof Collection<?> c ? new ArrayList<>(c.size()) : new ArrayList<>());
        addAll(elems);
    }

    /** Creates a new JsonList instance from the given elements. */
    public static JsonList from(Object... elems) {
        var instance = new JsonList(elems.length);
        instance.addAll(elems);
        return instance;
    }

    /** Creates a new JsonList instance from the given elements. */
    public static JsonList from(byte[] elems) {
        var instance = new JsonList(elems.length);
        instance.addAll(elems);
        return instance;
    }

    /** Creates a new JsonList instance from the given elements. */
    public static JsonList from(short[] elems) {
        var instance = new JsonList(elems.length);
        instance.addAll(elems);
        return instance;
    }

    /** Creates a new JsonList instance from the given elements. */
    public static JsonList from(int[] elems) {
        var instance = new JsonList(elems.length);
        instance.addAll(elems);
        return instance;
    }

    /** Creates a new JsonList instance from the given elements. */
    public static JsonList from(long[] elems) {
        var instance = new JsonList(elems.length);
        instance.addAll(elems);
        return instance;
    }

    /** Creates a new JsonList instance from the given elements. */
    public static JsonList from(float[] elems) {
        var instance = new JsonList(elems.length);
        instance.addAll(elems);
        return instance;
    }

    /** Creates a new JsonList instance from the given elements. */
    public static JsonList from(double[] elems) {
        var instance = new JsonList(elems.length);
        instance.addAll(elems);
        return instance;
    }

    /** Creates a new JsonList instance from the given elements. */
    public static JsonList from(boolean[] elems) {
        var instance = new JsonList(elems.length);
        instance.addAll(elems);
        return instance;
    }

    /** Creates a new JsonList instance from the given elements. */
    public static JsonList from(char[] elems) {
        var instance = new JsonList(elems.length);
        instance.addAll(elems);
        return instance;
    }

    /** Creates a new JsonList instance from the given elements. */
    public static JsonList from(Iterable<?> elems) {
        var instance = new JsonList();
        instance.addAll(elems);
        return instance;
    }


    // ==== METHODS ========================================================== //

    public JsonList add(Object elem) {
        value.add(Json.treeFromInstance(elem));
        return this;
    }

    public JsonList addList(Object... elems) {
        return add(new JsonList().addAll(elems));
    }

    public JsonList addList(byte[] elems) {
        return add(new JsonList().addAll(elems));
    }

    public JsonList addList(short[] elems) {
        return add(new JsonList().addAll(elems));
    }

    public JsonList addList(int[] elems) {
        return add(new JsonList().addAll(elems));
    }

    public JsonList addList(long[] elems) {
        return add(new JsonList().addAll(elems));
    }

    public JsonList addList(float[] elems) {
        return add(new JsonList().addAll(elems));
    }

    public JsonList addList(double[] elems) {
        return add(new JsonList().addAll(elems));
    }

    public JsonList addList(boolean[] elems) {
        return add(new JsonList().addAll(elems));
    }

    public JsonList addList(char[] elems) {
        return add(new JsonList().addAll(elems));
    }

    public JsonList addList(Iterable<?> it) {
        return add(new JsonList().addAll(it));
    }

    public JsonList addAll(Object... elems) {
        Arrays.stream(elems).forEach(this::add);
        return this;
    }

    public JsonList addAll(byte[] elems) {
        for (var elem : elems) add(elem);
        return this;
    }

    public JsonList addAll(short[] elems) {
        for (var elem : elems) add(elem);
        return this;
    }

    public JsonList addAll(int[] elems) {
        Arrays.stream(elems).forEach(this::add);
        return this;
    }

    public JsonList addAll(long[] elems) {
        Arrays.stream(elems).forEach(this::add);
        return this;
    }

    public JsonList addAll(float[] elems) {
        for (var elem : elems) add(elem);
        return this;
    }

    public JsonList addAll(double[] elems) {
        Arrays.stream(elems).forEach(this::add);
        return this;
    }

    public JsonList addAll(boolean[] elems) {
        for (var elem : elems) add(elem);
        return this;
    }

    public JsonList addAll(char[] elems) {
        for (var elem : elems) add(elem);
        return this;
    }

    public JsonList addAll(Iterable<?> it) {
        it.forEach(this::add);
        return this;
    }

    @SuppressWarnings("BoundedWildcard")
    public JsonList addObj(Function<JsonMap, JsonMap> elem) {
        return add(elem.apply(new JsonMap()));
    }

    public Optional<JsonElement<?>> tryGet(int idx) {
        return Optional.ofNullable(value.get(idx));
    }

    /** @return the amount of elements in this array. */
    public int size() {
        return value.size();
    }

    /** @return whether this array is empty. */
    public boolean isEmpty() {
        return value.isEmpty();
    }

    public boolean contains(Object elem) {
        return value.contains(Json.treeFromInstance(elem));
    }

    public boolean removeAt(int idx) {
        return value.remove(idx) != null;
    }

    public void clear() {
        value.clear();
    }

    @Override
    public Iterator<JsonElement<?>> iterator() {
        return value.iterator();
    }

    public Stream<JsonElement<?>> stream() {
        return value.stream();
    }

    /**
     * Returns this list as a {@code byte[]}.
     * <p>
     * If a value doesn't fit into a byte, it gets truncated according to the
     * <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.
     *
     * @throws IllegalStateException if this list contains elements that are not {@link JsonNumber}s.
     * @see JsonNumber#getAsByte()
     */
    public final byte[] getAsByteArray() {
        var res = new byte[size()];
        for (var i = 0; i < res.length; i++) {
            if (value.get(i) instanceof JsonNumber<?> jNr)
                res[i] = jNr.getAsByte();
            else throw Json.CANT_CONVERT_AT_IDX.apply(i, JsonNumber.class);
        }
        return res;
    }

    /**
     * Returns this list as a {@code short[]}.
     * <p>
     * If a value doesn't fit into a short, it gets truncated according to the
     * <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.
     *
     * @throws IllegalStateException if this list contains elements that are not {@link JsonNumber}s.
     * @see JsonNumber#getAsShort()
     */
    public final short[] getAsShortArray() {
        var res = new short[size()];
        for (var i = 0; i < res.length; i++) {
            if (value.get(i) instanceof JsonNumber<?> jNr)
                res[i] = jNr.getAsShort();
            else throw Json.CANT_CONVERT_AT_IDX.apply(i, JsonNumber.class);
        }
        return res;
    }

    /**
     * Returns this list as a {@code int[]}.
     * <p>
     * If a value doesn't fit into a long, it gets truncated according to the
     * <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.
     *
     * @throws IllegalStateException if this list contains elements that are not {@link JsonNumber}s.
     * @see JsonNumber#getAsInt()
     */
    public final int[] getAsIntArray() {
        var res = new int[size()];
        for (var i = 0; i < res.length; i++) {
            if (value.get(i) instanceof JsonNumber<?> jNr)
                res[i] = jNr.getAsInt();
            else throw Json.CANT_CONVERT_AT_IDX.apply(i, JsonNumber.class);
        }
        return res;
    }

    /**
     * Returns this list as a {@code long[]}.
     * <p>
     * If a value doesn't fit into a long, it gets truncated according to the
     * <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.
     *
     * @throws IllegalStateException if this list contains elements that are not {@link JsonNumber}s.
     * @see JsonNumber#getAsLong()
     */
    public final long[] getAsLongArray() {
        var res = new long[size()];
        for (var i = 0; i < res.length; i++) {
            if (value.get(i) instanceof JsonNumber<?> jNr)
                res[i] = jNr.getAsLong();
            else throw Json.CANT_CONVERT_AT_IDX.apply(i, JsonNumber.class);
        }
        return res;
    }

    /**
     * Returns this list as a {@code float[]}.
     * <p>
     * If a value doesn't fit into a float, it gets truncated according to the
     * <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.
     *
     * @throws IllegalStateException if this list contains elements that are not {@link JsonNumber}s.
     * @see JsonNumber#getAsFloat()
     */
    public final float[] getAsFloatArray() {
        var res = new float[size()];
        for (var i = 0; i < res.length; i++) {
            if (value.get(i) instanceof JsonNumber<?> jNr)
                res[i] = jNr.getAsFloat();
            else throw Json.CANT_CONVERT_AT_IDX.apply(i, JsonNumber.class);
        }
        return res;
    }

    /**
     * Returns this list as a {@code double[]}.
     * <p>
     * If a value doesn't fit into a double, it gets truncated according to the
     * <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.3">specification "Narrowing Primitive Conversion"</a>.
     *
     * @throws IllegalStateException if this list contains elements that are not {@link JsonNumber}s.
     * @see JsonNumber#getAsDouble()
     */
    public final double[] getAsDoubleArray() {
        var res = new double[size()];
        for (var i = 0; i < res.length; i++) {
            if (value.get(i) instanceof JsonNumber<?> jNr)
                res[i] = jNr.getAsDouble();
            else throw Json.CANT_CONVERT_AT_IDX.apply(i, JsonNumber.class);
        }
        return res;
    }

    /**
     * Returns this list as a {@code boolean[]}.
     *
     * @throws IllegalStateException if this list contains elements that are not {@link JsonBool}s.
     * @see JsonBool#getAsBool()
     */
    public final boolean[] getAsBoolArray() {
        var res = new boolean[size()];
        for (var i = 0; i < res.length; i++) {
            if (value.get(i) instanceof JsonBool jBool)
                res[i] = jBool.getAsBool();
            else throw Json.CANT_CONVERT_AT_IDX.apply(i, JsonBool.class);
        }
        return res;
    }

    /**
     * Returns this list as a {@code char[]}.
     *
     * @throws IllegalStateException if this list contains elements that are not {@link JsonString}s.
     * @see JsonString#getAsChar()
     */
    public final char[] getAsCharArray() {
        var res = new char[size()];
        for (var i = 0; i < res.length; i++) {
            if (value.get(i) instanceof JsonString jStr)
                res[i] = jStr.getAsChar();
            else throw Json.CANT_CONVERT_AT_IDX.apply(i, JsonString.class);
        }
        return res;
    }

    /**
     * Tries to return this element as an array of the passed class.
     * This can lead to various
     *
     * @param elemCls the class of the elements in the array.
     *
     * @see Json#instanceFromTree(JsonElement, Class)
     */
    public final <T> T[] getAsArrayOf(Class<? extends T> elemCls) {
        var res = (T[]) Array.newInstance(elemCls, size());
        for (var i = 0; i < res.length; i++) {
            res[i] = Json.instanceFromTree(value.get(i), elemCls);
        }
        return res;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}