package de.trinext.framework.json;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Dennis Woithe
 */
class JsonMapTest {

    private static final String FIELD_1 = "FIELD_1";
    private static final String FIELD_2 = "FIELD_2";
    private static final String FIELD_3 = "FIELD_3";
    private static final String VALUE_1 = "Test 1";
    private static final String FIELD_4 = "FIELD_4";
    private final JsonMap jsonTestMap = new JsonMap();

    private final Map<String, JsonElement<?>> testMap = new LinkedHashMap<>();

    @BeforeEach
    void setUp() {
        jsonTestMap.add(FIELD_1, VALUE_1);
        jsonTestMap.add(FIELD_2, 2);
        jsonTestMap.add(FIELD_3, 3);
        testMap.put(FIELD_1, JsonString.from(VALUE_1));
        testMap.put(FIELD_2, JsonInteger.from(2));
        testMap.put(FIELD_3, JsonInteger.from(3));
    }

    @Test
    void test_json_map_iterator() {
        var jIter = jsonTestMap.iterator();
        var testIter = testMap.entrySet().iterator();
        while (jIter.hasNext() && testIter.hasNext()) {
            var jEntry = jIter.next();
            var testEntry = testIter.next();
            assertEquals(testEntry.getKey(), jEntry.getKey());
            assertEquals(testEntry.getValue(), jEntry.getValue());
        }
        assertFalse(jIter.hasNext());
        assertFalse(testIter.hasNext());
    }

    @Test
    void test_contains() {
        assertTrue(jsonTestMap.contains(FIELD_1));
        assertTrue(jsonTestMap.contains(FIELD_2));
        assertTrue(jsonTestMap.contains(FIELD_3));
        assertFalse(jsonTestMap.contains(FIELD_4));
        assertThrows(IllegalArgumentException.class, () -> jsonTestMap.contains(null));
    }

    @Test
    void test_add_arr() {
        var testArray = new String[]{FIELD_1, FIELD_2, FIELD_3};
        assertThrows(JsonFieldAlreadyExistsException.class, () -> jsonTestMap.addArr(FIELD_1, (Object[]) testArray));
        assertTrue(jsonTestMap.addArr(FIELD_4, (Object[]) testArray).contains(FIELD_4));

    }

    @Test
    void test_add() {
        assertThrows(JsonFieldAlreadyExistsException.class, () -> jsonTestMap.add(FIELD_1, VALUE_1));
        jsonTestMap.addArr(FIELD_4, VALUE_1);
        assertEquals(VALUE_1, jsonTestMap.getValue().get(FIELD_1).getValue());

    }

    @Test
    void test_to_string() {
        assertEquals(
                "{\"" + FIELD_1 + "\":\"" + VALUE_1 + "\", " +
                        "\"" + FIELD_2 + "\":" + 2 + ", " +
                        "\"" + FIELD_3 + "\":" + 3 + "}", jsonTestMap.toString());
    }

    @Test
    void test_stream() {
        assertEquals(3, jsonTestMap.stream().count());
        assertTrue(jsonTestMap.stream().anyMatch(x -> x.getValue().getValue().equals(VALUE_1)));
        assertEquals(jsonTestMap.getValue().entrySet().stream().toList(), jsonTestMap.stream().toList());
    }

    @Test
    void test_add_obj() {
        var jMap = new JsonMap();
        jMap.addObj("obj", obj -> obj.add("int", 1));
        var optInt = jMap.tryGetPathAsInt("obj.int");
        assertTrue(optInt.isPresent());
        assertEquals(1, optInt.getAsInt());
    }

    @Test
    void test_json_type_name() {
        assertEquals(JsonMap.class.getSimpleName(), new JsonMap().typeName());
    }

    @Test
    void test_try_get() {
        assertEquals("\"" + VALUE_1 + "\"", jsonTestMap.tryGet(FIELD_1).orElseThrow().toString());
        assertNotEquals("\"" + VALUE_1 + "\"", jsonTestMap.tryGet(FIELD_2).orElseThrow().toString());
        assertThrows(NoSuchElementException.class, () -> jsonTestMap.tryGet(FIELD_4).orElseThrow().toString());
    }

    @Test
    void test_remove_key() {
        assertEquals(3, jsonTestMap.getValue().size());
        jsonTestMap.removeKey(FIELD_1);
        assertEquals(2, jsonTestMap.getValue().size());
    }


}