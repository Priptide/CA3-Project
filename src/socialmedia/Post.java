package socialmedia;

public class Post {
    // Variables
    private String handle;

    private int id;

    private String message;

    private int endorsments;

    private bool isEndorsment;

    // Constructors

    /**
     * Create a post with a users handle and it's message and id
     * 
     * @param handle  The post's users handle
     * @param id      The id of the Post
     * @param message The Posts Message
     */
    public Post(String handle, int id, string message) {
        this.handle = handle;
        this.id = id;
        this.message = message;
        this.endorsments = 0;
        this.isEndorsment = false;
    }

    /**
     * Create a post with a users handle, it's message, id and if it's endorsed
     * 
     * @param handle       The post's users handle
     * @param id           The id of the Post
     * @param message      The Posts Message
     * @param isEndorsment Whether or not the Post is endorsed
     */
    public Post(String handle, int id, string message, boolean isEndorsment) {
        this.handle = handle;
        this.id = id;
        this.message = message;
        this.endorsments = 0;
        this.isEndorsment = isEndorsment;
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

    /**
     * Get the posts total endorsments
     * 
     * @return Total Endosrments
     */
    public int getEndorsments() {
        return endorsments;
    }

    /**
     * Add endorsments to the post
     * 
     * @param endorsments Ammount of endorsments to add
     */
    public void addEndorsments(int endorsments) {
        this.endorsments += endorsments;
    }

    /**
     * Get whether or not the post is an endorsment
     * 
     * @return True if it's an endorsment post
     */
    public boolean isEndorsed() {
        return isEndorsment;
    }
}
