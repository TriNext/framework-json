package de.trinext.framework.json;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestHelper.*;

/**
 * @author Dennis Woithe
 */
class JsonListTest {

    private static final int ELEMS_PER_TEST = 100;

    // ==== METHODS ========================================================== //

    @Test
    void test_get_index() {
        var elems = randomInts(ELEMS_PER_TEST)
                .mapToObj(JsonInteger::from)
                .toArray(JsonInteger[]::new);
        var jList = new JsonList((Object[]) elems);
        for (var i = 0; i < elems.length; i++) {
            var res = jList.tryGet(i);
            assertTrue(res.isPresent());
            assertEquals(elems[i], res.get());
        }
        assertEquals(ELEMS_PER_TEST, jList.size());
    }

    @Test
    void test_add() {
        var jList = new JsonList();
        assertTrue(jList.isEmpty());
        randomInts(ELEMS_PER_TEST).forEach(jList::add);
        assertEquals(ELEMS_PER_TEST, jList.size());
    }

    @Test
    void test_stream() {
        var jList = new JsonList();
        randomInts(ELEMS_PER_TEST).forEach(jList::add);
        assertArrayEquals(jList.value.toArray(), jList.stream().toArray());
    }

    @Test
    void test_iterator() {
        var elems = randomInts(ELEMS_PER_TEST)
                .mapToObj(JsonInteger::from)
                .toArray(JsonInteger[]::new);
        var iter = new JsonList((Object[]) elems).iterator();
        for (var i = 0; iter.hasNext(); i++)
            assertEquals(elems[i], iter.next());
    }

    @Test
    void test_to_string() {
        var elems = randomInts(ELEMS_PER_TEST)
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
        var randomInts = randomInts(ELEMS_PER_TEST).boxed().toList();
        var jList = new JsonList(randomInts);
        // [ 1, 2, ... ] //
        var res = jList.tryGetPath("*");
        assertTrue(res.isPresent());
        assertEquals(new JsonList(randomInts), res.get());
    }

    @Test
    void test_try_get_kleene_star() {
        var name = randomWord(5);
        var randomInts = randomInts(ELEMS_PER_TEST).boxed().toList();
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
        var randomIntLists = IntStream.range(0, ELEMS_PER_TEST)
                .mapToObj(i -> randomInts(3).boxed().toList())
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
        var randomInts = randomInts(ELEMS_PER_TEST).boxed().toList();
        // [ 1, 2, ... ] //
        var jList = new JsonList(randomInts);
        assertEquals(randomInts.size(), jList.size());
        assertTrue(jList.removePath("0"));
        assertEquals(randomInts.size() - 1, jList.size());
    }

    @Test
    void test_remove_nested_path() {
        var name = randomWord(5);
        var randomInts = randomInts(ELEMS_PER_TEST).boxed().collect(Collectors.toSet());
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
        var jList = new JsonList(randomInts(ELEMS_PER_TEST).boxed().toList());
        // [ 1, 2, ... ] //
        assertTrue(jList.removePath("*"));
        assertTrue(jList.isEmpty());
    }

    @Test
    void test_remove_kleene_star() {
        var name = randomWord(5);
        var randomInts = randomInts(ELEMS_PER_TEST).boxed().toList();
        var jList = new JsonList();
        // [ { "a": 1 }, { "a": 2 }, ... ] //
        randomInts.forEach(e -> jList.addObj(jObj -> jObj.add(name, e)));
        var jListWithEmptyMaps = new JsonList();
        randomInts.forEach(e -> jListWithEmptyMaps.add(new JsonMap()));
        assertTrue(jList.removePath("*." + name));
        assertEquals(jListWithEmptyMaps, jList);
    }


}