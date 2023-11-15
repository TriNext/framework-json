package de.trinext.framework.json;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Dennis Woithe
 */
class JsonTest {

    @Test
    void test_constructor() throws NoSuchMethodException {
        var constructor = Json.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        assertEquals(0, constructor.getParameterCount());
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
            fail("Should throw AssertionError");
        } catch (InvocationTargetException ite) {
          assertInstanceOf(AssertionError.class, ite.getCause());
        } catch (IllegalAccessException | InstantiationException e) {
            fail("Shouldn't throw other exceptions but threw: " + e.getMessage());
        }
    }

    @Test
    void test_tree_from_string() {
        Json.treeFromString("""
                                                 {
                                                    "a": [
                                                    {}
                                                    ]
                                                 }
                                                 """);
    }


}