package de.trinext.framework.json;

import java.util.*;
import java.util.function.Function;
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

    public JsonList() {
        super(new ArrayList<>());
    }

    public JsonList(Object... elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    public JsonList(byte[] elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    public JsonList(short[] elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    public JsonList(int[] elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    public JsonList(long[] elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    public JsonList(float[] elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    public JsonList(double[] elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    public JsonList(boolean[] elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    public JsonList(char[] elems) {
        super(new ArrayList<>(elems.length));
        addAll(elems);
    }

    public JsonList(Iterable<?> elems) {
        super(elems instanceof Collection<?> c ? new ArrayList<>(c.size()) : new ArrayList<>());
        addAll(elems);
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

    @Override
    public String toString() {
        return value.toString();
    }

}