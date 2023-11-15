package de.trinext.framework.json;

import com.google.gson.Gson;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings("ClassCanBeRecord")
class JsonObjectParser {

    private final Object object;

    JsonObjectParser(Object object) {
        this.object = object;
    }

    JsonElement<?> parse() {
        return new JsonGsonParser(new Gson().toJsonTree(object)).parse();
    }

}