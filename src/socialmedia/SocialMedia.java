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

	private int currentIndex;

	public SocialMedia() {
		this.currentUsers = new HashMap<>();

		currentIndex = 0;
	}

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		// Check the handle
		handle = validateHandle(handle);
		// Check this handle doesn't already exist
		if (checkForHandle(handle))
			throw IllegalHandleException("A user with that handle already exists");

		// Now we get the users id as the next index in our hash map
		id = currentIndex;

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
		Iterator<String, User> accounts = currentUsers.entrySet().iterator();
		while (accounts.hasNext()) {

			// Get next entry
			Map.Entry<String, User> user = (Map.Entry) accounts.next();

			// If this entry has the id we need then we will remove it and return
			if (user.getValue().getId == id) {
				currentUsers.remove(user.getKey());
				return;
			}

			// This line helps avoid a ConcurrentModificationException
			accounts.remove();
		}

		// If we find no users then we will throw an error
		throw AccountIDNotRecognisedException("There is no user with this id");

	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {

		// Check account exists
		if (!checkForHandle(handle))
			throw HandleNotRecognisedException("A user with that handle doesn't exist");

		// Remove the account from current accounts
		currentUsers.remove(handle);

	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {

		// Check account exsists
		if (!checkForHandle(oldHandle))
			throw HandleNotRecognisedException("A user with that handle doesn't exist");

		// Check the handle
		newHandle = validateHandle(newHandle);

		// Check this handle doesn't already exist
		if (checkForHandle(newHandle))
			throw IllegalHandleException("A user with the new handle already exists");

		// Create a copy of the current user
		User currentUser = currentUsers.get(oldHandle);

		// Update the users handle
		currentUser.setHandle(newHandle);

		// Create a new mapping for that user with their new handle
		currentUsers.put(newHandle, currentUser);

		// Remove the old mapping
		currentUsers.remove(oldHandle);
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {

		// Check account exsists
		if (!checkForHandle(handle))
			throw HandleNotRecognisedException("A user with that handle doesn't exist");

		// Update description
		currentUsers.get(handle).setDescription(description);
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {

		// Check account exsists
		if (!checkForHandle(handle))
			throw HandleNotRecognisedException("A user with that handle doesn't exist");

		return currentUsers.get(handle).toString();
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

	/**
	 * Checks a user with a given handle exsists
	 * 
	 * @param handle
	 * @return True if there is a user with that handle
	 */
	public boolean checkForHandle(String handle) {
		return currentUsers.containsKey(handle);
	}

}
