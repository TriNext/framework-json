package de.trinext.framework.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestConstants.*;

/**
 * @author Dennis Woithe
 */
class JsonBoolTest {

    @Test
    void test_get_value() {
        assertTrue(JsonBool.TRUE.value);
        assertFalse(JsonBool.FALSE.value);
    }

    @Test
    void test_hash_code() {
        assertEquals(Boolean.TRUE.hashCode(), JsonBool.TRUE.hashCode());
        assertEquals(Boolean.FALSE.hashCode(), JsonBool.FALSE.hashCode());
    }

    @Test @SuppressWarnings({"SimplifiableAssertion", "EqualsWithItself"})
    void test_equals() {
        assertTrue(JsonBool.TRUE.equals(JsonBool.TRUE));
        assertTrue(JsonBool.FALSE.equals(JsonBool.FALSE));

        assertFalse(JsonBool.TRUE.equals(JsonBool.FALSE));
        assertFalse(JsonBool.FALSE.equals(JsonBool.TRUE));

        assertFalse(JsonBool.TRUE.equals(null));
        assertFalse(JsonBool.FALSE.equals(null));
    }

    @Test
    void test_to_string() {
        assertEquals(Boolean.TRUE.toString(), JsonBool.TRUE.toString());
        assertEquals(Boolean.FALSE.toString(), JsonBool.FALSE.toString());
    }

    @Test
    void test_from_literal() {
        assertEquals(JsonBool.TRUE, JsonBool.from(true));
        assertEquals(JsonBool.FALSE, JsonBool.from(false));
    }

    @Test
    void test_json_type_name() {
        assertEquals(JsonBool.class.getSimpleName(), JsonBool.TRUE.typeName());
        assertEquals(JsonBool.class.getSimpleName(), JsonBool.FALSE.typeName());
    }

    @Test
    void test_try_get_path_as_bool() {
        var map = new JsonMap();
        map.add(field(1), Boolean.TRUE);
        map.add(field(2), Boolean.FALSE);
        map.add(field(3), true);
        map.add(field(4), false);
        assertTrue(map.tryGetPathAsBool(field(1)).orElseThrow());
        assertFalse(map.tryGetPathAsBool(field(2)).orElseThrow());
        assertTrue(map.tryGetPathAsBool(field(3)).orElseThrow());
        assertFalse(map.tryGetPathAsBool(field(4)).orElseThrow());
    }


}