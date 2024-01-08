package de.trinext.framework.json.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;

import de.trinext.framework.json.Json;
import org.junit.jupiter.api.Test;

import static de.trinext.framework.util.RandomHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.util.TestConstants.WORDS_PER_TEST;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"DuplicateStringLiteralInspection"}) //
final class JsonStringToInstanceTest {

    @Test
    void test_boolean_conversion() {
        assertEquals(true, Json.instanceFromString("true", boolean.class));
        assertEquals(Boolean.TRUE, Json.instanceFromString("true", Boolean.class));

        assertEquals(false, Json.instanceFromString("false", boolean.class));
        assertEquals(Boolean.FALSE, Json.instanceFromString("false", Boolean.class));
    }

    /** Generates a decimal point followed by 1-10 zeroes. */
    static String randDecZeroes() {
        return "." + "0".repeat(randomInt(1, 10));
    }

    @Test
    void test_byte_conversion() {
        runForRandomBytes(WORDS_PER_TEST, randByte -> {
            assertEquals(randByte, Json.instanceFromString(String.valueOf(randByte), byte.class));
            assertEquals(randByte, Json.instanceFromString(String.valueOf(randByte), Byte.class));

            // Test with decimal zeroes
            assertEquals(randByte, Json.instanceFromString(randByte + randDecZeroes(), byte.class));
            assertEquals(randByte, Json.instanceFromString(randByte + randDecZeroes(), Byte.class));
        });

        runForRandomBigDecs(WORDS_PER_TEST, randBigDec -> {
            assertEquals(randBigDec.byteValue(), Json.instanceFromString(randBigDec.toPlainString(), byte.class));
            assertEquals(randBigDec.byteValue(), Json.instanceFromString(randBigDec.toPlainString(), Byte.class));
        });

        // Overflow
        var biggerThanByteMax = String.valueOf(Byte.MAX_VALUE + 1L);
        assertEquals(Byte.MIN_VALUE, Json.instanceFromString(biggerThanByteMax, byte.class));
        assertEquals(Byte.MIN_VALUE, Json.instanceFromString(biggerThanByteMax, Byte.class));

        // Underflow
        var smallerThanByteMin = String.valueOf(Byte.MIN_VALUE - 1L);
        assertEquals(Byte.MAX_VALUE, Json.instanceFromString(smallerThanByteMin, byte.class));
        assertEquals(Byte.MAX_VALUE, Json.instanceFromString(smallerThanByteMin, Byte.class));
    }

    @Test
    void test_short_conversion() {
        runForRandomShorts(WORDS_PER_TEST, randShort -> {
            assertEquals(randShort, Json.instanceFromString(String.valueOf(randShort), short.class));
            assertEquals(randShort, Json.instanceFromString(String.valueOf(randShort), Short.class));

            // Test with decimal zeroes
            assertEquals(randShort, Json.instanceFromString(randShort + randDecZeroes(), short.class));
            assertEquals(randShort, Json.instanceFromString(randShort + randDecZeroes(), Short.class));
        });

        runForRandomBigDecs(WORDS_PER_TEST, randBigDec -> {
            assertEquals(randBigDec.shortValue(), Json.instanceFromString(randBigDec.toPlainString(), short.class));
            assertEquals(randBigDec.shortValue(), Json.instanceFromString(randBigDec.toPlainString(), Short.class));
        });

        // Overflow
        var biggerThanShortMax = String.valueOf(Short.MAX_VALUE + 1L);
        assertEquals(Short.MIN_VALUE, Json.instanceFromString(biggerThanShortMax, short.class));
        assertEquals(Short.MIN_VALUE, Json.instanceFromString(biggerThanShortMax, Short.class));

        // Underflow
        var smallerThanShortMin = String.valueOf(Short.MIN_VALUE - 1L);
        assertEquals(Short.MAX_VALUE, Json.instanceFromString(smallerThanShortMin, short.class));
        assertEquals(Short.MAX_VALUE, Json.instanceFromString(smallerThanShortMin, Short.class));
    }

    @Test
    void test_int_conversion() {
        runForRandomInts(WORDS_PER_TEST, randInt -> {
            assertEquals(randInt, Json.instanceFromString(String.valueOf(randInt), int.class));
            assertEquals(randInt, Json.instanceFromString(String.valueOf(randInt), Integer.class));

            // Test with decimal zeroes
            assertEquals(randInt, Json.instanceFromString(randInt + randDecZeroes(), int.class));
            assertEquals(randInt, Json.instanceFromString(randInt + randDecZeroes(), Integer.class));
        });

        runForRandomBigDecs(WORDS_PER_TEST, randBigDec -> {
            assertEquals(randBigDec.intValue(), Json.instanceFromString(randBigDec.toPlainString(), int.class));
            assertEquals(randBigDec.intValue(), Json.instanceFromString(randBigDec.toPlainString(), Integer.class));
        });

        // Overflow
        var biggerThanIntMax = String.valueOf(Integer.MAX_VALUE + 1L);
        assertEquals(Integer.MIN_VALUE, Json.instanceFromString(biggerThanIntMax, int.class));
        assertEquals(Integer.MIN_VALUE, Json.instanceFromString(biggerThanIntMax, Integer.class));

        // Underflow
        var smallerThanIntMin = String.valueOf(Integer.MIN_VALUE - 1L);
        assertEquals(Integer.MAX_VALUE, Json.instanceFromString(smallerThanIntMin, int.class));
        assertEquals(Integer.MAX_VALUE, Json.instanceFromString(smallerThanIntMin, Integer.class));
    }

    @Test
    void test_long_conversion() {
        runForRandomLongs(WORDS_PER_TEST, randLong1 -> {
            assertEquals(randLong1, Json.instanceFromString(String.valueOf(randLong1), long.class));
            assertEquals(randLong1, Json.instanceFromString(String.valueOf(randLong1), Long.class));
        });

        runForRandomBigInts(WORDS_PER_TEST, randBigInt -> {
            assertEquals(randBigInt.longValue(), Json.instanceFromString(randBigInt.toString(10), long.class));
            assertEquals(randBigInt.longValue(), Json.instanceFromString(randBigInt.toString(10), Long.class));
        });

        // Overflow
        var biggerThanLongMax = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE);
        assertEquals(biggerThanLongMax.longValue(), Json.instanceFromString(biggerThanLongMax.toString(10), long.class));
        assertEquals(biggerThanLongMax.longValue(), Json.instanceFromString(biggerThanLongMax.toString(10), Long.class));

        // Underflow
        var smallerThanLongMin = BigInteger.valueOf(Long.MIN_VALUE).subtract(BigInteger.ONE);
        assertEquals(smallerThanLongMin.longValue(), Json.instanceFromString(smallerThanLongMin.toString(10), long.class));
        assertEquals(smallerThanLongMin.longValue(), Json.instanceFromString(smallerThanLongMin.toString(10), Long.class));
    }

    @Test
    void test_float_conversion() {
        runForRandomFloats(WORDS_PER_TEST, randFloat -> {
            assertEquals(randFloat, Json.instanceFromString(String.valueOf(randFloat), float.class));
            assertEquals(randFloat, Json.instanceFromString(String.valueOf(randFloat), Float.class));
        });

        // Overflow
        var biggerThanFloatMax = randomBigDecBiggerThan(new BigDecimal(Float.MAX_VALUE)).add(BigDecimal.ONE);
        assertEquals(biggerThanFloatMax.floatValue(), Json.instanceFromString(biggerThanFloatMax.toPlainString(), float.class));
        assertEquals(biggerThanFloatMax.floatValue(), Json.instanceFromString(biggerThanFloatMax.toPlainString(), Float.class));

        // Underflow
        var smallerThanFloatMin = randomBigDecSmallerThan(new BigDecimal(Float.MIN_VALUE)).subtract(BigDecimal.ONE);
        assertEquals(smallerThanFloatMin.floatValue(), Json.instanceFromString(smallerThanFloatMin.toPlainString(), float.class));
        assertEquals(smallerThanFloatMin.floatValue(), Json.instanceFromString(smallerThanFloatMin.toPlainString(), Float.class));
    }

    @Test
    void test_double_conversion() {
        var randDouble = randomDouble();
        assertEquals(randDouble, Json.instanceFromString(String.valueOf(randDouble), double.class));
        assertEquals(randDouble, Json.instanceFromString(String.valueOf(randDouble), Double.class));

        // Overflow
        var biggerThanDoubleMax = randomBigDecBiggerThan(new BigDecimal(Double.MAX_VALUE)).add(BigDecimal.ONE);
        assertEquals(biggerThanDoubleMax.doubleValue(), Json.instanceFromString(biggerThanDoubleMax.toPlainString(), double.class));
        assertEquals(biggerThanDoubleMax.doubleValue(), Json.instanceFromString(biggerThanDoubleMax.toPlainString(), Double.class));

        // Underflow
        var smallerThanDoubleMin = randomBigDecSmallerThan(new BigDecimal(Double.MIN_VALUE)).subtract(BigDecimal.ONE);
        assertEquals(smallerThanDoubleMin.doubleValue(), Json.instanceFromString(smallerThanDoubleMin.toPlainString(), double.class));
        assertEquals(smallerThanDoubleMin.doubleValue(), Json.instanceFromString(smallerThanDoubleMin.toPlainString(), Double.class));
    }

    @Test
    void test_big_integer_conversion() {
        runForRandomBigInts(WORDS_PER_TEST, randBigInt -> assertEquals(randBigInt, Json.instanceFromString(randBigInt.toString(10), BigInteger.class)));

        runForRandomBigDecs(WORDS_PER_TEST, randBigDec -> assertEquals(randBigDec.toBigInteger(), Json.instanceFromString(randBigDec.toPlainString(), BigInteger.class)));
    }

    @Test
    void test_big_decimal_conversion() {
        runForRandomBigDecs(WORDS_PER_TEST, randBigDec -> assertEquals(randBigDec, Json.instanceFromString(randBigDec.toPlainString(), BigDecimal.class)));
    }

    @Test @SuppressWarnings("CallToNumericToString")
    void test_number_conversion() {
        runForRandomBigDecs(WORDS_PER_TEST, randBigDec -> {
            var number = Json.instanceFromString(randBigDec.toPlainString(), Number.class);
            assertEquals(randBigDec.toString(), number.toString());
        });
    }

}