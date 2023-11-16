package de.trinext.framework.json;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestHelper.randomInts;

/**
 * @author Dennis Woithe
 */
class JsonListTest {

    private static final int ELEMS_PER_TEST = 100;

    private static final int ARRAY_TEST_VALUE_1 = -1;
    private static final int ARRAY_TEST_VALUE_2 = 0;
    private static final int ARRAY_TEST_VALUE_3 = 1;

    private JsonList testArr;
    @BeforeEach
    void setUp() {
        testArr = new JsonList();
        testArr.add(ARRAY_TEST_VALUE_1);
        testArr.add(ARRAY_TEST_VALUE_2);
        testArr.add(ARRAY_TEST_VALUE_3);
    }

    // ==== METHODS ========================================================== //

    @Test
    void test_get_index() {
        var elems = randomInts(ELEMS_PER_TEST)
                .mapToObj(JsonInteger::from)
                .toArray(JsonInteger[]::new);
        var jArr = new JsonList((Object[]) elems);
        for (var i = 0; i < elems.length; i++) {
            var res = jArr.tryGet(i);
            assertTrue(res.isPresent());
            assertEquals(elems[i], res.get());
        }
        assertEquals(ELEMS_PER_TEST, jArr.size());
    }

    @Test
    void test_add() {
        var jArr = new JsonList();
        assertTrue(jArr.isEmpty());
        randomInts(ELEMS_PER_TEST).forEach(jArr::add);
        assertEquals(ELEMS_PER_TEST, jArr.size());
    }
    @Test
    void test_remove() {
        var jArr = new JsonList();
        assertTrue(jArr.isEmpty());
        randomInts(ELEMS_PER_TEST).forEach(jArr::add);
        assertEquals(ELEMS_PER_TEST, jArr.size());
        jArr.removeAt(0);
        assertEquals(ELEMS_PER_TEST-1, jArr.size());
    }

    @Test
    void test_stream() {
        var jArr = new JsonList();
        randomInts(ELEMS_PER_TEST).forEach(jArr::add);
        assertArrayEquals(jArr.getValue().toArray(), jArr.stream().toArray());
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
        var jArr = new JsonList((Object[]) elems);
        assertEquals(Arrays.toString(elems), jArr.toString());
    }

    @Test
    void test_try_get_empty() {
        var jArr = new JsonList();
        var res = jArr.tryGetPath("");
        assertTrue(res.isPresent());
        assertEquals(jArr, res.get());
    }

    @Test
    void test_try_get_index() {
        var jArr = new JsonList().add(10);
        assertEquals(jArr.tryGet(0), jArr.tryGetPath("0"));
        assertEquals(Optional.empty(), jArr.tryGetPath("0.x"));
    }

    @Test
    void test_try_get_nested_obj() {
        var jArr = new JsonList().addObj(obj -> obj.add("a", "b"));
        var res = jArr.tryGetPath("0.a");
        assertTrue(res.isPresent());
        assertEquals(JsonString.from("b"), res.get());
    }

    @Test
    void test_json_type_name() {
        assertEquals(JsonList.class.getSimpleName(), new JsonList().typeName());
    }

    @Test
    void test_getSize() {
        var jArr = new JsonList();
        randomInts(ELEMS_PER_TEST).forEach(jArr::add);
        assertEquals(100, jArr.getValue().size());
    }

    @Test
    void test_contains() {
        assertTrue(testArr.contains(ARRAY_TEST_VALUE_1));
        assertTrue(testArr.contains(ARRAY_TEST_VALUE_2));
        assertTrue(testArr.contains(ARRAY_TEST_VALUE_3));
        assertFalse(testArr.contains("fail"));
        assertFalse(testArr.contains(null));
    }

    @Test
    void test_size() {
        var jArr = new JsonList();
        assertEquals(0,jArr.size());
        jArr.add(ARRAY_TEST_VALUE_1);
        assertEquals(1,jArr.size());
        jArr.add(ARRAY_TEST_VALUE_2);
        assertEquals(2,jArr.size());
        //TODO: check if size behaves correctly after removing an element
    }
    @Test
    void test_addArr() {
        var jArr = new JsonList().addArr(ARRAY_TEST_VALUE_1, ARRAY_TEST_VALUE_2, ARRAY_TEST_VALUE_3);
        assertEquals(1, jArr.size());
        jArr.addArr(ARRAY_TEST_VALUE_1, ARRAY_TEST_VALUE_2, ARRAY_TEST_VALUE_3);
        assertEquals(2, jArr.size());
    }
}