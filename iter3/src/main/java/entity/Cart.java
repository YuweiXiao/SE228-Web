package entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.BookDao;

public class Cart {
	
	private HashMap<Integer, Integer> goods;
	
	@Override
	public String toString() {
		return "Cart [goods=" + goods + ", totalPrice=" + totalPrice + "]";
	}

	private int totalPrice;
	
	public Cart()
	{
		goods = new HashMap<Integer, Integer>();
		totalPrice = 0;
	}
	
	
	public HashMap<Integer, Integer> getGoods() {
		return goods;
	}

	public double getTotalPrice() {
		calTotalPrice();
		return totalPrice/100;
	}

	public boolean addGoodsInCart(int bookID ,int amount)
	{
		if(goods.containsKey(bookID))
		{
			int oldAmount = goods.get(bookID);
			if(oldAmount + amount > 0)	// the total amount of a certain item should > 0 
				goods.put(bookID, oldAmount+amount);
			else
				return false;
		}
		else
			goods.put(bookID, amount);	
		return true;
	}
	
	public boolean removeGoodsFromCart(int bookID)
	{
		goods.remove(bookID);
		return true;
	}
	
	public double calTotalPrice()
	{
		int sum = 0;
		BookDao bookDao = getBookDaoBean();
		Set<Integer> keys = goods.keySet(); 
		Iterator<Integer> it = keys.iterator(); 
	    while(it.hasNext())
	    {
	    	Integer bookID = it.next();
	    	Book book = bookDao.getBookByID(bookID);
	    	sum += book.getPrice()* goods.get(bookID);
	    }
	    totalPrice = sum; 
	    return sum;
	}
	
	public Order createOrder(Order order) {
		int sum = 0;
		BookDao bookDao = getBookDaoBean();
		Iterator iter = goods.keySet().iterator();
		while(iter.hasNext()) {
			Integer bookID = (Integer) iter.next();
			Book book = bookDao.getBookByID(bookID);
			sum += book.getPrice() * goods.get(bookID);
			OrdersBook ob = new OrdersBook(book, goods.get(bookID));
			order.getBooks().add(ob);
		}
		order.setTotalPrice(sum);
		System.out.println("in cart entity. create Order finished");
		return order;
	}
	
	private BookDao getBookDaoBean() {
		ApplicationContext	ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		return ctx.getBean(BookDao.class);
	}
	
}
