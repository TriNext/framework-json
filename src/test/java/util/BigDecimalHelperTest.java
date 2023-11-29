package util;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Dennis Woithe
 */
class BigDecimalHelperTest {


    @Test
    void test_is_integer() {
        assertTrue(BigDecimalHelper.isInteger(new BigDecimal("5")));
        assertFalse(BigDecimalHelper.isInteger(new BigDecimal("5.44")));
    }

}