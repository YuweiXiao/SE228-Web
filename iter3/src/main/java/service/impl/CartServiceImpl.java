package service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import dao.BookDao;
import entity.Book;
import entity.Cart;
import service.CartService;

@Transactional(readOnly=true)
public class CartServiceImpl implements CartService{

	private Cart cart;
	private BookDao bookDao;
	
	@Override
	public boolean addItem(int bookID, int amount) {
		// get cart object from session or create a cart save into session
		Map session = ActionContext.getContext().getSession();
		if(session.containsKey("cart")) {
			cart = (Cart) session.get("cart");
		} else {
			cart = new Cart();
			session.put("cart", cart);
		}
		
		// add item into session
		return cart.addGoodsInCart(bookID, amount);
	}

	@Override
	public List<Book> getItems() {
		Map session = ActionContext.getContext().getSession();
		if(session.containsKey("cart")) {
			cart = (Cart) session.get("cart");
		} else {
			cart = new Cart();
			session.put("cart", cart);
		}
		HashMap<Integer, Integer> goods = cart.getGoods();
		Set<Integer> keys = goods.keySet();					// get books ID set from cart 
		Iterator<Integer> it = keys.iterator();	
		ArrayList<Book> books = new ArrayList<Book>();		// array store book in cart
		while(it.hasNext())									//get every book info from database
	    {
	    	Integer bookID = it.next();
	    	Book book = bookDao.getBookByID(bookID);
	    	/*book.setCategory(null);*/							//set category to null, avoid json serialization cause hibernate lazy loading
	    	book.setAmount(goods.get(bookID));
	    	books.add(book);
	    }
		//System.out.println(books);
		return books;
	}

	@Override
	public boolean changeItemAmount(int bookID, int change) {
		Map session = ActionContext.getContext().getSession();
		cart = (Cart) session.get("cart");
		if(cart == null){					//cart does not exist, which means change should not happen
			return false;
		}
		return cart.addGoodsInCart(bookID, change);
	}

	@Override
	public boolean removeItem(int bookID) {
		Map session = ActionContext.getContext().getSession();
		cart = (Cart) session.get("cart");
		if(cart == null){
			return false;
		}
		return cart.removeGoodsFromCart(bookID);
	}

	/*getters and setters*/
	
	public BookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}
	
}
