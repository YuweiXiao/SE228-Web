package service;

import java.util.List;

import entity.Order;
import entity.Page;

public interface OrderService {

	/**
	 * Create a new order using user info and cart info in session 
	 * and save the new order into database.
	 * Meanwhile, deduct inventory of books, the order state will record as unpaied.
	 * @return new order object created. 
	 */
	public Order create();
	
	/**
	 * Delete order using orderID.
	 * @param orderID
	 * @return success or not
	 */
	public boolean delete(int orderID);
	
	/**
	 * Colse the transaction of order.
	 * Change the state of order to close.
	 * Change inventory of books.
	 * @param orderID : id of order
	 * @return success or not
	 */
	public boolean colse(int orderID);
	
	/**
	 * Pay for the order.
	 * Transfer money and increase the sold amount of books.
	 * Change the state of order to paied.
	 * @param orderID : id of order.
	 * @return success or not.
	 */
	public boolean pay(int orderID);
	
	/**
	 * Get users of according to page.
	 * User info is obtained from session.
	 * @param page using to pagination, should initialize with currentPage and pageSize
	 * @return List of order
	 */
	public List<Order> queryByUser(Page page);
	
	/**
	 * Get orders from database.
	 * @param page using to pagination, should initialize with currentPage and pageSize
	 * @return List of order
	 */
	public List<Order> query(Page page);
}
