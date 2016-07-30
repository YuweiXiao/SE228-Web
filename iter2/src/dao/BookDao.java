package dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import db.HibernateSessionFactory;
import entity.Book;
import entity.Category;

public class BookDao {

	
	public List<Book> getMostPopularBook() {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			String hql = "from Book a order by a.soldAmount";
			session.beginTransaction();
			List books = session.createQuery(hql).setMaxResults(6).list();
			session.getTransaction().commit();
//			System.out.println(books);
			return books;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		return null; 
	}
	
	public List<Book> getBookNameLike(String title){
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		String hql = "";
		try {
			hql = "from Book a where a.title like ?";
			session.beginTransaction();
			List books = session.createQuery(hql).setParameter(0, "%"+title+"%").list();
			session.getTransaction().commit();
//			System.out.println(books);
			if(books.size()>0)
				return books;
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		return null;
	}
	
	public List<Book> getRecentNewBook() {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			String hql = "from Book a order by a.bookID desc";
			session.beginTransaction();
			List books = session.createQuery(hql).setMaxResults(8).list();
			session.getTransaction().commit();
//			System.out.println(books);
			return books;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		return null; 
	}
	
	public Set<Book> getBooksByCategoryName(String name) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		String hql = "";
		try {
			hql = "from Category where name=:name";
			session.beginTransaction();
			List rs = session.createQuery(hql).setParameter("name", name).list();
			if(rs.size() == 0)
				return null;
			Set books = ((Category)rs.get(0)).getBooks();
			session.getTransaction().commit();
			return books;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		return null; 
	}
	
	public Book getBookByID(Integer bookID){
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			
			String hql = "from Book where bookID = ?";
			session.beginTransaction();
			List list = session.createQuery(hql).setParameter(0, bookID).list();
			session.getTransaction().commit();
			if(list.size() == 1)
				return (Book)list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		return null;
	}
	
	public List<Book> getBooks(int offset , int limits) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			String hql = "from Book";
			session.beginTransaction();
			List books = session.createQuery(hql).list();
			session.getTransaction().commit();
			return books;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getTotalBookAmount() {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			String hql = "select count(*) from Book";
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
	
	public boolean saveBook(Book book) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.save(book);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return false;
		
	}
	
	public boolean updateBook(Book book) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.update(book);
			session.getTransaction().commit();
			System.out.println("here we are");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		return false;
	}
	
	public boolean destroyBook(int bookID) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Book book = (Book) session.load(Book.class, bookID);
			session.delete(book);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}
}
