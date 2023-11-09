package de.trinext.framework.json;

import java.math.BigDecimal;

import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;
import util.UnexpectedGsonTypeException;

import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestHelper.*;

/**
 * @author Dennis Woithe
 */
class JsonDecimalTest {

    private static final int NRS_PER_TEST = 50;

    // ==== METHODS ========================================================== //

    @Test
    void test_hash_code() {
        testForRandomBigDecs(NRS_PER_TEST, randBigDec -> assertEquals(
                randBigDec.hashCode(),
                JsonDecimal.from(randBigDec).hashCode()
        ));
    }

    @Test
    @SuppressWarnings({"SimplifiableAssertion", "EqualsBetweenInconvertibleTypes"})
    void test_equals() {
        testForRandomDoubles(NRS_PER_TEST, randDouble -> {
            var jD = JsonDecimal.from(randDouble);
            assertTrue(jD.equals(JsonDecimal.from(randDouble)));
            assertTrue(jD.equals(JsonDecimal.from(randDouble)));
            assertFalse(jD.equals(randDouble));
            assertFalse(jD.equals(null));
        });
    }

    @Test
    void test_to_string() {
        testForRandomBigDecs(NRS_PER_TEST, randBigDec -> assertEquals(
                randBigDec.toPlainString(),
                JsonDecimal.from(randBigDec).toString()
        ));
    }

    @Test
    void test_from_literal() {
        testForRandomBigDecs(NRS_PER_TEST, randBigDec -> {
            assertEquals(randBigDec, JsonDecimal.from(randBigDec).getValue());
            assertEquals(randBigDec.floatValue(), JsonDecimal.from(randBigDec.floatValue()).getValue().floatValue());
            assertEquals(randBigDec.doubleValue(), JsonDecimal.from(randBigDec.doubleValue()).getValue().doubleValue());
        });
    }

    @Test
    void test_from_gson() {
        testForRandomBigInts(NRS_PER_TEST, randBigInt -> assertEquals(
                JsonDecimal.from(new BigDecimal(randBigInt)),
                JsonDecimal.from(new JsonPrimitive(randBigInt))
        ));
        testForRandomBigDecs(NRS_PER_TEST, randBigDec -> assertEquals(
                JsonDecimal.from(randBigDec),
                JsonDecimal.from(new JsonPrimitive(randBigDec))
        ));
        assertThrows(
                UnexpectedGsonTypeException.class,
                () -> JsonDecimal.from(new com.google.gson.JsonPrimitive(""))
        );
        assertThrows(
                UnexpectedGsonTypeException.class,
                () -> JsonDecimal.from(new com.google.gson.JsonPrimitive(false))
        );
    }

    @Test
    void test_to_gson_elem() {
        testForRandomBigInts(NRS_PER_TEST, randBigInt -> assertEquals(
                new JsonPrimitive(randBigInt),
                JsonDecimal.from(randBigInt).toGsonElem()
        ));
        testForRandomBigDecs(NRS_PER_TEST, randBigDec -> assertEquals(
                new JsonPrimitive(randBigDec),
                JsonDecimal.from(randBigDec).toGsonElem()
        ));
    }

}