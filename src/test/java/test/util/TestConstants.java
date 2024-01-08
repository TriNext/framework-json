package test.util;

/**
 * @author Erik Bannasch
 */
public final class TestConstants {

    public static final int //
            ELEMS_PER_LIST = 5,
            WORDS_PER_TEST = 100,
            WORD_LENGTH = 10;

    private static final String FIELD_CONSTANT = "field-";

    public static String field(int i) {
        return FIELD_CONSTANT + i;
    }

}