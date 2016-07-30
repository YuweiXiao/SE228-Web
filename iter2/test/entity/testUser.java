package entity;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;

import dao.UsersDao;
import db.HibernateSessionFactory;

public class testUser {
	
	@Test
	public void testGetUserFromDB()
	{
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List users = session.createQuery("from User").list();
		//transaction.commit();
		//session.getTransaction().commit();
		//session.close();
		for(int i=0; i < users.size(); ++i) {
			User user = (User) users.get(i);
			System.out.println(user);
		}
	}
	
	@Test
	public void testUserLogin(){
		User user = new User();
		user.setUsername("admin");
		user.setPassword("123456");
		UsersDao userDao = new UsersDao();
		Assert.assertEquals(userDao.usersLogin(user), true);
	}
}
