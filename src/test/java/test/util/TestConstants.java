package test.util;

import java.math.BigInteger;

/**
 * @author Erik Bannasch
 */
public final class TestConstants {
    public static final double DOUBLE_PI_CONSTANT = 3.141_59;
    public static final boolean BOOL_CONSTANT_TRUE = Boolean.TRUE, BOOL_CONSTANT_FALSE = Boolean.FALSE;

    public static final Number NUMBER_CONSTANT = BigInteger.ONE;

    public static final int ARRAY_TEST_VALUE_1 = -1,
            ARRAY_TEST_VALUE_2 = 0,
            ARRAY_TEST_VALUE_3 = 1,
            NRS_PER_TEST = 100,
            INT_CONSTANT = 1,
            WORDS_PER_TEST = 100,
            WORD_LENGTH = 10;

    public static final String //
            LIST_FIELD = "LIST_FIELD",
            STRING_FIELD = "STRING_FIELD",
            INT_FIELD = "INT_FIELD",
            ENUM_FIELD = "ENUM_FIELD",
            DOUBLE_FIELD = "DOUBLE_FIELD",
            NON_EXISTEND_FIELD = "NON_EXISTEND_FIELD",
            BIG_INT_FIELD = "NUMBER_FIELD",
            BIG_DEC_FIELD = "BIG_DEC_FIELD",
            BOOL_FIELD_TRUE = "BOOL_FIELD_TRUE",
            BOOL_FIELD_FALSE = "BOOL_FIELD_FALSE",
            OBJ_FIELD = "OBJ_FIELD",
            SET_FIELD = "SET_FIELD",
            NESTED_FIELD = "NESTED_FIELD",
            STRING_CONSTANT = "TRUE",
            A_CONSTANT = "a",
            B_CONSTANT = "b",
            FIELD_1 = "FIELD_1",
            FIELD_2 = "FIELD_2",
            FIELD_3 = "FIELD_3",
            FIELD_4 = "FIELD_4",
            EXISTING_PATH = "EXISTING_PATH",

            DATE_FIELD = "DATE_FIELD",
            DATE_CONSTANT = "01.03/1995",
            DATE_TIME_FIELD = "DATE_TIME_FIELD",
            TIME_FIELD = "TIME_FIELD",
            TIME_CONSTANT = "12:34:56",
            JSON_TREE_STRING_ARRAY_WITH_EMPTY_OBJECT = """
                                {
                                    "a": [{}]
                                }
                               """;

}
