package socialmedia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * SocialMedia is a compiling, functioning implementor of the
 * SocialMediaPlatform interface.
 * 
 * @author Charlie Crooke & Owen Redstone
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {

	// Create a map for our users
	private Map<String, User> currentUsers;

	// Create a map for our posts
	private Map<Integer, Post> posts;

	private int currentIndex;

	private int idSetter;

	public SocialMedia() {
		this.currentUsers = new HashMap<>();
		this.posts = new HashMap<>();

		currentIndex = 0;
		idSetter = 0;
	}

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		// Check the handle
		handle = validateHandle(handle);
		// Check this handle doesn't already exist
		if (checkForHandle(handle))
			throw new IllegalHandleException("A user with that handle already exists");

		// Now we get the users id as the next index in our hash map
		int id = currentIndex;

		// Update the current index
		currentIndex++;

		// Create the user as an object
		User localUser = new User(handle, id);

		// Add the user to the current users map
		currentUsers.put(handle, localUser);

		// Return the id
		return id;
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {

		// Create a user and get it's id
		int id = createAccount(handle);

		// Get that user and then give it a description
		currentUsers.get(handle).setDescription(description);

		// Return the users id
		return id;
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		// For this we will iterate through all accounts and check their id
		Iterator<Map.Entry<String, User>> accounts = currentUsers.entrySet().iterator();
		while (accounts.hasNext()) {

			// Get next entry
			Map.Entry<String, User> user = accounts.next();

			// If this entry has the id we need then we will remove it and return
			if (user.getValue().getId() == id) {

				String handle = user.getKey();
				// Get the user we are removing
				User removedUser = currentUsers.get(handle);

				// Now we take the users posts and check through them
				for (Post removingPosts : removedUser.getPosts()) {
					if (removingPosts.isEndorsed()) {
						posts.get(removingPosts.getOriginalPost().getId()).removeEndorsment(removingPosts);
						posts.remove(removingPosts.getId());
					} else {
						try {
							deletePost(removingPosts.getId());
						} catch (Exception e) {

							// Using this to keep compiler happy but this show never cause an error
							System.out.println("Error, posts unsynced!");

						}
					}

				}

				// Remove the account from current accounts
				currentUsers.remove(handle);

				return;
			}
		}

		// If we find no users then we will throw an error
		throw new AccountIDNotRecognisedException("There is no user with this id");

	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {

		// Check account exists
		if (!checkForHandle(handle))
			throw new HandleNotRecognisedException("A user with that handle doesn't exist");

		// Get the user we are removing
		User removedUser = currentUsers.get(handle);

		// Now we take the users posts and check through them
		for (Post removingPosts : removedUser.getPosts()) {
			if (removingPosts.isEndorsed()) {
				posts.get(removingPosts.getOriginalPost().getId()).removeEndorsment(removingPosts);
				posts.remove(removingPosts.getId());
			} else {
				try {
					deletePost(removingPosts.getId());
				} catch (Exception e) {

					// Using this to keep compiler happy but this show never cause an error
					System.out.println("Error, posts unsynced!");

				}
			}

		}

		// Remove the account from current accounts
		currentUsers.remove(handle);
	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {

		// Check account exsists
		if (!checkForHandle(oldHandle))
			throw new HandleNotRecognisedException("A user with that handle doesn't exist");

		// Check the handle
		newHandle = validateHandle(newHandle);

		// Check this handle doesn't already exist
		if (checkForHandle(newHandle))
			throw new IllegalHandleException("A user with the new handle already exists");

		// Create a copy of the current user
		User currentUser = currentUsers.get(oldHandle);

		// Update the users handle
		currentUser.setHandle(newHandle);

		// Update all the users posts handles
		for (Post updatingPosts : currentUser.getPosts()) {
			// Update post handles
			posts.get(updatingPosts.getId()).setHandle(newHandle);
			updatingPosts.setHandle(newHandle);

			// Add updated post to the user
			currentUser.addPost(updatingPosts);
		}

		// Create a new mapping for that user with their new handle
		currentUsers.put(newHandle, currentUser);

		// Remove the old mapping
		currentUsers.remove(oldHandle);
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {

		// Check account exsists
		if (!checkForHandle(handle))
			throw new HandleNotRecognisedException("A user with that handle doesn't exist");

		// Update description
		currentUsers.get(handle).setDescription(description);
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {

		// Check account exsists
		if (!checkForHandle(handle))
			throw new HandleNotRecognisedException("A user with that handle doesn't exist");

		return currentUsers.get(handle).toString();
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {

		// Check this handle already exist
		if (!checkForHandle(handle))
			throw new HandleNotRecognisedException("There is no user with this handle");

		// checks message is valid
		message = validateMessage(message);
		// set id as the current sequential number
		int id = idSetter;

		// increment the sequential number
		idSetter++;

		// Create a new post
		Post userPost = new Post(handle, id, message);

		// Add it to the global list of posts
		posts.put(id, userPost);

		// Add it to the users posts
		currentUsers.get(handle).addPost(userPost);

		return id;
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		// Check this handle already exist
		if (!checkForHandle(handle))
			throw new HandleNotRecognisedException("There is no user with this handle");
		// Check this post id already exist
		if (!checkForId(id, false))
			throw new PostIDNotRecognisedException("There is no post with this ID");

		// Get the post we want to endorse
		Post endorsedPost = posts.get(id);

		if (endorsedPost.isEndorsed())
			throw new NotActionablePostException("You can't endorse an endorsed post");

		// Set a new id for the endorsed post
		int postId = idSetter;

		// increment the sequential number
		idSetter++;

		// Here we make sure to truncate the string if it is too long
		String endorsedMessage = "EP@" + endorsedPost.getHandle() + ": " + endorsedPost.getMessage();
		endorsedMessage = endorsedMessage.substring(0, Math.min(endorsedMessage.length(), 99));

		// Create a new endorsed post
		Post userPost = new Post(handle, postId, endorsedMessage, true, endorsedPost);

		posts.put(postId, userPost);

		// Add it to the users posts
		currentUsers.get(handle).addPost(userPost);

		// Add endorsments to the post
		endorsedPost.addEndorsment(userPost);

		// Update the users list of posts
		currentUsers.get(endorsedPost.getHandle()).addPost(endorsedPost);

		return postId;
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {

		// Validate our message is valid
		message = validateMessage(message);

		// Check for the user and post exsistsing
		if (!checkForHandle(handle))
			throw new HandleNotRecognisedException("There is no user with this handle");
		if (!checkForId(id, false))
			throw new PostIDNotRecognisedException("There is no post with this ID");

		// Get the post we want to check
		Post commentedPost = posts.get(id);

		// Check the post isn't endorsed
		if (commentedPost.isEndorsed())
			throw new NotActionablePostException("You can't comment on an endorsed post");

		int postId = idSetter;

		idSetter++;

		// Create a new
		Post comment = new Post(handle, postId, message, false, commentedPost);

		// Add it to the users posts
		currentUsers.get(handle).addPost(comment);

		// Add to our current posts this comment
		posts.put(postId, comment);

		// Add comment too the main post
		commentedPost.addComment(comment);

		// Update the users list of posts
		currentUsers.get(commentedPost.getHandle()).addPost(commentedPost);

		return postId;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// Check this post already exist
		if (!checkForId(id, false))
			throw new PostIDNotRecognisedException("There is no post with this ID");

		// Get the post we want to remove
		Post currentPost = posts.get(id);

		// First we remove all the endorsed posts by looping through them
		for (Post endorsedPost : currentPost.getEndorsedPosts()) {
			posts.remove(endorsedPost.getId());
			currentUsers.get(endorsedPost.getHandle()).removePost(endorsedPost.getId());
		}

		// And we remove the post from our users post list
		currentUsers.get(currentPost.getHandle()).removePost(currentPost.getId());

		// Now we mark the master post as removed
		currentPost.removePost();

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// Check this post already exist
		if (!checkForId(id, true))
			throw new PostIDNotRecognisedException("There is no post with this ID");

		// Get the post we want to show
		Post currentPost = posts.get(id);

		String handle;

		// Replace the handle if the user or post was removed
		if (currentPost.getHandle().equals(""))
			handle = "<removed>";
		else
			handle = currentPost.getHandle();

		return "ID: " + id + "\n  Account: " + handle + "\n  No. endorsments: " + currentPost.getEndorsments()
				+ " | No. Comments: " + currentPost.getTotalComments() + "\n  " + currentPost.getMessage();
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {

		// Check post exsist
		if (!checkForId(id, true))
			throw new PostIDNotRecognisedException("There is no post with this ID");
		// Get the post we want to check
		Post commentedPost = posts.get(id);

		// Check the post isn't endorsed
		if (commentedPost.isEndorsed())
			throw new NotActionablePostException("An endorsed post has no children");

		// We use this for adding new indents
		final String remove = "\n  ";

		// Create a new string builder
		StringBuilder outputBuilder = new StringBuilder();

		// Add the first post
		outputBuilder.append(showIndividualPost(id));

		// We only add the next line if we have any comments below
		if (posts.get(id).getTotalComments() > 0) {
			outputBuilder.append("\n  |");
		}

		// Loop through the posts comments
		for (int currentId : posts.get(id).getCommentIds()) {
			// Find the comment as a string
			String localComment = showIndividualPost(currentId);

			// Add more new lines too the comment
			localComment = localComment.replace(remove, "\n" + ("    ".repeat(2)));
			outputBuilder.append("\n  | > " + localComment);

			// Check if we have any comments on the post
			if (posts.get(currentId).getTotalComments() > 0) {
				// Loop through these comments in a new string builder
				StringBuilder commentBuilder = new StringBuilder();
				for (int commentID : posts.get(currentId).getCommentIds()) {
					commentBuilder.append("\n|\n| > " + showPostChildrenDetails(commentID));
				}
				String comments = commentBuilder.toString().replace(remove, "\n" + ("       ".repeat(2)));
				comments = comments.replace("\n|", "\n" + ("    ".repeat(2)) + "|");
				outputBuilder.append(comments);

			}
		}
		return outputBuilder;
	}

	@Override
	public int getNumberOfAccounts() {
		return currentUsers.size();
	}

	@Override
	public int getTotalOriginalPosts() {
		// Loop through all the posts currently in the system
		Iterator<Map.Entry<Integer, Post>> postsCurrent = posts.entrySet().iterator();
		int originalPosts = 0;
		while (postsCurrent.hasNext()) {

			// Get next post
			Map.Entry<Integer, Post> user = postsCurrent.next();

			// Check the post is an original and not deleted
			if (user.getValue().getOriginalPost() == null && !user.getValue().getHandle().equals("")) {
				originalPosts++;
			}
		}
		return originalPosts;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		// Loop through all the posts currently in the system
		Iterator<Map.Entry<Integer, Post>> postsCurrent = posts.entrySet().iterator();
		int endorsedPosts = 0;
		while (postsCurrent.hasNext()) {

			// Get next post
			Map.Entry<Integer, Post> user = postsCurrent.next();

			// Check the post is endorsed and not deleted
			if (user.getValue().isEndorsed() && !user.getValue().getHandle().equals("")) {
				endorsedPosts++;
			}
		}
		return endorsedPosts;
	}

	@Override
	public int getTotalCommentPosts() {
		// Loop through all the posts currently in the system
		Iterator<Map.Entry<Integer, Post>> postsCurrent = posts.entrySet().iterator();
		int commentPosts = 0;
		while (postsCurrent.hasNext()) {

			// Get next post
			Map.Entry<Integer, Post> user = postsCurrent.next();

			// Check the post isn't endorsed is a comment and not deleted
			if (!user.getValue().isEndorsed() && user.getValue().getOriginalPost() != null
					&& !user.getValue().getHandle().equals("")) {
				commentPosts++;
			}
		}
		return commentPosts;
	}

	@Override
	public int getMostEndorsedPost() {
		// Loop through all the posts currently in the system
		Iterator<Map.Entry<Integer, Post>> postsCurrent = posts.entrySet().iterator();
		Post topEndorsment = null;
		// We set an int to save computation later when checking against this value
		int topValue = 0;
		while (postsCurrent.hasNext()) {
			// Get next post
			Map.Entry<Integer, Post> user = postsCurrent.next();

			Post check = user.getValue();
			// If there is no top post place this one there
			if (topEndorsment == null) {
				topEndorsment = check;
				topValue = check.getEndorsments();
			} else {
				// We check only greater so we can't have a tie
				if (user.getValue().getEndorsments() > topValue) {
					topEndorsment = check;
					topValue = check.getEndorsments();
				}
			}
		}

		// Here we avoid a null exception
		if (topEndorsment != null) {
			return topEndorsment.getId();
		} else {
			return 0;
		}
	}

	@Override
	public int getMostEndorsedAccount() {

		// Loop through all the posts currently in the system
		Iterator<Map.Entry<String, User>> usersCurrent = currentUsers.entrySet().iterator();
		User topEndorsment = null;
		// We set an int to save computation later when checking against this value
		int topValue = 0;
		while (usersCurrent.hasNext()) {
			// Get next post
			Map.Entry<String, User> user = usersCurrent.next();

			User check = user.getValue();
			// If there is no top post place this one there
			if (topEndorsment == null) {
				topEndorsment = check;
				topValue = check.getTotalEndorsments();
			} else {
				// We check only greater so we can't have a tie
				if (user.getValue().getTotalEndorsments() > topValue) {
					topEndorsment = check;
					topValue = check.getTotalEndorsments();
				}
			}
		}

		// Here we avoid a null exception
		if (topEndorsment != null) {
			return topEndorsment.getId();
		} else {
			return 0;
		}
	}

	@Override
	public void erasePlatform() {
		this.currentUsers = new HashMap<>();
		this.posts = new HashMap<>();

		currentIndex = 0;
		idSetter = 0;
	}

	@Override
	public void savePlatform(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	/**
	 * This is used to validate a user's handle doesn't contain invalid characters
	 * 
	 * @param handle
	 * @return The handle if validated
	 * @throws InvalidHandleException When the handle isn't valid
	 */
	public String validateHandle(String handle) throws InvalidHandleException {
		if (handle.isEmpty())
			throw new InvalidHandleException("The handle can't be empty");

		if (handle.length() > 30)
			throw new InvalidHandleException("The handle must be 30 characters or less");

		if (handle.split(" ").length > 1)
			throw new InvalidHandleException("The handle can't contain whitespaces");

		return handle;
	}

	/**
	 * Checks if a user with a given handle exsists
	 * 
	 * @param handle
	 * @return True if there is a user with the given handle
	 */
	public boolean checkForHandle(String handle) {
		return currentUsers.containsKey(handle);
	}

	/**
	 * Validates the message isn't empty or too many characters
	 * 
	 * @param message
	 * @return The message if validated
	 * @throws InvalidPostException When the message is invalid
	 */
	public String validateMessage(String message) throws InvalidPostException {
		if (message.isEmpty())
			throw new InvalidPostException("The message can't be empty");

		if (message.length() > 100)
			throw new InvalidPostException("The message must be 100 characters or less");

		return message;
	}

	/**
	 * Checks if a post with a given id exsists
	 * 
	 * @param id           The posts id
	 * @param canBeDeleted Check for posts that have been deleted
	 * 
	 * @return True if there is a post with the given id and `it hasn't been removed
	 */
	public boolean checkForId(int id, boolean canBeDeleted) {

		if (canBeDeleted)
			return posts.containsKey(id);

		if (posts.containsKey(id))
			return !posts.get(id).isRemoved();
		return false;
	}

}
