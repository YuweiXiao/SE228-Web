package dao;

import java.util.List;

import org.hibernate.Session;

import entity.User;

public class UserDao extends BaseDao{
	
	/**
	 * Get user by userID
	 * @param userID
	 * @return user
	 */
	public User getUserByID(int userID) {
		try {
			return (User) getSessionFactory().getCurrentSession().load(User.class, userID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * get user using username
	 * @param username
	 * @return user with given username
	 */
	public User getUserByUsername(String username) {
		try {
			String hql = "from User where username=?";
			List list = getSessionFactory().getCurrentSession().createQuery(hql).setParameter(0, username).list();
			if( list.size() > 0 )
				return (User) list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * get total user amount
	 * @return Integer
	 */
	public Integer getTotalUserAmount() {
		try {
			String hql = "select count(*) from User";
			Long num = (Long) getSessionFactory().getCurrentSession().createQuery(hql).list().get(0);
			return num.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * delete user from database
	 * @param userID
	 * @return true: success
	 * 		   false: fail
	 */
	public boolean destroyUser(int userID) {
		try {
			Session session = getSessionFactory().getCurrentSession();
			User user = (User) session.load(User.class, userID);
			session.delete(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * get user from database using given offset and limits
	 * @param offset
	 * @param limits
	 * @return list of user
	 */
//	public List<User> getUsers(int offset , int limits) {
//		try {
//			@SuppressWarnings({ "rawtypes", "unchecked" })
//			String hql = "from User";
//			List users = getSessionFactory().getCurrentSession().createQuery(hql).setFirstResult(offset).setMaxResults(limits).list();
//			return users;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	/**
	 * update user in database
	 * @param user
	 * @return true: success
	 * 		   false: fail
	 */
//	public boolean updateUser(User user) {
//		try {
//			getSessionFactory().getCurrentSession().update(user);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		return false;
//	}

	/**
	 * save user u into database
	 * @param u
	 * @return user u
	 */
//	public User saveUser(User u) {
//		try {
//			getSessionFactory().getCurrentSession().save(u);
//			return u;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	// [TODO] method should not in DAO
//	public boolean isUsernameExists(String username) {
//		User user = getUserByUsername(username);
//		if(user == null)
//			return false;
//		return true;
//	}
}
