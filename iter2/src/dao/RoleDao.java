package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import db.HibernateSessionFactory;
import entity.Role;
import entity.User;

public class RoleDao {
	public Role getUserRole() {
		String hql = "";
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			hql = "from Role where name=:name";
			Query query = session.createQuery(hql);
			query.setParameter("name", "user");
			List<Role> list = query.list();
			session.getTransaction().commit();
			if( list.size() > 0 )
				return list.get(0);
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
	}
	
	public Role getRoleByName(String name) {
		if(name == null)
			return getUserRole();
		String hql = "";
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			hql = "from Role where name=:name";
			Query query = session.createQuery(hql);
			query.setParameter("name", name);
			List<Role> list = query.list();
			session.getTransaction().commit();
			if( list.size() > 0 )
				return list.get(0);
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
	}
}
