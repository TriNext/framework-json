package de.trinext.framework.json;

import java.time.format.DateTimeFormatter;
import java.util.*;

import org.junit.jupiter.api.Test;

import static de.trinext.framework.util.RandomHelper.randomWord;
import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestConstants.WORD_LENGTH;

/**
 * @author Dennis Woithe
 * @see JsonBool
 */
class JsonBoolTest {

    @Test
    void test_from() {
        assertEquals(JsonBool.TRUE, JsonBool.from(true));
        assertEquals(JsonBool.FALSE, JsonBool.from(false));
    }

    @Test
    void test_get_as_bool() {
        assertEquals(true, JsonBool.TRUE.getAsBool());
        assertEquals(false, JsonBool.FALSE.getAsBool());
    }

    @Test
    @SuppressWarnings("DuplicateStringLiteralInspection")
    void test_to_string() {
        assertEquals("true", JsonBool.TRUE.toString());
        assertEquals("false", JsonBool.FALSE.toString());
    }

    @Test
    void test_type_name() {
        assertEquals(JsonBool.class.getSimpleName(), JsonBool.TRUE.typeName());
        assertEquals(JsonBool.class.getSimpleName(), JsonBool.FALSE.typeName());
    }

    @Test
    void test_hash_code() {
        assertEquals(Boolean.TRUE.hashCode(), JsonBool.TRUE.hashCode());
        assertEquals(Boolean.FALSE.hashCode(), JsonBool.FALSE.hashCode());
    }

    @Test
    void test_equals() {
        assertEquals(JsonBool.TRUE, JsonBool.TRUE);
        assertEquals(JsonBool.FALSE, JsonBool.FALSE);
        assertNotEquals(JsonBool.TRUE, JsonBool.FALSE);
        assertNotEquals(JsonBool.FALSE, JsonBool.TRUE);
        assertNotEquals(JsonBool.TRUE, null);
        assertNotEquals(JsonBool.FALSE, null);
        assertNotEquals(JsonBool.TRUE, new Object());
        assertNotEquals(JsonBool.FALSE, new Object());
    }

    @Test
    void test_find_path() {
        assertFalse(JsonBool.TRUE.findPath(""));
        assertFalse(JsonBool.FALSE.findPath(""));
        assertFalse(JsonBool.TRUE.findPath(randomWord(WORD_LENGTH)));
        assertFalse(JsonBool.FALSE.findPath(randomWord(WORD_LENGTH)));
    }

    @Test
    void test_remove_path() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_byte() {
        assertEquals(Optional.empty(), JsonBool.TRUE.tryGetAsByte());
        assertEquals(Optional.empty(), JsonBool.FALSE.tryGetAsByte());
    }

    @Test
    void test_try_get_as_short() {
        assertEquals(Optional.empty(), JsonBool.TRUE.tryGetAsShort());
        assertEquals(Optional.empty(), JsonBool.FALSE.tryGetAsShort());
    }

    @Test
    void test_try_get_as_int() {
        assertEquals(OptionalInt.empty(), JsonBool.TRUE.tryGetAsInt());
        assertEquals(OptionalInt.empty(), JsonBool.FALSE.tryGetAsInt());
    }

    @Test
    void test_try_get_as_long() {
        assertEquals(OptionalLong.empty(), JsonBool.TRUE.tryGetAsLong());
        assertEquals(OptionalLong.empty(), JsonBool.FALSE.tryGetAsLong());
    }

    @Test
    void test_try_get_as_float() {
        assertEquals(Optional.empty(), JsonBool.TRUE.tryGetAsFloat());
        assertEquals(Optional.empty(), JsonBool.FALSE.tryGetAsFloat());
    }

    @Test
    void test_try_get_as_double() {
        assertEquals(OptionalDouble.empty(), JsonBool.TRUE.tryGetAsDouble());
        assertEquals(OptionalDouble.empty(), JsonBool.FALSE.tryGetAsDouble());
    }

    @Test
    void test_try_get_as_bool() {
        assertEquals(Optional.of(true), JsonBool.TRUE.tryGetAsBool());
        assertEquals(Optional.of(false), JsonBool.FALSE.tryGetAsBool());
    }

    @Test
    void test_try_get_as_char() {
        assertEquals(Optional.empty(), JsonBool.TRUE.tryGetAsChar());
        assertEquals(Optional.empty(), JsonBool.FALSE.tryGetAsChar());
    }

    @Test
    void test_try_get_as_number() {
        assertEquals(Optional.empty(), JsonBool.TRUE.tryGetAsNumber());
        assertEquals(Optional.empty(), JsonBool.FALSE.tryGetAsNumber());
    }

    @Test
    void test_try_get_as_string() {
        assertEquals(Optional.empty(), JsonBool.TRUE.tryGetAsString());
        assertEquals(Optional.empty(), JsonBool.FALSE.tryGetAsString());
    }

    @Test
    void test_try_get_as_big_int() {
        assertEquals(Optional.empty(), JsonBool.TRUE.tryGetAsBigInt());
        assertEquals(Optional.empty(), JsonBool.FALSE.tryGetAsBigInt());
    }

    @Test
    void test_try_get_as_big_dec() {
        assertEquals(Optional.empty(), JsonBool.TRUE.tryGetAsBigDec());
        assertEquals(Optional.empty(), JsonBool.FALSE.tryGetAsBigDec());
    }

    @Test
    void test_try_get_as_date() {
        assertEquals(Optional.empty(), JsonBool.TRUE.tryGetAsDate(DateTimeFormatter.ISO_DATE));
        assertEquals(Optional.empty(), JsonBool.FALSE.tryGetAsDate(DateTimeFormatter.ISO_DATE));
    }

    @Test
    void test_try_get_as_time() {
        assertEquals(Optional.empty(), JsonBool.TRUE.tryGetAsTime(DateTimeFormatter.ISO_TIME));
        assertEquals(Optional.empty(), JsonBool.FALSE.tryGetAsTime(DateTimeFormatter.ISO_TIME));
    }

    @Test
    void test_try_get_as_date_time() {
        assertEquals(Optional.empty(), JsonBool.TRUE.tryGetAsDateTime(DateTimeFormatter.ISO_DATE_TIME));
        assertEquals(Optional.empty(), JsonBool.FALSE.tryGetAsDateTime(DateTimeFormatter.ISO_DATE_TIME));
    }

    @Test
    void test_try_get_as_enum() {
        enum TestEnum {}
        assertEquals(Optional.empty(), JsonBool.TRUE.tryGetAsEnum(TestEnum.class));
        assertEquals(Optional.empty(), JsonBool.FALSE.tryGetAsEnum(TestEnum.class));
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