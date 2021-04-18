package socialmedia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * SocialMedia is a compiling, functioning implementor of the
 * SocialMediaPlatform interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {

	// Create an empty hashmap for our users
	private Map<String, User> currentUsers;

	public SocialMedia() {
		this.currentUsers = new HashMap<>();
	}

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		// Check the handle
		handle = validateHandle(handle);
		// Check this handle doesn't already exist
		if (checkForHandle(handle))
			throw IllegalHandleException("Handle already exsists");

		// Now we get the users id as the next index in our hash map
		id = currentUsers.size();

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
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfAccounts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalOriginalPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalCommentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedPost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedAccount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void erasePlatform() {
		// TODO Auto-generated method stub

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
			throw InvalidHandleException("The handle can't be empty");

		if (handle.length() > 30)
			throw InvalidHandleException("The handle must be 30 characters or less");

		if (handle.split(" ").length > 1)
			throw InvalidHandleException("The handle can't contain whitespaces");

		return handle;
	}

	public boolean checkForHandle(String handle) {
		return currentUsers.containsKey(handle);
	}

}
