package util;

import java.math.BigDecimal;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "InterfaceNeverImplemented"})
public interface BigDecimalHelper {

    /** Returns true if a {@link BigDecimal} has no decimal places. */
    static boolean isInteger(BigDecimal bd) {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }

}