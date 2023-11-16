package de.trinext.framework.json;

import com.google.gson.JsonParser;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings("ClassCanBeRecord")
class JsonTextParser {

    private final String json;

    JsonTextParser(String json) {
        this.json = json;
    }

    JsonElement<?> parse() {
        return new JsonGsonParser(JsonParser.parseString(json)).parse();
    }

}