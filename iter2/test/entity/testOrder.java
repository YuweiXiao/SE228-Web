package entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import db.HibernateSessionFactory;

public class testOrder {
	@Test
	public void testGetOrderFromDB()
	{
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List orders = session.createQuery("from Order").list();
		//transaction.commit();
		//session.getTransaction().commit();
		//session.close();
		for(int i=0; i < orders.size(); ++i) {
			Order order = (Order) orders.get(i);
			Set<OrdersBook> books = order.getBooks();
			for (Iterator iterator = books.iterator(); iterator.hasNext();) {
				OrdersBook ordersBook = (OrdersBook) iterator
						.next();
				System.out.println(ordersBook);
			}
		}
	}
	
	@Test
	public void testSaveOrder()
	{
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Order order = new Order();
		Date date = new Date();
		order.setIsDeal('N');
		order.setOrderTime(new Timestamp(date.getTime()));
		User user = (User) session.createQuery("from User where userID = ?").setParameter(0, 1).list().get(0);
		order.setUser(user);
//		session.save(order);
//		session.flush();
		System.out.println(order);
		System.out.println("ok there");
		List books = session.createQuery("from Book").list();
		for(int i = 0; i < books.size(); ++i) {
//			OrderAssociation oa = new OrderAssociation();
			OrdersBook ob = new OrdersBook();
			ob.setBook((Book)books.get(i));
			ob.setAmount(1);
			
			System.out.println(ob);
			order.getBooks().add(ob);
		}
		session.save(order);
		session.getTransaction().commit();
	}
}
