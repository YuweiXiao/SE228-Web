package action;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jdk.nashorn.internal.codegen.CompilerConstants.Call;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.BookDao;
import dao.OrderDao;
import entity.Order;

public class IndexAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7072973863541486492L;
	private Map<String,Object> dataMap = new HashMap<String, Object>();

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * 
	 */
	public String index(){
//		System.out.println("in index\n");
		BookDao bookDao = new BookDao();
		List books = bookDao.getMostPopularBook();
		ActionContext.getContext().put("books", books);
		books = bookDao.getRecentNewBook();
		ActionContext.getContext().put("newbooks", books);
//		System.out.println(books);
		return SUCCESS;
	}

	public String manageUser() {
		ActionContext.getContext().put("title", "manage User");
		return "user";
	}
	
	public String manageBook() {
		ActionContext.getContext().put("title", "manage Book");
		return "book";
	}
	
	public String manageOrder() {
		try {
			OrderDao orderDao = new OrderDao();
			List<Order> orders = orderDao.getOrders(1, 1);
			ActionContext.getContext().put("orders", orders);
			System.out.println(orders);
			return "order";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}
	
	public String getSalesByDay() throws ParseException {
		OrderDao orderDao = new OrderDao();
		List orders = orderDao.getOrders(-1, -1);
		HashMap<String, Integer> salesMap = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Long minDay = ((Order)orders.get(0)).getOrderTime().getTime();
		
		for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
			Order order = (Order) iterator.next();
			Long t = order.getOrderTime().getTime();
			if(t < minDay )
				minDay = t;
			String d = sdf.format(t);
			int old = 0;
			if( salesMap.containsKey(d) )
				old = salesMap.get(d);
			salesMap.put(d, old + order.getTotalPrice());
		}
		System.out.println(salesMap);
		List date = new ArrayList<String>();
		List sales = new ArrayList<>();
		Long nowDay = minDay;
		long oneDay = 1*24*60*60*1000;
		Long maxDay = sdf.parse(sdf.format((new Date()).getTime() + oneDay)).getTime();
		while(nowDay < maxDay) {
			
			String d = sdf.format(nowDay);
			System.out.println(d);
			int s = 0;
			if( salesMap.containsKey(d) )
				s = salesMap.get(d);
			date.add(d);
			sales.add(s);
			nowDay += oneDay;
		}
		dataMap.clear();
		dataMap.put("date", date);
		dataMap.put("sales", sales);
		return "ajax";
	}
	
	public String getSalesByMonth() throws ParseException {
		// get orders
		OrderDao orderDao = new OrderDao();
		List orders = orderDao.getOrders(-1, -1);
		
		// map order in month
		HashMap<String, Integer> salesMap = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Timestamp minDate = ((Order)orders.get(0)).getOrderTime();
		for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
			Order order = (Order) iterator.next();
			Timestamp t = order.getOrderTime();
			if(t.before(minDate))
				minDate = t;
			String d = sdf.format(t.getTime());
			int old = 0;
			if( salesMap.containsKey(d) )
				old = salesMap.get(d);
			salesMap.put(d, old + order.getTotalPrice());
		}

		// get the data from map
		List date = new ArrayList<String>();
		List sales = new ArrayList<>();
		Timestamp nowDate = minDate;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		Timestamp maxDate = new Timestamp(sdf.parse(sdf.format(cal.getTime())).getTime());
		while(nowDate.before(maxDate)) {
			String d = sdf.format(nowDate.getTime());
			System.out.println(d);
			int s = 0;
			if( salesMap.containsKey(d) )
				s = salesMap.get(d);
			date.add(d);
			sales.add(s);
			cal.setTime(nowDate);
			cal.add(Calendar.MONTH, 1);
			nowDate.setTime(cal.getTime().getTime());
		}
		dataMap.clear();
		dataMap.put("date", date);
		dataMap.put("sales", sales);
		return "ajax";
	}
	
	public String getSalesByYear() throws ParseException {
		// get orders
		OrderDao orderDao = new OrderDao();
		List orders = orderDao.getOrders(-1, -1);
		
		// map order in month
		HashMap<String, Integer> salesMap = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Timestamp minDate = ((Order)orders.get(0)).getOrderTime();
		for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
			Order order = (Order) iterator.next();
			Timestamp t = order.getOrderTime();
			if(t.before(minDate))
				minDate = t;
			String d = sdf.format(t.getTime());
			int old = 0;
			if( salesMap.containsKey(d) )
				old = salesMap.get(d);
			salesMap.put(d, old + order.getTotalPrice());
		}

		// get the data from map
		List date = new ArrayList<String>();
		List sales = new ArrayList<>();
		Timestamp nowDate = minDate;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		Timestamp maxDate = new Timestamp(sdf.parse(sdf.format(cal.getTime())).getTime());
		while(nowDate.before(maxDate)) {
			String d = sdf.format(nowDate.getTime());
			System.out.println(d);
			int s = 0;
			if( salesMap.containsKey(d) )
				s = salesMap.get(d);
			date.add(d);
			sales.add(s);
			cal.setTime(nowDate);
			cal.add(Calendar.YEAR, 1);
			nowDate.setTime(cal.getTime().getTime());
		}
		dataMap.clear();
		dataMap.put("date", date);
		dataMap.put("sales", sales);
		return "ajax";
	}
	
	public String categoryAnalysis() {
		OrderDao orderDao = new OrderDao();
		dataMap = orderDao.getSaleAmountByCategory();
		return "ajax";
	}
}
