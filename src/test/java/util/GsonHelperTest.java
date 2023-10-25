package util;

import java.util.*;

import com.google.gson.*;
import org.junit.jupiter.api.Test;

import static de.trinext.framework.json.GsonPrimitiveTypeName.*;
import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestHelper.randomInts;
import static util.GsonHelper.*;

/**
 * @author Dennis Woithe
 */
class GsonHelperTest {

    // ==== METHODS ========================================================== //

    private static final int ELEMS_PER_TEST = 100;

    @Test
    void test_gson_type_name() {
        assertEquals(JsonObject.class.getSimpleName(), gsonTypeName(new JsonObject()));
        assertEquals(JsonArray.class.getSimpleName(), gsonTypeName(new JsonArray()));
        assertEquals(JsonNull.class.getSimpleName(), gsonTypeName(JsonNull.INSTANCE));
        assertEquals(JSON_INTEGER_TYPE, gsonTypeName(new JsonPrimitive(1)));
        assertEquals(JSON_DECIMAL_TYPE, gsonTypeName(new JsonPrimitive(0.5)));
        assertEquals(JSON_STRING_TYPE, gsonTypeName(new JsonPrimitive("")));
        assertEquals(JSON_BOOLEAN_TYPE, gsonTypeName(new JsonPrimitive(true)));
    }

    @Test
    void test_gson_nr_is_int() {
        assertTrue(gsonNrIsInt(new JsonPrimitive(1)));
        assertFalse(gsonNrIsInt(new JsonPrimitive(0.5)));
        assertThrows(
                UnexpectedGsonTypeException.class,
                () -> gsonNrIsInt(new JsonPrimitive(""))
        );
    }

    @Test
    void test_gson_nr_is_dec() {
        assertTrue(gsonNrIsDec(new JsonPrimitive(0.5)));
        assertFalse(gsonNrIsDec(new JsonPrimitive(1)));
        assertThrows(
                UnexpectedGsonTypeException.class,
                () -> gsonNrIsInt(new JsonPrimitive(""))
        );
    }

    @Test
    void test_array_to_stream() {
        var elems = randomInts(ELEMS_PER_TEST).boxed().toArray();
        var jArr = new JsonArray();
        Arrays.stream(elems).forEach(e -> jArr.add((Number) e));
        assertArrayEquals(elems, arrayToStream(jArr).map(JsonElement::getAsInt).toArray());
    }


}