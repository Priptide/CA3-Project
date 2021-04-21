package socialmedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    // Variables
    private String handle;

    private int id;

    private String description;

    private Map<Integer, Post> posts;

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
        this.posts = new HashMap<>();
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
        this.posts = new HashMap<>();
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
        // Create an empty list for the posts
        List<Post> currentPosts = new ArrayList<>();

        // Loop through the map
        for (Map.Entry<Integer, Post> currentSet : posts.entrySet()) {
            currentPosts.add(currentSet.getValue());
        }

        return currentPosts;
    }

    /**
     * Sets the users posts as a map of posts and ids
     * 
     * @param posts A hashmap of posts
     */
    public void setPosts(Map<Integer, Post> posts) {
        this.posts = posts;
    }

    /**
     * Adds a new post to the users collection of posts can also be used to update a
     * post
     * 
     * @param newPost The post to add/update
     */
    public void addPost(Post newPost) {
        posts.put(newPost.getId(), newPost);
    }

    /**
     * Remove a post by the user, this post must still exsist
     * 
     * @param oldPostID The id of the old post
     */
    public void removePost(int oldPostID) {
        posts.remove(oldPostID);
    }

    /**
     * Gets the users total endorsments
     * 
     * @return Total endorsments
     */
    public int getTotalEndorsments() {
        int total = 0;
        for (Post p : getPosts()) {
            total += p.getEndorsments();
        }
        return total;
    }

    /**
     * Returns the user as a string in the display
     * 
     * <pre>
     * ID: [account ID]
     * Handle: [account handle]
     * Description: [account description]
     * Post count: [total number of posts, including endorsements and replies]
     * Endorse count: [sum of endorsements received by each post of this account]
     * </pre>
     */
    public String toString() {
        return "ID: " + id + "\n Handle: " + handle + "\n Description: " + description + "\n Post count: "
                + posts.size() + "\n Endorse count: " + getTotalEndorsments();
    }

}
