package de.trinext.framework.json;

import java.math.BigDecimal;

import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static test.util.TestHelper.*;

/**
 * @author Dennis Woithe
 */
class JsonNumberTest {

    private static final int NRS_PER_TEST = 100;

    // ==== METHODS ========================================================== //

    @Test @SuppressWarnings("OverlyLongLambda")
    void test_from_int_literal() {
        testForRandomBigInts(NRS_PER_TEST, randBigInt -> {
            var randByte = randBigInt.byteValue();
            assertEquals(
                    JsonInteger.from(randByte),
                    JsonNumber.from(randByte)
            );
            var randShort = randBigInt.shortValue();
            assertEquals(
                    JsonInteger.from(randShort),
                    JsonNumber.from(randShort)
            );
            var randInt = randBigInt.intValue();
            assertEquals(
                    JsonInteger.from(randInt),
                    JsonNumber.from(randInt)
            );
            var randLong = randBigInt.longValue();
            assertEquals(
                    JsonInteger.from(randLong),
                    JsonNumber.from(randLong)
            );
            assertEquals(
                    JsonInteger.from(randBigInt),
                    JsonNumber.from(randBigInt)
            );
        });
    }

    @Test
    void test_from_dec_literal() {
        testForRandomBigDecs(NRS_PER_TEST, randBigDec -> {
            assertEquals(
                    randBigDec.floatValue(),
                    JsonNumber.from(randBigDec.floatValue()).getValue().floatValue()
            );
            assertEquals(
                    randBigDec.doubleValue(),
                    JsonNumber.from(randBigDec.doubleValue()).getValue().doubleValue()
            );
            assertEquals(
                    randBigDec,
                    JsonNumber.from(randBigDec).getValue()
            );
        });
    }

    @Test
    void test_from_number_literal() {
        testForRandomBigDecs(NRS_PER_TEST, randBigDec -> {
            @SuppressWarnings("all")
            var anonNr = new Number() {

                // ==== METHODS ========================================================== //

                @Override
                public int intValue() {
                    return randBigDec.intValue();
                }

                @Override
                public long longValue() {
                    return randBigDec.longValue();
                }

                @Override
                public float floatValue() {
                    return randBigDec.floatValue();
                }

                @Override
                public double doubleValue() {
                    return randBigDec.doubleValue();
                }

                @Override
                public String toString() {
                    return randBigDec.toString();
                }
            };
            assertEquals(
                    randBigDec,
                    JsonNumber.from(anonNr).getValue()
            );
        });
    }

    @Test
    void test_try_getters_empty() {
        var nonJNr = JsonString.from("not a number");
        var hugeDecimal = BigDecimal.valueOf(Double.MAX_VALUE).multiply(BigDecimal.TEN);

        assertTrue(nonJNr.tryGetInt().isEmpty());
        assertTrue(JsonInteger.from(hugeDecimal.toBigInteger()).tryGetInt().isEmpty());

        assertTrue(nonJNr.tryGetLong().isEmpty());
        assertTrue(JsonInteger.from(hugeDecimal.toBigInteger()).tryGetLong().isEmpty());

        assertTrue(nonJNr.tryGetDouble().isEmpty());
        assertEquals(Double.POSITIVE_INFINITY, JsonDecimal.from(hugeDecimal).tryGetDouble().orElseThrow());


        assertTrue(nonJNr.tryGetBigInt().isEmpty());
        assertTrue(nonJNr.tryGetBigDec().isEmpty());
        assertTrue(nonJNr.tryGetNumber().isEmpty());
    }

}