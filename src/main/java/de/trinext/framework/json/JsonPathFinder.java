package de.trinext.framework.json;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import de.trinext.framework.json.JsonPathFinder.JsonPathIterator.JsonPathElem;

/**
 * @author Dennis Woithe
 */
class JsonPathFinder {

    private final JsonPathIterator pathIterator;

    private JsonElement<?> current;

    static final int NO_FLAGS = 0,
            THROW_PATH_FORMAT_EXCPT = 1,
            THROW_INVALID_TARGET_EXCPT = 2;

    private final int throwFlags;

    private final String fullJsonPath;

    // ==== CONSTRUCTORS ===================================================== //

    JsonPathFinder(JsonContainer<?> start, String jsonPath, int throwFlags) {
        this.throwFlags = throwFlags;
        current = Objects.requireNonNull(start);
        this.pathIterator = new JsonPathIterator(jsonPath);
        this.fullJsonPath = jsonPath;
    }


    // ==== METHODS ========================================================== //

    private boolean hasFlag(int flag) {
        return (throwFlags & flag) != 0;
    }

    class JsonPathIterator {

        String restOfPath;

        JsonPathElem currentPathElem, lastPathElem;

        JsonPathIterator(String jsonPath) {
            currentPathElem = null;
            lastPathElem = null;
            restOfPath = jsonPath;
        }

        boolean hasNext() {
            return !restOfPath.isEmpty();
        }

        JsonPathElem read() {
            lastPathElem = currentPathElem;
            var sb = new StringBuilder();
            var trimSep = false;
            for (var i = 0; i < restOfPath.length(); i++) {
                var c = restOfPath.charAt(i);
                if (c == JsonPathElem.ESCAPE_SYMBOL) {
                    i++;
                    if (i >= restOfPath.length())
                        throw new JsonPathFormatException("Invalid escape sequence.", restOfPath);
                    continue;
                }
                if (c == JsonPathElem.PATH_SEP) {
                    trimSep = true;
                    break;
                }
                if (i > 0 && c == JsonPathElem.STRICT_SEP)
                    break;
                sb.append(c);
            }
            restOfPath = restOfPath.substring(sb.length() + (trimSep ? 1 : 0));
            return (currentPathElem = new JsonPathElem(sb.toString()));
        }

        class JsonPathElem {

            private final String pathElem;

            JsonPathElem(String pathElem) {
                this.pathElem = pathElem;
                if (pathElem.isEmpty())
                    throw new JsonPathFormatException("Path element before \"" + restOfPath + "\" is empty.");
            }

            private static final char KLEENE_STAR = '*';

            private static final char STRICT_SEP = '!';

            private static final char PATH_SEP = '.';

            private static final char ESCAPE_SYMBOL = '\\';

            private static final Pattern INDEX_PATTERN = Pattern.compile("[0-9]+");

            boolean isStar() {
                return pathElem.length() == 1 && pathElem.charAt(0) == KLEENE_STAR;
            }

            boolean isStrict() {
                return pathElem.length() > 1 && pathElem.charAt(0) == STRICT_SEP;
            }

            boolean isIndex() {
                return INDEX_PATTERN.matcher(pathElem).matches();
            }

            String getAsKey() {
                return isStrict()
                       ? pathElem.substring(1)
                       : pathElem;
            }

            int getAsIndex() {
                return Integer.parseInt(pathElem);
            }

            @Override
            public String toString() {
                return pathElem;
            }

        }

    }

    Optional<JsonElement<?>> find() {
        while (pathIterator.hasNext()) {
            var nextPathElem = pathIterator.read();
            Optional<? extends JsonElement<?>> next;
            if (pathIterator.lastPathElem != null && pathIterator.lastPathElem.isStar()) {
                assert current instanceof JsonList;
                next = findNextAfterStar(nextPathElem, (JsonList) current);

            } else {
                next = switch (current) {
                    case JsonList jList -> findNext(nextPathElem, jList);
                    case JsonMap jMap -> findNext(nextPathElem, jMap);
                    default -> {
                        if (hasFlag(THROW_INVALID_TARGET_EXCPT))
                            throw new InvalidJsonPathTargetException(nextPathElem, current);
                        yield Optional.empty();
                    }
                };
            }
            if (next.isEmpty())
                return Optional.empty();
            current = next.get();
        }
        return Optional.of(current);
    }

    private Optional<JsonElement<?>> findNext(JsonPathElem pathElem, JsonList jList) {
        if (pathElem.isStar())
            return Optional.of(jList);
        if (pathElem.isIndex())
            return jList.tryGet(pathElem.getAsIndex());
        if (hasFlag(THROW_INVALID_TARGET_EXCPT))
            throw new InvalidJsonPathTargetException(pathElem, jList);
        return Optional.empty();
    }

    private Optional<JsonElement<?>> findNext(JsonPathElem nextPathElem, JsonMap jMap) {
        return jMap.tryGet(nextPathElem.getAsKey());
    }

    private Optional<JsonList> findNextAfterStar(JsonPathElem pathElem, JsonList listSearchedWithStar) {
        var res = new JsonList();
        for (var elem : listSearchedWithStar) {
            switch (elem) {
                case JsonList jListElem:
                    findNext(pathElem, jListElem).ifPresentOrElse(res::add, () -> {
                        if (pathElem.isStrict() && hasFlag(THROW_PATH_FORMAT_EXCPT))
                            throw new InvalidJsonPathTargetException(pathElem, listSearchedWithStar, jListElem);
                    });
                    break;
                case JsonMap jMapElem:
                    findNext(pathElem, jMapElem).ifPresentOrElse(res::add, () -> {
                        if (pathElem.isStrict() && hasFlag(THROW_PATH_FORMAT_EXCPT))
                            throw new InvalidJsonPathTargetException(pathElem, listSearchedWithStar, jMapElem);
                    });
                    break;
                default:
                    if (pathElem.isStrict()) {
                        if (hasFlag(THROW_PATH_FORMAT_EXCPT))
                            throw new InvalidJsonPathTargetException(pathElem, listSearchedWithStar);
                        return Optional.empty();
                    }
            }
        }
        return Optional.of(res);
    }


    /**
     * @author Dennis Woithe
     */
    @SuppressWarnings("serial")
    private class JsonPathFormatException extends RuntimeException {

        JsonPathFormatException(String message) {
            super(message + " Whole path: \"" + fullJsonPath + "\"");
        }

        JsonPathFormatException(String message, String pathElem) {
            super(message + "\n\"" + pathElem + "\" in \"" + fullJsonPath + "\"");
        }

    }

}