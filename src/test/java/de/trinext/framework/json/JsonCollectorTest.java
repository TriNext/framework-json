package de.trinext.framework.json;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import test.util.RandomHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Dennis Woithe
 */
final class JsonCollectorTest {

    @Test
    void test_json_collector() {
        var randJInt = JsonInteger.from(RandomHelper.randomInt());
        var randJDec = JsonDecimal.from(RandomHelper.randomDouble());
        var randJStr = JsonString.from(RandomHelper.randomString(10));
        var randJBool = JsonBool.from(RandomHelper.randomBool());
        var randJNull = JsonNull.NULL;
        assertEquals(
                new JsonList(randJInt, randJDec, randJStr, randJBool, randJNull),
                Stream.of(randJInt, randJDec, randJStr, randJBool, randJNull).collect(Json.collector())
        );
    }

}