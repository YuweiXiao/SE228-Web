package action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import service.CartService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CartAction extends ActionSupport{

	private Map<String,Object> dataMap = new HashMap<String, Object>();		// ajax data
	private int bookID, amount;
	@Autowired
	private CartService cartService;
	
	/**
	 * add book into cart
	 * @return String
	 */
	public String add() {
		dataMap.clear(); 
		dataMap.put("success", cartService.addItem(bookID, amount));
		return "json";
	}
	
	/**
	 * get cart info from session
	 * @return
	 */
	public String itemsInfo() {
	    dataMap.clear();
	    dataMap.put("cart", cartService.getItems());
		return "json";
	}
	
	/**
	 * change amount of book in cart, change can be negative, which mean remove book
	 * @return
	 */
	public String changeAmount() {
		int change = Integer.parseInt(((String [])ActionContext.getContext().getParameters().get("change"))[0]);  // get change from context
		dataMap.clear();
		dataMap.put("success", cartService.changeItemAmount(bookID, change));
		return "json";
	}
	
	/**
	 * delete certain book in cart.
	 * @return
	 */
	public String removeItem() {
		dataMap.clear();
		dataMap.put("success", cartService.removeItem(bookID));
		return "json";
	}

			/*getters and setters*/
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public CartService getCartService() {
		return cartService;
	}

	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}
	
	
	
}
