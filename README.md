# Functionality

This artifact retains 3 different functionalities:

- Serializing any instance into a Json String
- Deserializing a Json input source (`CharSequence`, `File`, `InputStream`) ...
    - ...into an instance of a concrete class.
    - ...into an abstract model of elements (`JsonObject`, `JsonPrimitive`).