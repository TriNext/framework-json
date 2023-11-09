package de.trinext.framework.json;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestHelper.randomInts;

/**
 * @author Dennis Woithe
 */
class JsonArrayTest {

    private static final int ELEMS_PER_TEST = 100;

    // ==== METHODS ========================================================== //

    @Test
    void test_get_index() {
        var elems = randomInts(ELEMS_PER_TEST)
                .mapToObj(JsonInteger::from)
                .toArray(JsonInteger[]::new);
        var jArr = new JsonArray((Object[]) elems);
        for (var i = 0; i < elems.length; i++) {
            var res = jArr.tryGet(i);
            assertTrue(res.isPresent());
            assertEquals(elems[i], res.get());
        }
        assertEquals(ELEMS_PER_TEST, jArr.size());
    }

    @Test
    void test_add() {
        var jArr = new JsonArray();
        assertTrue(jArr.isEmpty());
        randomInts(ELEMS_PER_TEST).forEach(jArr::add);
        assertEquals(ELEMS_PER_TEST, jArr.size());
    }

    @Test
    void test_stream() {
        var jArr = new JsonArray();
        randomInts(ELEMS_PER_TEST).forEach(jArr::add);
        assertArrayEquals(jArr.getValue().toArray(), jArr.stream().toArray());
    }

    @Test
    void test_iterator() {
        var elems = randomInts(ELEMS_PER_TEST)
                .mapToObj(JsonInteger::from)
                .toArray(JsonInteger[]::new);
        var iter = new JsonArray((Object[]) elems).iterator();
        for (var i = 0; iter.hasNext(); i++)
            assertEquals(elems[i], iter.next());
    }

    @Test
    void test_to_string() {
        var elems = randomInts(ELEMS_PER_TEST)
                .mapToObj(JsonInteger::from)
                .toArray(JsonInteger[]::new);
        var jArr = new JsonArray((Object[]) elems);
        assertEquals(Arrays.toString(elems), jArr.toString());
    }

    @Test
    void test_try_get_empty() {
        var jArr = new JsonArray();
        var res = jArr.tryGetPath("");
        assertTrue(res.isPresent());
        assertEquals(jArr, res.get());
    }

    @Test
    void test_try_get_index() {
        var jArr = new JsonArray().add(10);
        assertEquals(jArr.tryGet(0), jArr.tryGetPath("0"));
        assertEquals(Optional.empty(), jArr.tryGetPath("0.x"));
    }

    @Test
    void test_try_get_nested_obj() {
        var jArr = new JsonArray().addObj(obj -> obj.add("a", "b"));
        var res = jArr.tryGetPath("0.a");
        assertTrue(res.isPresent());
        assertEquals(JsonString.from("b"), res.get());
    }

}