package action;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.json.JSONCleaner;
import org.apache.struts2.json.JSONWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import service.BookService;
import service.CartService;
import util.MongoDBManager;
import util.MyMD5Generator;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import entity.Book;
import entity.Category;
import entity.Page;

public class BookAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4176737865303623314L;
	
	private int bookID, rows = 10, page = 1;
	private Book book;
	private int amount;
	private File myFile;
	private String myFileContentType;
	private String myFileFileName;
	private String categoryName;
	private String category;
	@Autowired
	private MongoDBManager mongo;
	
	private Map<String,Object> dataMap = new HashMap<String, Object>();		// ajax data
	
	@Autowired
	private BookService bookService;
	
	/**
	 * show book of given bookID
	 * @return String SUCCESS
	 */
	public String showBookByID() {
		ActionContext.getContext().put("book", bookService.queryByID(bookID));
//		System.out.println(bookID);
		return SUCCESS;
	}
	
	public String getBookInfoByID() {
		dataMap.clear();
		dataMap.put("book", bookService.queryByID(bookID));
		return "json";
	}
	
	
	/**
	 * show book by category
	 * @return String list_item
	 */
	public String listBookByCategory() {
		ActionContext.getContext().put("books", bookService.queryByCategory(categoryName));
		ActionContext.getContext().put("path_nav", "分类："+categoryName);
		return "list_item";
	}
	
	/**
	 * get all books using offset and limits
	 * @return String
	 */
	public String queryBooks() {
		System.out.println("in query book");
		Page p = new Page(page, rows);
		dataMap.clear();
		dataMap.put("rows", bookService.query(p));
		dataMap.put("total", p.getRowCount());
		dataMap.put("success", true);
		System.out.println(dataMap);
		return "json";
	}

	public String showCreateBookView() {
		List<Category> category = bookService.allCategory();
		ActionContext.getContext().put("category", category);
		return SUCCESS;
	}
	
	/**
	 * insert book into database.
	 * @return String
	 */
	public String saveBook() {
		try {
			DB db = mongo.getDB();
			GridFS gfsPhoto = new GridFS(db, "image");
			GridFSInputFile gfsFile = gfsPhoto.createFile(myFile);		// get image file from local drive
			
			Date dt = new Date();
			String newFileName = book.getTitle() + "-" + dt.getTime() + myFileFileName;
			String filenameMD5 = MyMD5Generator.stringMD5(newFileName);
			gfsFile.setFilename(filenameMD5);							// set a new filename for identify purpose
			gfsFile.save();												// save the image file into mongoDB
			
			book.setImageID(filenameMD5);
			
			String[] categoryArr = category.split(",");
			Set<Category> categories = new HashSet<Category>();
			for(int i = 0; i < categoryArr.length; ++i)
			{
				categoryArr[i] = categoryArr[i].trim();
				System.out.println(categoryArr[i]);
				categories.add(bookService.getCategoryByID(Integer.parseInt(categoryArr[i])));
			}
			book.setCategory(categories);
			System.out.println(book.getCategory());
			bookService.save(book);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list_book";
	}
	
	/**
	 * update book in database.
	 * @return String
	 */
	public String updateBook() {
		book.setBookID(bookID);
		dataMap.clear();
		dataMap.put("success", bookService.update(book));
		return "json";
	}
	
	/**
	 * delete book in database.
	 * @return String
	 */
	public String destroyBook() {
		dataMap.clear();
		dataMap.put("success", bookService.delete(bookID));
		return "json";
	}
	
	
			/*	geters and setters  */
	
	public int getAmount() {
		return amount;
	}

	public BookService getBookService() {
		return bookService;
	}

	public File getMyFile() {
		return myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public MongoDBManager getMongo() {
		return mongo;
	}

	public void setMongo(MongoDBManager mongo) {
		this.mongo = mongo;
	}

	public String getMyFileContentType() {
		return myFileContentType;
	}

	public void setMyFileContentType(String myFileContentType) {
		this.myFileContentType = myFileContentType;
	}

	public String getMyFileFileName() {
		return myFileFileName;
	}

	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	} 
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
}
