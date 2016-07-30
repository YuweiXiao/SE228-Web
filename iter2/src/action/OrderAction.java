package action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.BookDao;
import dao.OrderDao;
import entity.Book;
import entity.Cart;
import entity.Order;
import entity.OrdersBook;
import entity.User;

public class OrderAction extends ActionSupport{
	
	private Map<String,Object> dataMap = new HashMap<String, Object>();
	private int orderID;
	private int bookID;
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public String createOrder() {
		try {
			System.out.println("in create order");
			// get session
			Map session = ActionContext.getContext().getSession();
			
			//order's basic info
			Order order = new Order();
			order.setUser((User) session.get("user"));
			order.setIsDeal('N');
			
			//create order through cart
			Cart cart = (Cart) ActionContext.getContext().getSession().get("cart");
			order = cart.createOrder(order);
			System.out.println(order);
			OrderDao orderDao = new OrderDao();
			orderDao.saveOrder(order);
			ActionContext.getContext().put("totalPrice", order.getTotalPrice());
			ActionContext.getContext().put("orderID", order.getOrderID());
			ActionContext.getContext().put("books", order.getBooks());
			session.remove("cart");
			
			return "order_create_success";
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "fail";
	}
	
	public String closeOrder() {
		OrderDao orderDao = new OrderDao();
		orderDao.changeStatus(orderID, 'C');
		return "action_success";
	}
	
	public String pay() {
		OrderDao orderDao = new OrderDao();
		orderDao.changeStatus(orderID, 'Y');
		return "action_success";
	}
	
	public String myAllOrders(){
		try {
			User user = (User) ActionContext.getContext().getSession().get("user");
			OrderDao orderDao = new OrderDao();
			List<Order> orders = orderDao.getUserOrders(user.getUserID());
			System.out.println("what?");
			//System.out.println(orders);
			ActionContext.getContext().put("orders", orders);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
	public String destoryOrdersBook(){
		OrderDao orderDao = new OrderDao();
		boolean t = orderDao.destroyOrdersBook(orderID, bookID);
//		boolean t = true;
		System.out.println("hello");
		System.out.println(t);
		if(t) {
			dataMap.clear();
			dataMap.put("success", true);
			return SUCCESS;
		}
		else
			return "fail";
	}
	
	public String destroyOrder() {
		OrderDao orderDao = new OrderDao();
		boolean t = orderDao.destroyOrder(orderID);
//		boolean t = true;
		System.out.println("hello");
		System.out.println(t);
		if(t) {
			dataMap.clear();
			dataMap.put("success", true);
			return SUCCESS;
		}
		else
			return "fail";
	}
	
	public String test() {
		ActionContext.getContext().put("totalPrice", "123");
		ActionContext.getContext().put("orderID", "1237687123");
		return "order_create_success";
	}
}
