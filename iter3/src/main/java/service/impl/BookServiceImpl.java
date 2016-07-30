package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import dao.BookDao;
import dao.CategoryDao;
import entity.Book;
import entity.Category;
import entity.Page;
import service.BookService;

@Transactional(readOnly=true)
public class BookServiceImpl implements BookService{

	private BookDao bookDao;
	private CategoryDao categoryDao;
	
	@Override
	@Transactional(readOnly=false)
	public boolean save(Book book) {
		return bookDao.save(book);
	}

	@Override
	@Transactional(readOnly=false)
	public boolean update(Book book) {
		return bookDao.update(book);
	}

	@Override
	@Transactional(readOnly=false)
	public boolean delete(int bookID) {
		return bookDao.destroyBook(bookID);
	}

	@Override
	@Transactional(readOnly=false)
	public boolean updateImage(int bookID, String imageFileName) {
		Book book = bookDao.getBookByID(bookID);
		book.setImageID(imageFileName);
		bookDao.update(book);
		return true;
	}
	
	@Override
	public List<Book> search(String title) {
		return bookDao.getBookNameLike(title);
	}

	@Override
	public Book queryByID(int bookID) {
		return bookDao.getBookByID(bookID);
	}

	@Override
	public List<Book> queryByCategory(String categoryName) {
		try {
			Set<Book> b = bookDao.getBooksByCategoryName(categoryName);
			if( b == null)
				return null;
			List<Book> books = new ArrayList<Book>(b);
			return books;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List query(Page page) {
		String hql = "from Book";
		page.setRowCount(bookDao.getTotalBookAmount());
		return bookDao.query(hql, page.getOffset(), page.getPageSize());
	}

	@Override
	public List queryMostPopular() {
		String hql = "from Book a order by a.soldAmount";
		int limits = 5;
		return bookDao.query(hql, -1, limits);
	}

	@Override
	public List queryRecentlyShelve() {
		String hql = "from Book a order by a.bookID desc";
		int limits = 8;
		return bookDao.query(hql, -1, limits);
	}
	
	@Override
	public List allCategory() {
		String hql = "from Category";
		return categoryDao.query(hql, -1, -1);
	}
	
	@Override
	public Category getCategoryByID(int ID) {
		return categoryDao.getCategoryByID(ID);
	}

	
	
			/*getters and setters*/
	public BookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
}
