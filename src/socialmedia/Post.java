package socialmedia;

import java.util.ArrayList;
import java.util.List;

public class Post {
    // Variables
    private String handle;

    private int id;

    private String message;

    private int endorsments;

    private int comments;

    private boolean isEndorsment;

    private List<Post> endorsedPosts;

    private List<Post> commentedPosts;

    private boolean isRemoved;

    private Post originalPost;

    // Constructors

    /**
     * Create a post with a users handle and it's message and id
     * 
     * @param handle  The post's users handle
     * @param id      The id of the Post
     * @param message The Posts Message
     */
    public Post(String handle, int id, String message) {
        this.handle = handle;
        this.id = id;
        this.message = message;
        this.endorsments = 0;
        this.isEndorsment = false;
        this.isRemoved = false;
        this.endorsedPosts = new ArrayList<>();
        this.commentedPosts = new ArrayList<>();
    }

    /**
     * Create a post with a users handle, it's message, id and if it's endorsed
     * 
     * @param handle       The post's users handle
     * @param id           The id of the Post
     * @param message      The Posts Message
     * @param isEndorsment Whether or not the Post is endorsed
     * @param originalPost The post that this is endorsing
     */
    public Post(String handle, int id, String message, boolean isEndorsment, Post originalPost) {
        this.handle = handle;
        this.id = id;
        this.message = message;
        this.endorsments = 0;
        this.isEndorsment = isEndorsment;
        this.originalPost = originalPost;
        this.endorsedPosts = new ArrayList<>();
        this.commentedPosts = new ArrayList<>();
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
     * Update the posts user's handle
     * 
     * @param newHandle New User Handle
     */
    public void setHandle(String newHandle) {
        this.handle = newHandle;
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
     * Add a new endorsment to this post
     * 
     * @param endorsedPost The new endorsed post
     */
    public void addEndorsment(Post endorsedPost) {
        this.endorsments += 1;
        this.endorsedPosts.add(endorsedPost);
    }

    /**
     * Removes any endorsed posts
     * 
     * @param endorsedPost
     */
    public void removeEndorsment(Post endorsedPost) {
        this.endorsments -= 1;
        this.endorsedPosts.remove(endorsedPost);
    }

    /**
     * Get whether or not the post is an endorsment
     * 
     * @return True if it's an endorsment post
     */
    public boolean isEndorsed() {
        return isEndorsment;
    }

    /**
     * Gets if the post has been removed or not
     * 
     * @return True if the post has been removed
     */
    public boolean isRemoved() {
        return isRemoved;
    }

    /**
     * No arguments function to remove the post
     */
    public void removePost() {
        isRemoved = true;
        message = "The original content was removed from the system and is no longer available.";
        handle = "";
    }

    /**
     * Add a comment to the selected post
     * 
     * @param commentPost The new post which you have comented on
     */
    public void addComment(Post commentPost) {
        commentedPosts.add(commentPost);
        comments += 1;
    }

    /**
     * Gives a list of comment ids so that we don't need to continually update the
     * posts here
     * 
     * @return List of Commented Posts IDs
     */
    public List<Integer> getCommentIds() {
        List<Integer> commentIds = new ArrayList<>();
        for (Post p : commentedPosts) {
            commentIds.add(p.getId());
        }
        return commentIds;
    }

    public int getTotalComments() {
        return comments;
    }

    /**
     * Gets all the endorsed posts of this current post
     * 
     * @return A list of endorsed posts
     */
    public List<Post> getEndorsedPosts() {
        return endorsedPosts;
    }

    /**
     * Gets the original post of this endorsed post
     * 
     * @return The original post
     */
    public Post getOriginalPost() {
        return originalPost;
    }

}
