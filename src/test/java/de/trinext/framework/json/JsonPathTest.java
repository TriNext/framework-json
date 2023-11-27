package de.trinext.framework.json;

import de.trinext.framework.json.JsonPathFinder.InvalidJsonPathTargetException;
import org.junit.jupiter.api.Test;

import static de.trinext.framework.json.JsonPathFinder.THROW_INVALID_TARGET_EXCPT;
import static de.trinext.framework.json.JsonPathFinder.THROW_PATH_FORMAT_EXCPT;
import static org.junit.jupiter.api.Assertions.*;

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
        var jCon = new JsonList()
                .addObj(o -> o.add("a", 10))
                .addObj(o -> o.add("a", 15))
                .addObj(o -> o.add("a", 20));
        var path = new JsonPathFinder(jCon, "*!a", THROW_PATH_FORMAT_EXCPT | THROW_INVALID_TARGET_EXCPT);
        var res = path.find();
        assertTrue(res.isPresent());
        assertEquals(new JsonList(10, 15, 20), res.get());
    }

    @Test
    void testFind3() {
        var jCon = new JsonList()
                .addObj(o -> o.add("a", 10))
                .addObj(o -> o.add("a", 15))
                .addObj(o -> o.add("b", 20));
        var path = new JsonPathFinder(jCon, "*.a", THROW_PATH_FORMAT_EXCPT | THROW_INVALID_TARGET_EXCPT);
        var res = path.find();
        assertTrue(res.isPresent());
        assertEquals(new JsonList(10, 15), res.get());
    }

    @Test
    void testFind4() {
        var jCon = new JsonList()
                .addObj(o -> o.add("a", 10))
                .addObj(o -> o.add("a", 15))
                .addObj(o -> o.add("b", 20));
        var path = new JsonPathFinder(jCon, "*!a", THROW_PATH_FORMAT_EXCPT | THROW_INVALID_TARGET_EXCPT);
        assertThrows(InvalidJsonPathTargetException.class, path::find);
    }

}