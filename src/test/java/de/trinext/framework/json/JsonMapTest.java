package de.trinext.framework.json;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
    private static final String UNIT_TESTS = "unit_tests";
    private static final String FIELD_4 = "FIELD_4";
    private JsonObject jsonTestObject;
    private final JsonMap testMap = new JsonMap();

    @BeforeEach
    void setUp() {
        testMap.add(FIELD_1, VALUE_1);
        testMap.add(FIELD_2, 2);
        testMap.add(FIELD_3, 3);
        jsonTestObject = JsonParser.parseString("""
                                                {
                                                  "unit_tests": [
                                                    {
                                                      "id": 1,
                                                      "name": "Test 1",
                                                      "description": "Tests the functionality of X",
                                                      "input": {
                                                        "data": "example data",
                                                        "expectedOutput": "expected result"
                                                      },
                                                      "output": {
                                                        "actualOutput": "actual result",
                                                        "status": "pass"
                                                      }
                                                    },
                                                    {
                                                      "id": 2,
                                                      "name": "Test 2",
                                                      "description": "Tests the functionality of Y",
                                                      "input": {
                                                        "data": "example data",
                                                        "expectedOutput": "expected result"
                                                      },
                                                      "output": {
                                                        "actualOutput": "actual result",
                                                        "status": "fail"
                                                      }
                                                    },
                                                    {
                                                      "id": 3,
                                                      "name": "Test 3",
                                                      "description": "Tests the functionality of Z",
                                                      "input": {
                                                        "data": "example data",
                                                        "expectedOutput": "expected result"
                                                      },
                                                      "output": {
                                                        "actualOutput": "actual result",
                                                        "status": "pass"
                                                      }
                                                    }
                                                  ]
                                                }
                                                                                                                   
                                                                                                                   
                                                """).getAsJsonObject();
    }

    @Test @SuppressWarnings("deprecation")
    void test_json_map_from() {
        var parsedTestMap = JsonMap.from(jsonTestObject);
        assertEquals(1, parsedTestMap.stream().count());
        assertEquals(3, parsedTestMap.tryGet(UNIT_TESTS).stream().count());
    }

    @Test
    void test_json_map_iterator() {
        var iterations = new AtomicInteger();
        testMap.iterator().forEachRemaining(x -> iterations.incrementAndGet());
        assertEquals(3, iterations.get());
        var testmapIterator = testMap.iterator();
        assertTrue(testmapIterator.hasNext());
        var firstEntry = testmapIterator.next();
        assertEquals(FIELD_1, firstEntry.getKey());
        assertEquals(VALUE_1, firstEntry.getValue().tryGetString().orElseThrow());
        assertTrue(testmapIterator.hasNext());
        var secondEntry = testmapIterator.next();
        assertEquals(FIELD_2, secondEntry.getKey());
        assertEquals(2, secondEntry.getValue().tryGetInt().orElseThrow());
        var thirdEntry = testmapIterator.next();
        assertEquals(FIELD_3, thirdEntry.getKey());
        assertEquals(3, thirdEntry.getValue().tryGetInt().orElseThrow());
        assertFalse(testmapIterator.hasNext());
    }

    @Test
    void test_contains() {
        assertTrue(testMap.contains(FIELD_1));
        assertTrue(testMap.contains(FIELD_2));
        assertTrue(testMap.contains(FIELD_3));
        assertFalse(testMap.contains("fail"));
        assertThrows(NullPointerException.class,() -> testMap.contains(null));
    }

    @Test
    void test_addArr() {
        var testArray = new String[]{FIELD_1, FIELD_2, FIELD_3};
        assertThrows(JsonFieldAlreadyExistsException.class,() -> testMap.addArr(FIELD_1, (Object[]) testArray));
        assertTrue(testMap.addArr(FIELD_4, (Object[]) testArray).contains(FIELD_4));

    }
    @Test
    void test_add() {
        assertThrows(JsonFieldAlreadyExistsException.class,() -> testMap.add(FIELD_1, VALUE_1));
        testMap.addArr(FIELD_4, VALUE_1);
        assertEquals(VALUE_1, testMap.getValue().get(FIELD_1).getValue());

    }


    @Test
    void test_toGsonElem() {
        var conversionResult = testMap.toGsonElem().asMap();
        assertEquals(VALUE_1, conversionResult.get(FIELD_1).getAsString());
        assertEquals(2, conversionResult.get(FIELD_2).getAsInt());
        assertEquals(3, conversionResult.get(FIELD_3).getAsInt());
        assertNotEquals(4, conversionResult.get(FIELD_3).getAsInt());
    }

    @Test
    void test_toString() {
        assertEquals(
                "{\"" + FIELD_1 + "\":\"" + VALUE_1 + "\", " +
                        "\"" + FIELD_2 + "\":" + 2 + ", " +
                        "\"" + FIELD_3 + "\":" + 3 + "}", testMap.toString());
    }

    @Test
    void test_stream() {
        assertEquals(3, testMap.stream().count());
        assertTrue(testMap.stream().anyMatch(x -> x.getValue().getValue().equals(VALUE_1)));
        assertEquals(testMap.getValue().entrySet().stream().toList(), testMap.stream().toList());
    }

    @Test
    void test_add_obj() {
        var jMap = new JsonMap();
        jMap.addObj("obj", obj -> obj.add("int", 1));
        var jObj = jMap.tryGetPathAsInt("obj.int");
        assertTrue(jObj.isPresent());
        assertEquals(1, jObj.getAsInt());
    }

    @Test
    void test_json_type_name() {
        assertEquals(JsonMap.class.getSimpleName(), new JsonMap().typeName());
    }

    @Test
    void test_tryGet() {
        assertEquals("\"" + VALUE_1 + "\"", testMap.tryGet(FIELD_1).orElseThrow().toString());
        assertNotEquals("\"" + VALUE_1 + "\"", testMap.tryGet(FIELD_2).orElseThrow().toString());
        assertThrows(NoSuchElementException.class,() -> testMap.tryGet(FIELD_4).orElseThrow().toString());
    }


}