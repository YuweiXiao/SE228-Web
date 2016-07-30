package dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import entity.Role;

public class RoleDao extends BaseDao{
	
	/**
	 * get role named user
	 * @return Role
	 */
	public Role getUserRole() {
		try {
			return getRoleByName("user");
//			String hql = "from Role where name = ?";
//			List list = getSessionFactory().getCurrentSession().createQuery(hql).setParameter(0, "user").list();
//			if( list.size() > 0 )
//				return (Role) list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get role according to name.
	 * @param name
	 * @return Role
	 */
	public Role getRoleByName(String name) {
		if(name == null)
			name = "user";
		try {
			String hql = "from Role where name = ?";
			List list = getSessionFactory().getCurrentSession().createQuery(hql).setParameter(0, name).list();
			if( list.size() > 0 )
				return (Role) list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
