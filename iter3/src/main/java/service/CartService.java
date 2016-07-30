package service;

import java.util.List;

import entity.Book;

public interface CartService {
	
	/**
	 * Add item into cart.
	 * If the cart object do not exist in session, then create a new cart.
	 * @param bookID
	 * @param amount
	 * @return success or not.
	 */
	public boolean addItem(int bookID, int amount);
	
	/**
	 * Get all items in cart.
	 * Because the cart only store id of item, so it will query DAO object to get info of item.
	 * The amount information is store in book object.
	 * @return List of book.
	 */
	public List<Book> getItems();
	
	/**
	 * Change amount of item in the cart.
	 * It will return false if the item is not in cart.
	 * The minimum amount of item is 1, so if the origin amount is 1, it will return false.
	 * @return success or not.
	 */
	public boolean changeItemAmount(int bookID, int change);
	
	/**
	 * Remove item in the cart.
	 * It will return false if the item is not in cart.
	 * @return success or not.
	 */
	public boolean removeItem(int bookID);
	
}
