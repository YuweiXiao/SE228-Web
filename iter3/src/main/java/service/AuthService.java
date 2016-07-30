package service;

import entity.User;

public abstract class AuthService {
	
	/**
	 * Whether the user is logged in.
	 * @return yes or not
	 */
	public abstract boolean isLogin();
	
	/**
	 * Login 
	 * @return success or not
	 */
	public abstract String login(User user);
	
	/**
	 * log out, remove user from session.
	 * @return true log out successfully
	 * 		   false   user have not logged in.
	 */
	public abstract boolean logout();
	
	/**
	 * Register
	 * @return success or not
	 */
	public abstract boolean register(User user);
	
	/**
	 * Add user to session
	 */
	protected abstract void allow_passport(User user);
	
}
