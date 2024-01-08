package de.trinext.framework.json;

import org.junit.jupiter.api.Test;

import static de.trinext.framework.util.RandomHelper.randomString;
import static de.trinext.framework.util.RandomHelper.runForRandomStrings;
import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestConstants.WORDS_PER_TEST;
import static test.util.TestConstants.WORD_LENGTH;

/**
 * @author Dennis Woithe
 * @see JsonString
 */
class JsonStringTest {

    @Test
    void test_from_char() {
        // TODO: Implement me!
    }

    @Test
    void test_from_chars() {
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST, randStr -> assertEquals(
                randStr,
                JsonString.from(randStr).value
        ));
    }

    @Test
    void test_from_enum() {
        // TODO: Implement me!
    }

    @Test
    void test_get_as_string() {
        // TODO: Implement me!
    }

    @Test
    void test_get_as_char() {
        // TODO: Implement me!
    }

    @Test
    void test_get_as_enum() {
        // TODO: Implement me!
    }

    @Test
    void test_get_as_UUID() {
        // TODO: Implement me!
    }

    @Test
    void test_get_as_pattern() {
        // TODO: Implement me!
    }

    @Test
    void test_to_string() {
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST,
                randStr -> assertEquals(
                        JsonPrimitive.from(randStr).toString(),
                        JsonString.from(randStr).toString()
                ));
        assertEquals("\"\\\"\"", JsonString.from("\"").toString());
        assertEquals("\"\\\\\"", JsonString.from("\\").toString());
        assertEquals("\"\\/\"", JsonString.from("/").toString());
        assertEquals("\"\\b\"", JsonString.from("\b").toString());
        assertEquals("\"\\f\"", JsonString.from("\f").toString());
        assertEquals("\"\\n\"", JsonString.from("\n").toString());
        assertEquals("\"\\r\"", JsonString.from("\r").toString());
        assertEquals("\"\\t\"", JsonString.from("\t").toString());
        assertEquals("\"\\u0000\"", JsonString.from("\0").toString());
        // Convert Unicode (UTF-8) to json and back
        assertEquals("÷", Json.instanceFromString(JsonString.from("÷").toString(), String.class));
    }

    @Test
    void test_type_name() {
        assertEquals(
                JsonString.class.getSimpleName(),
                JsonString.from(randomString(WORD_LENGTH)).typeName()
        );
    }

    @Test
    void test_hash_code() {
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST, randStr -> assertEquals(
                randStr.hashCode(),
                JsonString.from(randStr).hashCode()
        ));
    }

    @Test
    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    void test_equals() {
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST,
                randStr -> {
                    var jS = JsonString.from(randStr);
                    assertEquals(jS, JsonString.from(randStr));
                    assertNotEquals(randStr, jS);
                    assertNotEquals(null, jS);
                });
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
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST, randStr -> {
            var optJStr = JsonString.from(randStr).tryGetAsString();
            assertTrue(optJStr.isPresent());
            assertEquals(randStr, optJStr.get());
        });
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