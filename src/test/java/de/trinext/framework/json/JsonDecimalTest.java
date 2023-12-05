package de.trinext.framework.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestConstants.NRS_PER_TEST;
import static test.util.RandomHelper.*;

/**
 * @author Dennis Woithe
 */
class JsonDecimalTest {

    @Test
    void test_hash_code() {
        runForRandomBigDecs(NRS_PER_TEST, randBigDec -> assertEquals(
                randBigDec.hashCode(),
                JsonDecimal.from(randBigDec).hashCode()
        ));
    }

    @Test
    @SuppressWarnings({"SimplifiableAssertion", "EqualsBetweenInconvertibleTypes"})
    void test_equals() {
        runForRandomDoubles(NRS_PER_TEST, randDouble -> {
            var jD = JsonDecimal.from(randDouble);
            assertTrue(jD.equals(JsonDecimal.from(randDouble)));
            assertTrue(jD.equals(JsonDecimal.from(randDouble)));
            assertFalse(jD.equals(randDouble));
            assertFalse(jD.equals(null));
        });
    }

    @Test
    void test_to_string() {
        runForRandomBigDecs(NRS_PER_TEST, randBigDec -> assertEquals(
                randBigDec.toPlainString(),
                JsonDecimal.from(randBigDec).toString()
        ));
    }

    @Test
    void test_from_literal() {
        runForRandomBigDecs(NRS_PER_TEST, randBigDec -> {
            assertEquals(randBigDec, JsonDecimal.from(randBigDec).value);
            assertEquals(randBigDec.floatValue(), JsonDecimal.from(randBigDec.floatValue()).value.floatValue());
            assertEquals(randBigDec.doubleValue(), JsonDecimal.from(randBigDec.doubleValue()).value.doubleValue());
        });
    }

    @Test
    void test_json_type_name() {
        assertEquals(JsonDecimal.class.getSimpleName(), JsonDecimal.from(0.1).typeName());
    }

}