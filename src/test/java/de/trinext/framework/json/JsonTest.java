package de.trinext.framework.json;

import java.util.stream.Stream;

import de.trinext.framework.util.RandomHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.util.TestConstants.WORD_LENGTH;

/**
 * @author Dennis Woithe
 * @see Json
 */
class JsonTest {

    @Test
    void test_tree_from_string() {
        // TODO: Implement me!
    }

    @Test
    void test_tree_from_instance() {
        // TODO: Implement me!
    }

    @Test
    void test_instance_from_string() {
        // TODO: Implement me!
    }

    @Test
    void test_instance_from_tree() {
        // TODO: Implement me!
    }

    @Test
    void test_tree_body_handler() {
        // TODO: Implement me!
    }

    @Test
    void test_instance_body_handler() {
        // TODO: Implement me!
    }

    @Test
    void test_tree_body_handler_with_charset() {
        // TODO: Implement me!
    }

    @Test
    void test_instance_body_handler_with_charset() {
        // TODO: Implement me!
    }

    @Test
    void test_collector() {
        var randInt = RandomHelper.randomInt();
        var randDec = RandomHelper.randomDouble();
        var randStr = RandomHelper.randomString(WORD_LENGTH);
        var randBool = RandomHelper.randomBool();
        assertEquals(
                JsonList.from(randInt, randDec, randStr, randBool, JsonNull.NULL),
                Stream.of(randInt, randDec, randStr, randBool, JsonNull.NULL)
                        .map(Json::treeFromInstance)
                        .collect(Json.collector())
        );

    }

}