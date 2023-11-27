package de.trinext.framework.json;

import java.time.format.DateTimeFormatter;
import java.util.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestConstants.*;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings("SimpleDateFormatWithoutLocale") class JsonMapTest {
    private final JsonMap jsonTestMap = new JsonMap();
    private final JsonMap dateTimeTestMap = new JsonMap();
    private final JsonMap jsonPathTestMap = new JsonMap();
    private final Map<String, JsonElement<?>> testMap = new LinkedHashMap<>();

    @BeforeEach
    void setUp() {
        jsonTestMap.add(FIELD_1, STRING_CONSTANT);
        jsonTestMap.add(FIELD_2, 2);
        jsonTestMap.add(FIELD_3, 3);
        dateTimeTestMap.add(DATE_FIELD, DATE_CONSTANT);
        dateTimeTestMap.add(TIME_FIELD, TIME_CONSTANT);
        dateTimeTestMap.add(DATE_TIME_FIELD, DATE_CONSTANT + " " + TIME_CONSTANT);
        dateTimeTestMap.add(FIELD_1, STRING_CONSTANT);
        testMap.put(FIELD_1, JsonString.from(STRING_CONSTANT));
        testMap.put(FIELD_2, JsonInteger.from(2));
        testMap.put(FIELD_3, JsonInteger.from(3));
        jsonPathTestMap.add(STRING_FIELD, STRING_CONSTANT);
        jsonPathTestMap.add(INT_FIELD, 2);
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
        assertThrows(JsonFieldAlreadyExistsException.class, () -> jsonTestMap.add(FIELD_1, STRING_CONSTANT));
        jsonTestMap.addArr(FIELD_4, STRING_CONSTANT);
        assertEquals(STRING_CONSTANT, jsonTestMap.value.get(FIELD_1).value);

    }

    @Test
    void test_to_string() {
        assertEquals(
                "{\"" + FIELD_1 + "\":\"" + STRING_CONSTANT + "\", " +
                        "\"" + FIELD_2 + "\":" + 2 + ", " +
                        "\"" + FIELD_3 + "\":" + 3 + "}", jsonTestMap.toString());
    }

    @Test
    void test_stream() {
        assertEquals(3, jsonTestMap.stream().count());
        assertTrue(jsonTestMap.stream().anyMatch(x -> x.getValue().value.equals(STRING_CONSTANT)));
        assertEquals(jsonTestMap.value.entrySet().stream().toList(), jsonTestMap.stream().toList());
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
        assertEquals("\"" + STRING_CONSTANT + "\"", jsonTestMap.tryGet(FIELD_1).orElseThrow().toString());
        assertNotEquals("\"" + STRING_CONSTANT + "\"", jsonTestMap.tryGet(FIELD_2).orElseThrow().toString());
        assertThrows(NoSuchElementException.class, () -> jsonTestMap.tryGet(FIELD_4).orElseThrow().toString());
    }

    @Test
    void test_remove_key() {
        var size = jsonTestMap.value.size();
        var returnValue = jsonTestMap.removeKey(FIELD_1);
        assertTrue(returnValue);
        assertEquals(size - 1 , jsonTestMap.value.size());
        returnValue = jsonTestMap.removeKey(FIELD_1);
        assertFalse(returnValue);
        assertEquals(size - 1 , jsonTestMap.value.size());
    }

    @Test
    void test_try_get_path() {
        assertTrue(jsonPathTestMap.tryGetPath(INT_FIELD).isPresent());
        assertTrue(jsonPathTestMap.tryGetPath(NON_EXISTEND_FIELD).isEmpty());
    }

    @Test
    void test_find_path() {
        assertTrue(jsonPathTestMap.findPath(STRING_FIELD));
    }
    @Test
    void test_remove_path() {
        assertTrue(jsonPathTestMap.findPath(STRING_FIELD));
        jsonPathTestMap.removePath(STRING_FIELD);
        assertFalse(jsonPathTestMap.findPath(STRING_FIELD));
        //var jList = new JsonList(1,2,3);
        assertThrows(IllegalArgumentException.class, () -> jsonPathTestMap.removePath(""));
        //TODO: fix out of bounds exception
        //jList.removePath("5");

    }
    // TODO: fix add method for doublicate nested elements to prevent tests from breaking
//    @Test
//    void test_remove_path_nested() {
//        testMap.removePath(NESTED_FIELD + "." + STRING_FIELD);
//        assertFalse(testMap.findPath(NESTED_FIELD + "." + STRING_FIELD));
//    }

    @Test
    void test_try_get_as_date() {
        var date = dateTimeTestMap.tryGet(DATE_FIELD).orElseThrow().tryGetAsDate(DateTimeFormatter.ofPattern("dd.MM/yyyy"));
        assertTrue(date.isPresent());
        assertEquals("1995.01.03", date.get().format(DateTimeFormatter.ofPattern("yyyy.dd.MM")));
        date = dateTimeTestMap.tryGet(FIELD_1).orElseThrow().tryGetAsDate(DateTimeFormatter.ofPattern("dd.MM/yyyy"));
        assertTrue(date.isEmpty());
    }

    @Test
    void test_try_get_as_time() {
        var time = dateTimeTestMap.tryGet(TIME_FIELD).orElseThrow().tryGetAsTime(DateTimeFormatter.ofPattern("HH:mm:ss"));
        assertTrue(time.isPresent());
        assertEquals("56:12:34", time.get().format(DateTimeFormatter.ofPattern("ss:HH:mm")));
        time = dateTimeTestMap.tryGet(FIELD_1).orElseThrow().tryGetAsTime(DateTimeFormatter.ofPattern("dd.MM/yyyy"));
        assertTrue(time.isEmpty());
    }

    @Test
    void test_try_get_as_date_time() {
        var dateTime = dateTimeTestMap.tryGet(DATE_TIME_FIELD).orElseThrow().tryGetAsDateTime(DateTimeFormatter.ofPattern("dd.MM/yyyy HH:mm:ss"));
        assertTrue(dateTime.isPresent());
        assertEquals("1995.01.03 56:12:34", dateTime.get().format(DateTimeFormatter.ofPattern("yyyy.dd.MM ss:HH:mm")));
        dateTime = dateTimeTestMap.tryGet(FIELD_1).orElseThrow().tryGetAsDateTime(DateTimeFormatter.ofPattern("dd.MM/yyyy"));
        assertTrue(dateTime.isEmpty());
    }

    @Test
    void test_try_get_path_as_date() {
        var date = dateTimeTestMap.tryGetPathAsDate(DATE_FIELD, DateTimeFormatter.ofPattern("dd.MM/yyyy"));
        assertTrue(date.isPresent());
        assertEquals("1995.01.03", date.get().format(DateTimeFormatter.ofPattern("yyyy.dd.MM")));
        date = dateTimeTestMap.tryGetPathAsDate(FIELD_1, DateTimeFormatter.ofPattern("dd.MM/yyyy"));
        assertTrue(date.isEmpty());
    }

    @Test
    void test_try_get_path_as_time() {
        var time = dateTimeTestMap.tryGetPathAsTime(TIME_FIELD, DateTimeFormatter.ofPattern("HH:mm:ss"));
        assertTrue(time.isPresent());
        assertEquals("56:12:34", time.get().format(DateTimeFormatter.ofPattern("ss:HH:mm")));
        time = dateTimeTestMap.tryGetPathAsTime(FIELD_1, DateTimeFormatter.ofPattern("dd.MM/yyyy"));
        assertTrue(time.isEmpty());
    }

    @Test
    void test_try_get_path_as_date_time() {
        var dateTime = dateTimeTestMap.tryGetPathAsDateTime(DATE_TIME_FIELD, DateTimeFormatter.ofPattern("dd.MM/yyyy HH:mm:ss"));
        assertTrue(dateTime.isPresent());
        assertEquals("1995.01.03 56:12:34", dateTime.get().format(DateTimeFormatter.ofPattern("yyyy.dd.MM ss:HH:mm")));
        dateTime = dateTimeTestMap.tryGetPathAsDateTime(FIELD_1, DateTimeFormatter.ofPattern("dd.MM/yyyy"));
        assertTrue(dateTime.isEmpty());
    }

}