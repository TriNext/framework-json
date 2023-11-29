package de.trinext.framework.json;

import de.trinext.framework.json.JsonPathFinder.InvalidJsonPathTargetException;
import org.junit.jupiter.api.Test;
import test.util.RandomHelper;

import static de.trinext.framework.json.JsonPathFinder.THROW_INVALID_TARGET_EXCPT;
import static de.trinext.framework.json.JsonPathFinder.THROW_PATH_FORMAT_EXCPT;
import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestConstants.field;

/**
 * @author Dennis Woithe
 */
class JsonPathTest {

    @Test
    void testFind1() {
        var jCon = new JsonList()
                .addObj(o -> o.add("a", 10))
                .addObj(o -> o.add("a", 15))
                .addObj(o -> o.add("a", 20));
        var path = new JsonPathFinder(jCon, "*", THROW_PATH_FORMAT_EXCPT | THROW_INVALID_TARGET_EXCPT);
        var res = path.find();
        assertTrue(res.isPresent());
        assertEquals(jCon, res.get());
    }

    @Test
    void testFind2() {
        var randInts = RandomHelper.randomInts(3).toArray();
        var jCon = new JsonList()
                .addObj(o -> o.add(field(1), randInts[0]))
                .addObj(o -> o.add(field(1), randInts[1]))
                .addObj(o -> o.add(field(1), randInts[2]));
        var path = new JsonPathFinder(jCon, "*!" + field(1), THROW_PATH_FORMAT_EXCPT | THROW_INVALID_TARGET_EXCPT);
        var res = path.find();
        assertTrue(res.isPresent());
        assertEquals(new JsonList(randInts), res.get());
    }

    @Test
    void testFind3() {
        var randInts = RandomHelper.randomInts(3).toArray();
        var jCon = new JsonList()
                .addObj(o -> o.add(field(1), randInts[0]))
                .addObj(o -> o.add(field(1), randInts[1]))
                .addObj(o -> o.add(field(2), randInts[2]));
        var path = new JsonPathFinder(jCon, "*." + field(1), THROW_PATH_FORMAT_EXCPT | THROW_INVALID_TARGET_EXCPT);
        var res = path.find();
        assertTrue(res.isPresent());
        assertEquals(new JsonList(randInts[0], randInts[1]), res.get());
    }

    @Test
    void testFind4() {
        var randInts = RandomHelper.randomInts(3).toArray();
        var jCon = new JsonList()
                .addObj(o -> o.add(field(1), randInts[0]))
                .addObj(o -> o.add(field(1), randInts[1]))
                .addObj(o -> o.add(field(2), randInts[2]));
        var path = new JsonPathFinder(jCon, "*!" + field(1), THROW_PATH_FORMAT_EXCPT | THROW_INVALID_TARGET_EXCPT);
        assertThrows(InvalidJsonPathTargetException.class, path::find);
    }

}