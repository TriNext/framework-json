package de.trinext.framework.json;

import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;
import util.UnexpectedGsonTypeException;

import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestHelper.testForRandomStrings;

/**
 * @author Dennis Woithe
 */
class JsonStringTest {

    private static final int WORDS_PER_TEST = 100;
    private static final int WORD_LENGTH = 10;

    // ==== METHODS ========================================================== //

    @Test
    void test_hash_code() {
        testForRandomStrings(WORD_LENGTH, WORDS_PER_TEST, randStr -> assertEquals(
                randStr.hashCode(),
                JsonString.from(randStr).hashCode()
        ));
    }

    @Test @SuppressWarnings({"SimplifiableAssertion", "EqualsBetweenInconvertibleTypes"})
    void test_equals() {
        testForRandomStrings(WORD_LENGTH, WORDS_PER_TEST,
                randStr -> {
                    var jS = JsonString.from(randStr);
                    assertTrue(jS.equals(JsonString.from(randStr)));
                    assertFalse(jS.equals(randStr));
                    assertFalse(jS.equals(null));
                });
    }

    @Test
    void test_to_string() {
        testForRandomStrings(WORD_LENGTH, WORDS_PER_TEST,
                randStr -> assertEquals(
                        "\"" + randStr + "\"",
                        JsonString.from(randStr).toString()
                ));
    }

    @Test
    void test_from_literal() {
        testForRandomStrings(WORD_LENGTH, WORDS_PER_TEST,
                randStr -> assertEquals(
                        randStr,
                        JsonString.from(randStr).getValue()
                ));
    }

    @Test
    void test_from_gson() {
        testForRandomStrings(WORD_LENGTH, WORDS_PER_TEST,
                randStr -> assertEquals(
                        JsonString.from(randStr),
                        JsonString.from(new com.google.gson.JsonPrimitive(randStr))
                ));
        assertThrows(
                UnexpectedGsonTypeException.class,
                () -> JsonString.from(new com.google.gson.JsonPrimitive(true))
        );
        assertThrows(
                UnexpectedGsonTypeException.class,
                () -> JsonString.from(new com.google.gson.JsonPrimitive(1))
        );
    }

    @Test
    void test_to_gson_elem() {
        testForRandomStrings(WORD_LENGTH, WORDS_PER_TEST,
                randStr -> assertEquals(
                        new JsonPrimitive(randStr),
                        JsonString.from(randStr).toGsonElem()
                ));
    }

    @Test
    void test_json_type_name() {
        assertEquals(JsonString.class.getSimpleName(), JsonString.from("").typeName());
    }


    @Test
    void test_try_getters() {
        testForRandomStrings(WORD_LENGTH, WORDS_PER_TEST, randStr -> assertEquals(
                randStr,
                JsonString.from(randStr).tryGetString().orElseThrow()
        ));
    }

    @Test
    void test_try_getters_empty() {
        var nonJNr = JsonInteger.from(0);
        assertTrue(nonJNr.tryGetString().isEmpty());
    }

}