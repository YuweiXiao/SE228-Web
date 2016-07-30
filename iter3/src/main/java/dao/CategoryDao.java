package dao;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import entity.Category;

public class CategoryDao extends BaseDao{
	
	/**
	 * Get category object using categoryID
	 * @param categoryID
	 * @return Category
	 */
	public Category getCategoryByID(int categoryID) {
		try {
			String hql = "from Category where categoryID = ?";
			List list = getSessionFactory().getCurrentSession().createQuery(hql).setParameter(0, categoryID).list();
			if(list.size() == 1)
				return (Category)list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public List<Category> getAllCategory() {
//		try {
//			String hql = "from Category";
//			List list = getSessionFactory().getCurrentSession().createQuery(hql).list();
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
}
