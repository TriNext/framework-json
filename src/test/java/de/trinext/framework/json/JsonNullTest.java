package de.trinext.framework.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author Dennis Woithe
 * @see JsonNull
 */
class JsonNullTest {

    @Test
    void test_from_obj() {
        assertEquals(JsonNull.NULL, JsonPrimitive.from(null));
    }

    @Test
    void test_type_name() {
        assertEquals(JsonNull.class.getSimpleName(), JsonNull.NULL.typeName());
    }

    @Test
    void test_hash_code() {
        assertEquals(0, JsonNull.NULL.hashCode());
    }

    @Test
    void test_equals() {
        assertEquals(JsonNull.NULL, JsonNull.NULL);
        assertNotEquals(null, JsonNull.NULL);
    }

    @Test
    void test_to_string() {
        assertEquals("null", JsonNull.NULL.toString());
    }

    @Test
    void test_find_path() {
        // TODO: Implement me!
    }

    @Test
    void test_remove_path() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_byte() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_short() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_int() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_long() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_float() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_double() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_bool() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_char() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_number() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_string() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_big_int() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_big_dec() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_date() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_time() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_date_time() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_enum() {
        // TODO: Implement me!
    }


    @Test
    void test_try_get_as_obj_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_obj_with_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_stream_of_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_list_of_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_set_of_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_stream_of_with_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_list_of_with_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_set_of_with_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_array_of_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_array_of_with_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_byte_array() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_short_array() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_int_array() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_long_array() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_float_array() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_double_array() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_boolean_array() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_char_array() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_byte() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_short() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_int() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_long() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_double() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_float() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_number() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_string() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_big_int() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_big_dec() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_bool() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_date() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_time() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_date_time() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_enum() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_obj_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_obj_with_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_stream_of_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_stream_of_with_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_list_of_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_list_of_with_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_set_of_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_set_of_with_function() {
        // TODO: Implement me!
    }

}