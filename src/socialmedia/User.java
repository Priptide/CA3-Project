package socialmedia;

import java.util.ArrayList;

public class User {
    // Variables
    private String handle;

    private int id;

    private String description;

    private List<Post> posts;

    // Constructors

    /**
     * Create a user with a handle and it's given id
     * 
     * @param handle
     * @param id
     */
    public User(String handle, int id) {
        this.handle = handle;
        this.id = id;
        this.description = "";
        this.posts = new ArrayList<>();
    }

    /**
     * Create a user wtih a handle, description and it's given id
     * 
     * @param handle
     * @param id
     * @param description
     */
    public User(String handle, int id, String description) {
        this.handle = handle;
        this.id = id;
        this.description = description;
        this.posts = new ArrayList<>();
    }

    /**
     * Gets the users description
     * 
     * @return The Users Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets or updates the users description
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the users handle
     * 
     * @return The Users Handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets or updates the users handle
     * 
     * @param handle
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Gets the users current id
     * 
     * @return The Users Id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets or updates the users current id
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets all the posts of the user
     * 
     * @return Posts in a list
     */
    public List<Post> getPosts() {
        return this.posts;
    }

    /**
     * Sets the users posts as a list of posts
     * 
     * @param posts
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    /**
     * Adds a new post to the users collection of posts
     * 
     * @param newPost
     */
    public void addPost(Post newPost) {
        posts.add(newPost);
    }

    /**
     * Remove a post by the user, this post must still exsist
     * 
     * @param oldPost
     */
    public void removePost(Post oldPost) {
        posts.remove(oldPost);
    }

    public String toString() {
        // TODO: Add posts and endorsments
        return "ID: " + id + "\nHandle: " + handle + "\nDescription: " + description + "\nPosts: \nEndorsments: ";
    }

}
