package de.trinext.framework.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static de.trinext.framework.util.RandomHelper.*;
import static test.util.TestConstants.*;

/**
 * @author Dennis Woithe
 * @see JsonNumber
 */
class JsonNumberTest {

    @Test @SuppressWarnings("OverlyLongLambda")
    void test_from_int_literal() {
        runForRandomBigInts(WORDS_PER_TEST, randBigInt -> {
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
        runForRandomBigDecs(WORDS_PER_TEST, randBigDec -> {
            assertEquals(
                    randBigDec.floatValue(),
                    JsonNumber.from(randBigDec.floatValue()).value.floatValue()
            );
            assertEquals(
                    randBigDec.doubleValue(),
                    JsonNumber.from(randBigDec.doubleValue()).value.doubleValue()
            );
            assertEquals(
                    randBigDec,
                    JsonNumber.from(randBigDec).value
            );
        });
    }

    @Test
    void test_from_number_literal() {
        runForRandomBigDecs(WORDS_PER_TEST, randBigDec -> {
            @SuppressWarnings("all")
            var anonNr = new Number() {

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
                    return randBigDec.toPlainString();
                }
            };
            assertEquals(
                    randBigDec,
                    JsonNumber.from(anonNr).value
            );
        });
    }

    @Test
    void test_try_getters_empty() {
        var nonJNr = JsonString.from(randomString(WORD_LENGTH));
        assertTrue(nonJNr.tryGetAsByte().isEmpty());
        assertTrue(nonJNr.tryGetAsShort().isEmpty());
        assertTrue(nonJNr.tryGetAsInt().isEmpty());
        assertTrue(nonJNr.tryGetAsLong().isEmpty());
        assertTrue(nonJNr.tryGetAsFloat().isEmpty());
        assertTrue(nonJNr.tryGetAsDouble().isEmpty());

        assertTrue(nonJNr.tryGetAsBigInt().isEmpty());
        assertTrue(nonJNr.tryGetAsBigDec().isEmpty());
        assertTrue(nonJNr.tryGetAsNumber().isEmpty());
    }

    @Test
    void test_try_get_path_as_number_empty() {
        var nonJNr = JsonString.from(randomString(WORD_LENGTH));
        var testMap = new JsonMap();
        testMap.add(field(1), nonJNr);
        assertTrue(testMap.tryGetPathAsByte(field(1)).isEmpty());
        assertTrue(testMap.tryGetPathAsShort(field(1)).isEmpty());
        assertTrue(testMap.tryGetPathAsInt(field(1)).isEmpty());
        assertTrue(testMap.tryGetPathAsLong(field(1)).isEmpty());
        assertTrue(testMap.tryGetPathAsFloat(field(1)).isEmpty());
        assertTrue(testMap.tryGetPathAsDouble(field(1)).isEmpty());
        assertTrue(testMap.tryGetPathAsBigInt(field(1)).isEmpty());
        assertTrue(testMap.tryGetPathAsBigDec(field(1)).isEmpty());
        assertTrue(testMap.tryGetPathAsNumber(field(1)).isEmpty());
    }

}