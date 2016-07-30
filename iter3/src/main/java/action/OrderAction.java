package action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import service.OrderService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import entity.Order;
import entity.Page;

public class OrderAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1157338456692766935L;
	private Map<String,Object> dataMap = new HashMap<String, Object>();
	private int orderID;
	private int pageno = 1, rows = 6;
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * create a new order and save it into database
	 * @return String
	 */
	public String createOrder() {
		Order order = orderService.create();
		if(order != null) {
			ActionContext.getContext().put("totalPrice", order.getTotalPrice());
			ActionContext.getContext().put("orderID", order.getOrderID());
			ActionContext.getContext().put("books", order.getBooks());
			return "order_create_success";
		}
		return "fail";
	}
	
	/**
	 * change order status to close.
	 * @return String
	 */
	public String closeOrder() {
		if(orderService.colse(orderID))
			return SUCCESS;
		return "fail";
	}
	
	/**
	 * pay for order and change the status of order
	 * @return String
	 */
	public String pay() {
		// no business logic here about payment
		if(orderService.pay(orderID))
			return SUCCESS;
		return "fail";
	}
	
	/**
	 * List User orders using offset and limits
	 * @return String list_order
	 */
	public String listUserOrder(){
		Page page = new Page(pageno, rows);
		ActionContext.getContext().put("orders", orderService.queryByUser(page));
		ActionContext.getContext().put("page", page);
		System.out.println("end of listuserorder");
		return "list_order";
	}

	/**
	 * get orders info from database for order management page.
	 * @return String
	 */
	public String listOrder() {
		System.out.println("list order");
		try {
			Page page = new Page(pageno, rows);
			ActionContext.getContext().put("orders", orderService.query(page));
			ActionContext.getContext().put("page", page);
			return "list_order";
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("fail");
		return "fail";
	}
	
	/**
	 * remove order
	 * @return String
	 */
	public String destroyOrder() {
		dataMap.clear();
		dataMap.put("success", orderService.delete(orderID));
		return "json";
	}
	
			/*getters and setters*/
	
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

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public int getPageno() {
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
}
