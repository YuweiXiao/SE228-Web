package entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class Order {
	private int orderID;
	private User user;
	private Timestamp orderTime;
	private int totalPrice;
	private char isDeal;
	//private Map<Book, OrdersBook> books;
//	private Set<OrderAssociation> books = new HashSet<OrderAssociation>(0);
	private Set<OrdersBook> books = new HashSet<OrdersBook>(0);
	
	
	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", user=" + user + ", orderTime="
				+ orderTime + ", isDeal=" + isDeal + ", books=" + books + "]";
	}

	public Order() {

	}

//	public Order(int orderID, User user, Timestamp orderTime, char isDeal,
//			Map books) {
//		super();
//		this.orderID = orderID;
//		this.user = user;
//		this.orderTime = orderTime;
//		this.isDeal = isDeal;
//		this.books = books;
//	}
	
	

	public int getOrderID() {
		return orderID;
	}
	public Order(int orderID, User user, Timestamp orderTime, char isDeal,
		Set<OrdersBook> books) {
	super();
	this.orderID = orderID;
	this.user = user;
	this.orderTime = orderTime;
	this.isDeal = isDeal;
	this.books = books;
}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Timestamp getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}
	public char getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(char isDeal) {
		this.isDeal = isDeal;
	}

	public Set<OrdersBook> getBooks() {
		return books;
	}

	public void setBooks(Set<OrdersBook> books) {
		this.books = books;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

//	public Set<OrderAssociation> getBooks() {
//		return books;
//	}
//
//	public void setBooks(Set<OrderAssociation> books) {
//		this.books = books;
//	}

	
}


