package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.BookDao;
import entity.Book;
import entity.Cart;

public class BookAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4176737865303623314L;
	
	private int bookID, rows, page;
	private Book book;
	private String title;
	private int amount;
	private String categoryName;
	private Map<String,Object> dataMap = new HashMap<String, Object>();
	
	
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String search() {
		BookDao bookDao = new BookDao();
		List books = bookDao.getBookNameLike(title);
		ActionContext.getContext().put("books", books);
		ActionContext.getContext().put("path_nav", "ËÑË÷:" + title);
		return "list_item";
	}
	
	public String showBookByID() {
//		System.out.println(bookID);
//		//System.out.println(ActionContext.getContext().getParameters().get("bookID"));
//		String []bookID = (String[]) ActionContext.getContext().getParameters().get("bookID");
		BookDao bookDao = new BookDao();
		Book book = bookDao.getBookByID(bookID);
//		System.out.println(book);
		ActionContext.getContext().put("book", book);
		return SUCCESS;
	}
	
	public String listBookByCategory() {
		System.out.println("here");
		BookDao bookDao = new BookDao();
		Set<Book> books = bookDao.getBooksByCategoryName(categoryName);
		ActionContext.getContext().put("books", books);
		ActionContext.getContext().put("path_nav", "·ÖÀà£º"+categoryName);
		return "list_item";
	}
	
	public String allBooks() {
		HttpServletRequest request = ServletActionContext.getRequest();
//		String page = request.getParameter("page").toString();
//		int page_num = page == null ? 1 : Integer.parseInt(page);
//		String row = request.getParameter("rows").toString();
//		int row_num = row==null ? 10 : Integer.parseInt(row);
//		int offset = (page_num-1)*row_num;
		
		
		BookDao bookDao = new BookDao();
		dataMap.clear();
		dataMap.put("total", bookDao.getTotalBookAmount());
		dataMap.put("rows", bookDao.getBooks(1, 1));
		dataMap.put("success", true);
		return SUCCESS;
	}
	
	public String addToCart() {
		// get cart object from session or create a cart save into session
		Map session = ActionContext.getContext().getSession();
		Cart cart;
		if(session.containsKey("cart")) {
			cart = (Cart) session.get("cart");
		} else {
			cart = new Cart();
			session.put("cart", cart);
		}
		
		// add item into session
		cart.addGoodsInCart(bookID, amount);
		System.out.println(cart);
		System.out.println(cart.getTotalPrice());
		
		// ajax data 
		dataMap.clear();
		dataMap.put("success", true);
		return "ajax";
	}
	
	public String cartInfo() {
		Map session = ActionContext.getContext().getSession();
		Cart cart;
		if(session.containsKey("cart")) {
			cart = (Cart) session.get("cart");
		} else {
			cart = new Cart();
			session.put("cart", cart);
		}
		HashMap<Integer, Integer> goods = cart.getGoods();
		BookDao bookDao = new BookDao();
		Set<Integer> keys = goods.keySet(); 
		Iterator<Integer> it = keys.iterator();
		ArrayList<Book> books = new ArrayList<Book>();
		while(it.hasNext())
	    {
	    	Integer bookID = it.next();
	    	Book book = bookDao.getBookByID(bookID);
	    	book.setAmount(goods.get(bookID));
	    	books.add(book);
	    }
	    dataMap.clear();
	    dataMap.put("cart", books);
		return "ajax";
	}
	
	public String changeAmountInCart() {
		dataMap.clear();
		Map session = ActionContext.getContext().getSession();
		Cart cart = (Cart) session.get("cart");
		if(cart == null){
			dataMap.put("success", false);
			return "ajax";
		}
		int change = Integer.parseInt(((String [])ActionContext.getContext().getParameters().get("change"))[0]);
		cart.addGoodsInCart(bookID, change);
		dataMap.put("success", true);
		return "ajax";
	}
	
	public String removeBookInCart() {
		dataMap.clear();
		Map session = ActionContext.getContext().getSession();
		Cart cart = (Cart) session.get("cart");
		if(cart == null){
			dataMap.put("success", false);
			return "ajax";
		}
		cart.removeGoodsFromCart(bookID);
		dataMap.put("success", true);
		return "ajax";
	}
	
	public String saveBook() {
		BookDao bookDao= new BookDao();
		bookDao.saveBook(book);
		dataMap.clear();
		dataMap.put("success", true);
		return SUCCESS;
	}
	
	public String updateBook() {
		book.setBookID(bookID);
		BookDao bookDao = new BookDao();
		bookDao.updateBook(book);
		dataMap.clear();
		dataMap.put("success", true);
		return SUCCESS;
	}
	
	public String destroyBook() {
		BookDao bookDao = new BookDao();
		bookDao.destroyBook(bookID);
		dataMap.clear();
		dataMap.put("success", true);
		return SUCCESS;
	}
}
