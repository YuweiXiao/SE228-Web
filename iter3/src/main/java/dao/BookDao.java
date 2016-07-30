package dao;

import java.util.List;
import java.util.Set;
import entity.Book;
import entity.Category;

public class BookDao extends BaseDao{

	/**
	 * constructor from super class
	 */
	public BookDao() {
		super();
	}
	
	/**
	 * 通过title获得书本
	 * @param  title String, title of book
	 * @return       list of book which book name contain title
	 * @throws Exception 
	 */
	public List<Book> getBookNameLike(String title) {
		try {
			String hql = "from Book a where a.title like ?";
			List books = getSessionFactory().getCurrentSession().createQuery(hql).setParameter(0, "%"+title+"%").list();
			if(books.size() > 0)
				return books;
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * get Set of books that belongs to given category name
	 * @param name the name of category
	 * @return set of books that belongs to the category
	 * @throws Exception 
	 */
	public Set<Book> getBooksByCategoryName(String name) {
		try {
			String hql = "select c from Category c join fetch c.books  where c.name = ?";
			List rs = getSessionFactory().getCurrentSession().createQuery(hql).setParameter(0, name).list();
			if(rs.size() == 0)
				return null;
			Set books = ((Category)rs.get(0)).getBooks();
			return books;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * get book using bookID
	 * @param bookID 
	 * @return the Book that bookID eaqul to given ID  
	 * @throws Exception 
	 */
	public Book getBookByID(Integer bookID) {
		try {
			String hql = "from Book where bookID = ?";
			List list = getSessionFactory().getCurrentSession().createQuery(hql).setParameter(0, bookID).list();
			if(list.size() == 1)
				return (Book)list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * get total amount of books
	 * @return
	 * @throws Exception 
	 */
	public Integer getTotalBookAmount() {
		try {
			String hql = "select count(*) from Book";
			Long num = (Long) getSessionFactory().getCurrentSession().createQuery(hql).list().get(0);
			return num.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * delete book in database using given bookID
	 * @param bookID
	 * @return
	 */
	public boolean destroyBook(int bookID) {
		try {
			Book book = (Book) getSessionFactory().getCurrentSession().load(Book.class, bookID);
			getSessionFactory().getCurrentSession().delete(book);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * insert book into databse
	 * @param book
	 * @return true: success
	 * 		   flase: fail
	 */
//	public boolean saveBook(Book book) {
//		try {
//			getSessionFactory().getCurrentSession().save(book);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
	
	/**
	 * update book in databse
	 * @param book
	 * @return true: success
	 * 		   flase: fail
	 */
//	public boolean updateBook(Book book) {
//		try {
//			getSessionFactory().getCurrentSession().update(book);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		return false;
//	}
}
