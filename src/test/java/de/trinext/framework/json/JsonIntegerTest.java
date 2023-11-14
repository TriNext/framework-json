package de.trinext.framework.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestHelper.testForRandomBigInts;

/**
 * @author Dennis Woithe
 */
class JsonIntegerTest {

    private static final int NRS_PER_TEST = 100;


    // ==== METHODS ========================================================== //

    @Test
    void test_hash_code() {
        testForRandomBigInts(NRS_PER_TEST, randBigInt -> assertEquals(
                randBigInt.hashCode(),
                JsonInteger.from(randBigInt).hashCode()
        ));
    }

    @Test @SuppressWarnings({"SimplifiableAssertion", "EqualsBetweenInconvertibleTypes"})
    void test_equals() {
        testForRandomBigInts(NRS_PER_TEST, randBigInt -> {
            var jI = JsonInteger.from(randBigInt);
            assertTrue(jI.equals(JsonInteger.from(randBigInt)));
            assertFalse(jI.equals(randBigInt));
            assertFalse(jI.equals(null));
        });
    }

    @Test
    void test_to_string() {
        testForRandomBigInts(NRS_PER_TEST, randBigInt -> assertEquals(
                String.valueOf(randBigInt),
                JsonInteger.from(randBigInt).toString()
        ));
    }

    @Test @SuppressWarnings("OverlyLongLambda")
    void test_from_literal() {
        testForRandomBigInts(NRS_PER_TEST, randBigInt -> {
            var randByte = randBigInt.byteValue();
            assertEquals(randByte, JsonInteger.from(randByte).getValue().byteValue());
            var randShort = randBigInt.shortValue();
            assertEquals(randShort, JsonInteger.from(randShort).getValue().shortValue());
            var randInt = randBigInt.intValue();
            assertEquals(randInt, JsonInteger.from(randInt).getValue().intValue());
            var randLong = randBigInt.longValue();
            assertEquals(randLong, JsonInteger.from(randLong).getValue().longValue());
        });
    }

    @Test
    void test_json_type_name() {
        assertEquals(JsonInteger.class.getSimpleName(), JsonInteger.from(0).typeName());
    }

}