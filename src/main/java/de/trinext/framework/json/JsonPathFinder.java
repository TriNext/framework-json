package de.trinext.framework.json;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Dennis Woithe
 */
class JsonPathFinder {

    private static final Pattern PATH_SEPARATOR = Pattern.compile("(?<!\\\\)\\.");
    private final List<String> pathElems;
    private final JsonElement<?>[] discovered;
    private JsonElement<?> current;

    // ==== CONSTRUCTORS ===================================================== //

    @SuppressWarnings("TypeMayBeWeakened")
    JsonPathFinder(JsonContainer<?> start, CharSequence jsonPath) {
        pathElems = new ArrayList<>(jsonPath.isEmpty() ? Collections.emptyList() : List.of(PATH_SEPARATOR.split(jsonPath)));
        discovered = new JsonElement<?>[pathElems.size()];
        current = start;
    }


    // ==== METHODS ========================================================== //

    Optional<JsonElement<?>> find() {
        if (pathElems.isEmpty())
            return Optional.of(current);
        var index = 0;
        while (!pathElems.isEmpty()) {
            var pathElem = pathElems.remove(0);
            var next = (switch (current) {
                case JsonMap jMap -> jMap.tryGet(pathElem);
                case JsonList jList -> jList.tryGet(Integer.parseInt(pathElem));
                default -> Optional.ofNullable(pathElems.isEmpty() ? null : current);
            });
            if (next.isEmpty())
                return Optional.empty();
            discovered[index++] = current;
            current = next.get();
        }
        return Optional.of(current);
    }

    JsonElement<?>[] elemPath() {
        if (Arrays.stream(discovered).anyMatch(Objects::isNull))
            throw new IllegalStateException("No path was discovered yet!");
        return discovered.clone();
    }

    String[] stringPath() {
        return pathElems.toArray(String[]::new);
    }

}