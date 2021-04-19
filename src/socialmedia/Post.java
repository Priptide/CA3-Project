package socialmedia;

public class Post {
    // Variables
    private String handle;

    private int id;

    private String message;

    // Constructors

    /**
     * Create a post with a handle and it's message and id
     * 
     * @param handle
     * @param id
     * @param message
     */
    public Post(String handle, int id, string message) {
        this.handle = handle;
        this.id = id;
        this.message = message;
    }

    /**
     * Gets the posts message
     * 
     * @return The posts message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the posts handle
     * 
     * @return The posts Handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Gets the posts current id
     * 
     * @return The post Id
     */
    public int getId() {
        return id;
    }
}
