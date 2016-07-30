package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import db.HibernateSessionFactory;
import entity.Book;
import entity.Role;
import entity.User;

public class UsersDao {
	
	public boolean usersLogin(User u) {
		String hql = "";
		try {
			Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			hql = "from User where username=? and password=?";
			Query query = session.createQuery(hql);
			query.setParameter(0, u.getUsername());
			query.setParameter(1, u.getPassword());
			List list = query.list();
			session.getTransaction().commit();
			if( list.size() > 0 )
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isUsernameExists(String username) {
		User user = getUserByUsername(username);
		if(user == null)
			return false;
		return true;
	}
	
	public User saveUser(User u) {
		String hql = "";
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.save(u);
			session.getTransaction().commit();
			return u;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
	}
	
	public User getUserByUsername(String username) {
		String hql = "";
		try {
			Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			hql = "from User where username=?";
			Query query = session.createQuery(hql);
			query.setParameter(0, username);
			List<User> list = query.list();
			session.getTransaction().commit();
			if( list.size() > 0 )
				return list.get(0);
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Integer getTotalUserAmount() {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			String hql = "select count(*) from User";
			session.beginTransaction();
			int num = ((Long)session.createQuery(hql).iterate().next()).intValue();
			session.getTransaction().commit();
			return num;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return null;
		
	}
	
	public List<User> getUsers(int offset , int limits) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			String hql = "from User";
			session.beginTransaction();
			List users = session.createQuery(hql).list();
			session.getTransaction().commit();
			System.out.println(users);
			return users;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return null;
	}

	
	public boolean updateUser(User user) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.update(user);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		return false;
	}
	
	public boolean destroyUser(int userID) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			User user = (User) session.load(User.class, userID);
			session.delete(user);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}
}
