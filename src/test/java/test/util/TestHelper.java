package test.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.*;
import java.util.stream.*;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "InterfaceNeverImplemented"})
public interface TestHelper {


    // ==== STATIC FUNCTIONS ================================================= //

    /** Executes the test for the passed amount of random doubles. */
    static void testForRandomDoubles(long wordAmount, DoubleConsumer test) {
        randomDoubles(wordAmount).forEach(test);
    }

    /** Generates n random doubles */
    static DoubleStream randomDoubles(long amount) {
        return ThreadLocalRandom.current().doubles(amount);
    }

    /** Executes the test for the passed amount of random {@link Double}s. */
    static void testForRandomBoxedDoubles(long wordAmount, Consumer<Double> test) {
        randomDoubles(wordAmount).boxed().forEach(test);
    }

    /** Executes the test for the passed amount of random {@link BigInteger}s. */
    static void testForRandomBigInts(long wordAmount, Consumer<BigInteger> test) {
        randomInts(wordAmount).mapToObj(BigInteger::valueOf).forEach(test);
    }

    static IntStream randomInts(long amount) {
        return ThreadLocalRandom.current().ints(amount);
    }

    /** Executes the test for the passed amount of random ints. */
    static void testForRandomInts(long wordAmount, IntConsumer test) {
        randomInts(wordAmount).forEach(test);
    }

    /** Executes the test for the passed amount of random {@link Integer}s. */
    static void testForRandomBoxedInts(long wordAmount, Consumer<Integer> test) {
        randomInts(wordAmount).boxed().forEach(test);
    }

    /** Executes the test for the passed amount of random longs. */
    static void testForRandomLongs(long wordAmount, LongConsumer test) {
        randomLongs(wordAmount).forEach(test);
    }


    static LongStream randomLongs(long amount) {
        return ThreadLocalRandom.current().longs(amount);
    }

    /** Executes the test for the passed amount of random {@link Long}s. */
    static void testForRandomBoxedLongs(long wordAmount, Consumer<Long> test) {
        randomLongs(wordAmount).boxed().forEach(test);
    }

    /** Executes the test for the passed amount of random {@link BigDecimal}s. */
    static void testForRandomBigDecs(long wordAmount, Consumer<BigDecimal> test) {
        randomDoubles(wordAmount).mapToObj(BigDecimal::valueOf).forEach(test);
    }

    /**
     * Executes the test for the passed amount of random {@link String}s.
     *
     * @param maxWordLength The max length of each {@link String}
     */
    static void testForRandomStrings(int maxWordLength, long wordAmount, Consumer<String> test) {
        ThreadLocalRandom.current()
                .ints(wordAmount, 0, maxWordLength + 1)
                .mapToObj(byte[]::new)
                .peek(ThreadLocalRandom.current()::nextBytes)
                .map(String::new).forEach(test);
    }

}