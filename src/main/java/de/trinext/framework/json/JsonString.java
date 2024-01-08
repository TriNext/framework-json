package de.trinext.framework.json;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * The json representation of a string of characters.
 *
 * @author Dennis Woithe
 * @see String java equivalent
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class JsonString extends JsonPrimitive<String> {

    private JsonString(CharSequence value) {
        super(value.toString());
    }

    public static JsonString from(char c) {
        return new JsonString(String.valueOf(c));
    }

    public static JsonString from(CharSequence charSeq) {
        return new JsonString(charSeq);
    }

    public static JsonString from(Enum<?> enumConst) {
        return new JsonString(enumConst.name());
    }

    /**
     * Returns the unescaped string value without the surrounding quotes.
     */
    public String getAsString() {
        return value;
    }

    /**
     * Returns this string as a {@code char} if it's exactly one character long.
     *
     * @throws IllegalArgumentException if this string is not exactly one character long.
     * @see Json#CANT_CONVERT
     */
    public char getAsChar() {
        if (value.length() == 1)
            return value.charAt(0);
        throw Json.CANT_CONVERT.apply(this, char.class);
    }

    public <T extends Enum<T>> T getAsEnum(Class<T> cls) {
        return convert(s -> Enum.valueOf(cls, s), cls);
    }

    /**
     * Returns this string as a {@link UUID} if it's a valid UUID.
     *
     * @see Json#CANT_CONVERT
     */
    public UUID getAsUUID() {
        return convert(UUID::fromString, UUID.class);
    }

    /**
     * Returns this string as a {@link Pattern} if it's a valid regex.
     *
     * @see Json#CANT_CONVERT
     */
    public Pattern getAsPattern() {
        return convert(Pattern::compile, Pattern.class);
    }

    /**
     * Returns this as an escaped json string.
     *
     * @see <a href="https://www.json.org/img/string.png">The parser</a>
     */
    @Override
    @SuppressWarnings({"MagicCharacter", "MagicNumber", "NumericCastThatLosesPrecision"})
    public String toString() {
        return value.chars()
                .mapToObj(c -> switch ((char) c) {
                    case '"' -> "\\\"";
                    case '\\' -> "\\\\";
                    case '/' -> "\\/";
                    case '\b' -> "\\b";
                    case '\f' -> "\\f";
                    case '\n' -> "\\n";
                    case '\r' -> "\\r";
                    case '\t' -> "\\t";
                    // Char is printable ASCII
                    default -> (c >= 0x20 && c < 0x7F)
                               ? String.valueOf(c)
                               : "\\u" + String.format("%04x", c);
                }).reduce(
                        new StringBuilder("\""),
                        StringBuilder::append,
                        (a, b) -> a
                ).append('"')
                .toString();
    }


}