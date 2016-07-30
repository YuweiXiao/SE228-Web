package dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import db.HibernateSessionFactory;
import entity.Category;
import entity.Order;
import entity.OrdersBook;
import entity.User;

public class OrderDao {
	public boolean saveOrder(Order order) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.save(order);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
	}
	
	public List<Order> getOrders(int offset , int limits) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			String hql = "from Order";
			session.beginTransaction();
			List orders = session.createQuery(hql).list();
			System.out.println(orders);
			session.getTransaction().commit();
			return orders;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Order> getUserOrders(int userID) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		String hql = "";
		try {
			session.beginTransaction();
			hql = "from Order od where userID=:userID order by od.orderTime desc";
			//hql = "from Order as order where userID=:userID order by order.orderTime";
			List<Order> orders = session.createQuery(hql).setParameter("userID", userID).list();
			//
			session.getTransaction().commit();
			//System.out.println(orders);
			return orders;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
	}
	
	public boolean destroyOrdersBook(int orderID, int bookID) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		String hql = "";
		try {
			session.beginTransaction();
			hql = "from Order where orderID=:orderID";
			List<Order> orders = session.createQuery(hql).setParameter("orderID", orderID).list();
			if(orders.size() == 0)
				return false;
			Order order = orders.get(0);
			Set<OrdersBook> books = order.getBooks();
			for (Iterator iterator = books.iterator(); iterator.hasNext();) {
				OrdersBook ordersBook = (OrdersBook) iterator.next();
				if(ordersBook.getBook().getBookID() == bookID)
				{
					books.remove(ordersBook);
					break;
				}
			}
			session.update(order);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
	}
	
	public boolean destroyOrder(int orderID) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Order order = (Order) session.load(Order.class, orderID);
			session.delete(order);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
	}
	
	public boolean changeStatus(int orderID, char status) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Order order = (Order) session.load(Order.class, orderID);
			order.setIsDeal(status);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
	}
	
	public Map<String, Object> getSaleAmountByCategory() {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		String sql;
		try {
			session.beginTransaction();
			sql = "select name, sum(sales) from books join bookscategory using(bookID) join category using(categoryID) group by categoryID";
			List<Object[]> list = session.createSQLQuery(sql).list();
			List<String> categoryName = new ArrayList<String>();
			List<String> amount = new ArrayList<String>();
			list.stream().forEach((record)->{
				String category = (String)record[0];
				BigDecimal  num = (BigDecimal)record[1];
				categoryName.add(category);
				amount.add(num.toString());
//				System.out.println(category+":"+num);
			});
			session.getTransaction().commit();
			Map<String, Object> t = new HashMap<String, Object>();
			t.put("categoryName", categoryName);
			t.put("amount", amount);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}
}
