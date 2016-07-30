package dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import entity.Order;

public class OrderDao extends BaseDao {

	/**
	 * Get order by orderID
	 * @param orderID
	 * @return Order
	 */
	public Order getOrderByID(int orderID) {
		try {
			return (Order) getSessionFactory().getCurrentSession().load(Order.class, orderID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * get order using given offset and limits
	 * @param offset 
	 * @param limits
	 * @return list of order
	 */
	@SuppressWarnings("unchecked")
	public List<Order> getOrders(int offset , int limits, int userID) {
		try {
			if(userID < 0) {
				String hql = "from Order od order by od.orderTime desc";
				return getSessionFactory().getCurrentSession().createQuery(hql)
						.setFirstResult(offset).setMaxResults(limits).list();
			}
			else {
				String hql = "from Order od where userID = ? order by od.orderTime desc";
				return getSessionFactory().getCurrentSession().createQuery(hql).setParameter(0, userID)
						.setFirstResult(offset).setMaxResults(limits).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * get the amount of order of certain user
	 * @param userID
	 * @return int
	 */
	public Integer getUserOrderAmount(int userID) {
		try {
			String hql = "select count(*) from Order where userID = ?";
			
			Long num = (Long) getSessionFactory().getCurrentSession().createQuery(hql)
										.setParameter(0, userID).list().get(0);
			return num.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * get the amount of order amount
	 * @return int
	 */
	public Integer getTotalOrderAmount() {
		try {
			String hql = "select count(*) from Order";
			Long num = (Long) getSessionFactory().getCurrentSession().createQuery(hql).list().get(0);
			return num.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * delete order
	 * @param orderID
	 * @return  true: success
	 * 			false: fail
	 */
	public boolean destroyOrder(int orderID) {
		try {
			Order order = (Order) getSessionFactory().getCurrentSession().get(Order.class, orderID);
			getSessionFactory().getCurrentSession().delete(order);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * get total sales amount by category
	 * @return List<Object []>
	 */
	public List getSaleAmountByCategory() {
		try {
			String sql = "CALL getSoldAmountByCategory";
			return getSessionFactory().getCurrentSession().createSQLQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * get total sales according to date group by format(date)
	 * @param format
	 * @return List<Object []>
	 */
	public List getTotalBy(String format) {
		try {
			String sql = "CALL getTotalPriceBy(:format)";
			return getSessionFactory().getCurrentSession().createSQLQuery(sql).setParameter("format", format).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get user behavior of what kind of book the user buy
	 * @param userID
	 * @return List<Object []>
	 */
	public List<Object []> getUserBehaviorBookKind(int userID) {
		try {
			String sql = "CALL getUserBehaviorOfBookCategory(:userID)";
			return getSessionFactory().getCurrentSession().createSQLQuery(sql).setParameter("userID", userID).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get user behavior of recent user sales statistic
	 * @param userID
	 * @return List<Object []>
	 */
	public List<Object []> getUserBehaviorRecentSales(int userID) {
		try {
			String sql = "CALL getUserRecentSales(:userID)";
			return getSessionFactory().getCurrentSession().createSQLQuery(sql)
							.setParameter("userID", userID)
							.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get sales statistic bewteen given date
	 * @param begin
	 * @param end
	 * @return List<Object []>
	 */
	public List<Object []> getTotalByGivenDate(String begin, String end) {
		try {
			String format = "%Y-%m-%d";
			String sql = "CALL getSalesWithLimitsBy(:format, :begin, :end)";
			return getSessionFactory().getCurrentSession().createSQLQuery(sql)
							.setParameter("format", format).setParameter("begin", begin).setParameter("end", end)
							.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * save order into database
	 * @param order
	 * @return
	 */
//	public boolean saveOrder(Order order) {
//		try {
//			getSessionFactory().getCurrentSession().save(order);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
	
	/**
	 * change the status of order
	 * @param userID
	 * @param orderID
	 * @param status
	 * @return  true: success
	 * 			false: fail
	 */
//	public boolean changeStatus(int orderID, int userID, char status) {
//		try {
//			//System.out.println(orderID);
//			Order order = (Order) getSessionFactory().getCurrentSession().get(Order.class, orderID);
//			//System.out.println(order);
//			//System.out.println(userID);
//			if(order.getUser().getUserID() != userID)	// the userID is not agreed
//				return false;
//			order.setIsDeal(status);
//			getSessionFactory().getCurrentSession().update(order);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
}
