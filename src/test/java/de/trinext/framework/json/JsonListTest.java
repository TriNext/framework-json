package de.trinext.framework.json;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.util.RandomHelper.*;
import static test.util.TestConstants.ELEMS_PER_LIST;

/**
 * @author Dennis Woithe
 */
final class JsonListTest {

    @Test
    void test_constructor_varargs() {
        var randomInts = randomInts(ELEMS_PER_LIST).toArray();
        var list = new JsonList(randomInts);
        assertArrayEquals(randomInts, list.tryGetAsIntArray().orElseThrow());
    }

    @Test
    void test_constructor_iterable() {
        var randomInts = randomInts(ELEMS_PER_LIST).boxed().toList();
        var list = new JsonList(randomInts);
        assertEquals(randomInts, list.tryGetAsListOf(Integer.class).orElseThrow());
    }

    @Test
    void test_get_index() {
        var elems = randomInts(ELEMS_PER_LIST)
                .mapToObj(JsonInteger::from)
                .toArray(JsonInteger[]::new);
        var jList = new JsonList((Object[]) elems);
        for (var i = 0; i < elems.length; i++) {
            var res = jList.tryGet(i);
            assertTrue(res.isPresent());
            assertEquals(elems[i], res.get());
        }
        assertEquals(ELEMS_PER_LIST, jList.size());
    }

    @Test
    void test_add() {
        var jList = new JsonList();
        assertTrue(jList.isEmpty());
        randomInts(ELEMS_PER_LIST).forEach(jList::add);
        assertEquals(ELEMS_PER_LIST, jList.size());
    }

    @Test
    void test_remove() {
        var jList = new JsonList();
        assertTrue(jList.isEmpty());
        randomInts(ELEMS_PER_LIST).forEach(jList::add);
        assertEquals(ELEMS_PER_LIST, jList.size());
        jList.removeAt(0);
        assertEquals(ELEMS_PER_LIST - 1, jList.size());
    }

    @Test
    void test_stream() {
        var jList = new JsonList();
        randomInts(ELEMS_PER_LIST).forEach(jList::add);
        assertArrayEquals(jList.value.toArray(), jList.stream().toArray());
    }

    @Test
    void test_iterator() {
        var elems = randomInts(ELEMS_PER_LIST)
                .mapToObj(JsonInteger::from)
                .toArray(JsonInteger[]::new);
        var iter = new JsonList((Object[]) elems).iterator();
        for (var i = 0; iter.hasNext(); i++)
            assertEquals(elems[i], iter.next());
    }

    @Test
    void test_to_string() {
        var elems = randomInts(ELEMS_PER_LIST)
                .mapToObj(JsonInteger::from)
                .toArray(JsonInteger[]::new);
        var jList = new JsonList((Object[]) elems);
        assertEquals(Arrays.toString(elems), jList.toString());
    }

    @Test
    void test_try_get_empty() {
        var jList = new JsonList();
        var res = jList.tryGetPath("");
        assertTrue(res.isPresent());
        assertEquals(jList, res.get());
    }

    @Test
    void test_try_get_index() {
        var randInt = randomInt();
        var jList = new JsonList().add(randInt);
        assertEquals(Optional.of(JsonInteger.from(randInt)), jList.tryGetPath("0"));
        assertEquals(jList.tryGet(0), jList.tryGetPath("0"));
        assertEquals(Optional.empty(), jList.tryGetPath("0.x"));
    }

    @Test
    void test_try_get_nested_obj() {
        var name1 = randomWord(5);
        var randInt = randomInt();
        // [ { "a": "b" } ] //
        var jList = new JsonList().addObj(obj -> obj.add(name1, randInt));
        var res = jList.tryGetPath("0." + name1);
        assertTrue(res.isPresent());
        assertEquals(JsonInteger.from(randInt), res.get());
    }

    @Test
    void test_json_type_name() {
        assertEquals(JsonList.class.getSimpleName(), new JsonList().typeName());
    }

    @Test
    void test_try_get_just_kleene_star() {
        var randomInts = randomInts(ELEMS_PER_LIST).boxed().toList();
        var jList = new JsonList(randomInts);
        // [ 1, 2, ... ] //
        var res = jList.tryGetPath("*");
        assertTrue(res.isPresent());
        assertEquals(new JsonList(randomInts), res.get());
    }

    @Test
    void test_try_get_kleene_star() {
        var name = randomWord(5);
        var randomInts = randomInts(ELEMS_PER_LIST).boxed().toList();
        var jList = new JsonList();
        // [ { "a": 1 }, { "a": 2 }, ... ] //
        randomInts.forEach(e -> jList.addObj(jObj -> jObj.add(name, e)));
        var res = jList.tryGetPath("*." + name);
        assertTrue(res.isPresent());
        assertEquals(new JsonList(randomInts), res.get());
    }

    @Test
    void test_try_get_nested_kleene_star() {
        var name = randomWord(5);
        var randomIntLists = IntStream.range(0, ELEMS_PER_LIST)
                .mapToObj(ignored -> randomInts(3).boxed().toList())
                .toList();
        var jList = new JsonList();
        // [ { "a": [1, 2, 3] }, { "a": [1, 2, 3] }, ... ] //
        randomIntLists.forEach(intList -> jList.addObj(jObj -> jObj.add(name, new JsonList(intList))));
        var res = jList.tryGetPath("*." + name + ".*");
        assertTrue(res.isPresent());
        assertEquals(new JsonList(randomIntLists), res.get());
    }

    @Test
    void test_remove_path() {
        var randomInts = randomInts(ELEMS_PER_LIST).boxed().toList();
        // [ 1, 2, ... ] //
        var jList = new JsonList(randomInts);
        assertEquals(randomInts.size(), jList.size());
        assertTrue(jList.removePath("0"));
        assertEquals(randomInts.size() - 1, jList.size());
    }

    @Test
    void test_remove_nested_path() {
        var name = randomWord(5);
        var randomInts = randomInts(ELEMS_PER_LIST).boxed().collect(Collectors.toSet());
        var jList = new JsonList();
        // [ { "a": 1 }, { "a": 2 }, ... ] //
        randomInts.forEach(e -> jList.addObj(jObj -> jObj.add(name, e)));
        assertEquals(randomInts.size(), jList.size());
        assertTrue(jList.tryGetPath("0." + name).isPresent());
        // ---- //
        assertTrue(jList.removePath("0." + name));
        // ---- //
        assertEquals(randomInts.size(), jList.size());
        var first = jList.tryGet(0);
        assertTrue(first.isPresent());
        assertEquals(new JsonMap(), first.get());
        assertTrue(jList.tryGetPath("0." + name).isEmpty());
    }

    @Test
    void test_remove_just_kleene_star() {
        var jList = new JsonList(randomInts(ELEMS_PER_LIST).boxed().toList());
        // [ 1, 2, ... ] //
        assertTrue(jList.removePath("*"));
        assertTrue(jList.isEmpty());
    }

    @Test
    void test_remove_kleene_star() {
        var name = randomWord(5);
        var randomInts = randomInts(ELEMS_PER_LIST).boxed().toList();
        var jList = new JsonList();
        // [ { "a": 1 }, { "a": 2 }, ... ] //
        randomInts.forEach(e -> jList.addObj(jObj -> jObj.add(name, e)));
        var jListWithEmptyMaps = new JsonList();
        randomInts.forEach(ignored -> jListWithEmptyMaps.add(new JsonMap()));
        assertTrue(jList.removePath("*." + name));
        assertEquals(jListWithEmptyMaps, jList);
    }

    @Test
    void test_size() {
        var jList = new JsonList();
        var size = randomInt(ELEMS_PER_LIST);
        randomInts(size).forEach(jList::add);
        assertEquals(size, jList.size());
    }

    @Test
    void test_contains() {
        var randomInts = randomInts(ELEMS_PER_LIST).boxed().toList();
        var jList = new JsonList(randomInts);
        randomInts.stream()
                .map(jList::contains)
                .forEach(Assertions::assertTrue);
    }

    @Test
    void test_add_list_varargs() {
        var randomInts = randomInts(ELEMS_PER_LIST).boxed().toList();
        var outer = new JsonList();
        outer.addList(randomInts.toArray());
        assertEquals(1, outer.size());
        assertEquals(new JsonList(randomInts), outer.tryGet(0).orElseThrow());
    }

    @Test
    void test_add_list_iterable() {
        var randomInts = randomInts(ELEMS_PER_LIST).boxed().toList();
        var outer = new JsonList();
        outer.addList(randomInts);
        assertEquals(1, outer.size());
        assertEquals(new JsonList(randomInts), outer.tryGet(0).orElseThrow());
    }

    @Test
    void test_add_all_varargs() {
        var randomInts = randomInts(ELEMS_PER_LIST).boxed().toList();
        var list = new JsonList();
        list.addAll(randomInts.toArray());
        assertEquals(randomInts.size(), list.size());
    }

    @Test
    void test_add_all_iterable() {
        var randomInts = randomInts(ELEMS_PER_LIST).boxed().toList();
        var list = new JsonList();
        list.addAll(randomInts);
        assertEquals(randomInts.size(), list.size());
    }

}