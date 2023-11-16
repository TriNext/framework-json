package de.trinext.framework.json;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestHelper.randomInts;

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
        assertArrayEquals(jList.getValue().toArray(), jList.stream().toArray());
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
        var jList = new JsonList().add(10);
        assertEquals(jList.tryGet(0), jList.tryGetPath("0"));
        assertEquals(Optional.empty(), jList.tryGetPath("0.x"));
    }

    @Test
    void test_try_get_nested_obj() {
        var jList = new JsonList().addObj(obj -> obj.add("a", "b"));
        var res = jList.tryGetPath("0.a");
        assertTrue(res.isPresent());
        assertEquals(JsonString.from("b"), res.get());
    }

    @Test
    void test_json_type_name() {
        assertEquals(JsonList.class.getSimpleName(), new JsonList().typeName());
    }

}