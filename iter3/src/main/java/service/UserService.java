package service;

import java.util.List;

import entity.Page;
import entity.User;

public interface UserService {
	
	/**
	 * Save user into database
	 * @param user
	 * @return success or not
	 */
	public boolean save(User user);
	
	/**
	 * Update user in database.
	 * In order to update the role of user, it have to query for role object.
	 * @param user
	 * @return success or not.
	 */
	public boolean update(User user);
	
	/**
	 * Delete user in database
	 * @param userID 
	 * @return success or not
	 */
	public boolean delete(int userID);
	
	/**
	 * Get user from database.
	 * @param page using to pagination, should initialize with currentPage and pageSize
	 * @return List of user
	 */
	public List<User> query(Page page);
	
	/**
	 * Get user from database by username
	 * @param username
	 * @return User
	 */
	public User queryUserByUsername(String username);
	
	
	/**
	 * Save user profile into mongodb
	 * @return
	 */
	public boolean updateUserProfile(User user);
	
	/**
	 * Get user profile from mongo using user.getProfileID()
	 * @param user
	 * @return User with profile
	 */
	public User getUserProfile(User user);
}
