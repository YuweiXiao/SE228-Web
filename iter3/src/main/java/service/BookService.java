package service;

import java.util.List;
import entity.Book;
import entity.Category;
import entity.Page;

public interface BookService {
	
	/**
	 * Save book into database
	 * @param book
	 * @return success or not
	 */
	public boolean save(Book book);
	
	/**
	 * Update book in database
	 * @param book
	 * @return success or not
	 */
	public boolean update(Book book);
	
	/**
	 * Delete book in database
	 * @param bookID
	 * @return success or not
	 */
	public boolean delete(int bookID);
	
	/**
	 * Search books whose title is like given title
	 * @param title
	 * @return List of books
	 */
	public List<Book> search(String title);
	
	/**
	 * Get book from database by bookID
	 * @param bookID
	 * @return Book
	 */
	public Book queryByID(int bookID);
	
	/**
	 * Get books under given category name.
	 * @param categoryName
	 * @return List of books.
	 */
	public List<Book> queryByCategory(String categoryName);
	
	/**
	 * Get books using offset and limits
	 * @param page using to pagination, should initialize with currentPage and pageSize
	 * @return List of books.
	 */
	public List<Book> query(Page page);
	
	/**
	 * Get the most polular books. 
	 * The popularity is defined in sold amount.
	 * @return List of books.
	 */
	public List<Book> queryMostPopular();
	
	/**
	 * Get books which go on shelf recently, which is according to id of books
	 * @return
	 */
	public List<Book> queryRecentlyShelve();
	
	/**
	 * Update the image of book which ID is bookID
	 * @param bookID
	 * @param imageFileName
	 * @return success or not
	 */
	public boolean updateImage(int bookID, String imageFileName);
	
	/**
	 * Get all category
	 * @return list of category object
	 */
	public List<Category> allCategory();

	/**
	 * Get category by categoryID
	 * @return Category
	 */
	public Category getCategoryByID(int ID);
}
