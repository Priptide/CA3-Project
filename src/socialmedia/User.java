package socialmedia;

public class User {
    // Variables
    private String handle;

    private int id;

    private String description;

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

}
