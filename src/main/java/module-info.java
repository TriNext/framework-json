import de.trinext.framework.json.Json;

/**
 * Currently a wrapper for com.google.gson
 *
 * @author Dennis Woithe
 * @see Json
 */
module framework.json {
    exports de.trinext.framework.json;
    exports de.trinext.framework.json.paths;
    requires com.google.gson;
}