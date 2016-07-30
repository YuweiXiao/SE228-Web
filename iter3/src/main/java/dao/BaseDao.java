package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

public class BaseDao {
	private SessionFactory sessionFactory;

	/**
	 * Execute query using hql, offset and limits
	 * @param hql the hql used to query
	 * @param offset -1 means no offset
	 * @param limits -1 means no limits
	 * @return List of Object
	 */
	public List query(String hql, int offset, int limits) {
		try {
			Query q = getSessionFactory().getCurrentSession().createQuery(hql);
			if(offset != -1)
				q.setFirstResult(offset);
			if(limits != -1)
				q.setMaxResults(limits);
			return q.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Save object into database
	 * @param obj
	 * @return Success or not
	 */
	public boolean save(Object obj) {
		try {
			getSessionFactory().getCurrentSession().save(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	/**
	 * Update object in database
	 * @param obj
	 * @return Success or not
	 */
	public boolean update(Object obj) {
		try {
			getSessionFactory().getCurrentSession().update(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	
	
			/* setters and getters*/
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
}
