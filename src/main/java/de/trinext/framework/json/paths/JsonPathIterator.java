package de.trinext.framework.json.paths;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Dennis Woithe
 */
class JsonPathIterator implements Iterator<String> {

    private static final Pattern PATH_SEPARATOR = Pattern.compile("(?<!\\\\)\\.");
    private final String[] pathElems;

    private int i;

    // ==== CONSTRUCTORS ===================================================== //

    JsonPathIterator(String jsonPath) {
        pathElems = PATH_SEPARATOR.split(jsonPath);
        i = 0;
    }


    // ==== METHODS ========================================================== //

    @Override
    public String toString() {
        return Stream.of(pathElems)
                .limit(i)
                .collect(Collectors.joining("."));
    }

    @Override
    public boolean hasNext() {
        return i < pathElems.length;
    }

    @Override
    public String next() {
        if (!hasNext())
            throw new NoSuchElementException("Reached the end of \"" + this + "\"");
        return pathElems[i++];
    }

}