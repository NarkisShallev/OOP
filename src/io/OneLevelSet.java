package io;

/**
 * create one level set.
 */
public class OneLevelSet {
    private String key;
    private String message;
    private String path;

    /**
     * constructor.
     *
     * @param key     key
     * @param message message
     * @param path    path
     */
    public OneLevelSet(String key, String message, String path) {
        this.key = key;
        this.message = message;
        this.path = path;
    }

    /**
     * get key.
     *
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * get message.
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * get path.
     *
     * @return path
     */
    public String getPath() {
        return path;
    }
}
