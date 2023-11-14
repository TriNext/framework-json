package util;

import java.math.BigDecimal;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess", "InterfaceNeverImplemented"})
public interface BigDecimalHelper {

    // METHODS ========================================================>>

    /** Returns true if a {@link BigDecimal} has no decimal places. */
    static boolean isInteger(BigDecimal bd) {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }

}