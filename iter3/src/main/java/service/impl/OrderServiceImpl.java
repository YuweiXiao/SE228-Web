package service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import dao.OrderDao;
import entity.Cart;
import entity.Order;
import entity.Page;
import entity.User;
import service.OrderService;

@Transactional(readOnly=true)
public class OrderServiceImpl implements OrderService{

	private OrderDao orderDao;
	
	@Override
	@Transactional(readOnly=false)
	public Order create() {
		Map session = ActionContext.getContext().getSession();		// get session
		
		//set order's basic info
		Order order = new Order();
		order.setUser((User) session.get("user"));
		order.setIsDeal('N');
		
		//create order through cart
		Cart cart = (Cart) ActionContext.getContext().getSession().get("cart");
		order = cart.createOrder(order);
		if( orderDao.save(order) )
		{
			session.remove("cart");		// clear cart
			return order;
		}
		return null;
	}

	@Override
	@Transactional(readOnly=false)
	public boolean delete(int orderID) {
		return orderDao.destroyOrder(orderID);
	}

	@Override
	@Transactional(readOnly=false)
	public boolean colse(int orderID) {
		User user = (User) ActionContext.getContext().getSession().get("user");
		Order order = orderDao.getOrderByID(orderID);
		if(order.getUser().getUserID() != user.getUserID())
			return false;
		order.setIsDeal('C');
		orderDao.update(order);
		return true;
	}

	@Override
	@Transactional(readOnly=false)
	public boolean pay(int orderID) {
		User user = (User) ActionContext.getContext().getSession().get("user");
		Order order = orderDao.getOrderByID(orderID);
		if(order.getUser().getUserID() != user.getUserID())
			return false;
		order.setIsDeal('P');
		orderDao.update(order);
		return true;
	}

	@Override
	public List<Order> queryByUser(Page page) {
		User user = (User) ActionContext.getContext().getSession().get("user");
		page.setRowCount(orderDao.getUserOrderAmount(user.getUserID()));
		List<Order> orders = orderDao.getOrders(page.getOffset(), page.getPageSize(), user.getUserID());
		return orders;
	}

	@Override
	public List<Order> query(Page page) {
		page.setRowCount(orderDao.getTotalOrderAmount());
		List<Order> orders = orderDao.getOrders(page.getOffset(), page.getPageSize(), -1);
		return orders;
	}

			/*getters and setters*/
	
	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	
	
}
