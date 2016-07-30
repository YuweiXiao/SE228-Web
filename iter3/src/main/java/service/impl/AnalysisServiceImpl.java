package service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import dao.OrderDao;
import service.AnalysisService;

@Transactional(readOnly=true, isolation=Isolation.READ_UNCOMMITTED)
public class AnalysisServiceImpl implements AnalysisService{

	private OrderDao orderDao;
	
	@Override
	public Map<String, Object> salesByDayInfo() {
		List<Object[]> list = orderDao.getTotalBy("%Y-%m-%d"); 
		return generateSalesMap(list);
	}

	@Override
	public Map<String, Object> salesByMonthInfo() {
		List<Object[]> list = orderDao.getTotalBy("%Y-%m"); 
		return generateSalesMap(list);
	}

	@Override
	public Map<String, Object> salesByYearInfo() {
		List<Object[]> list = orderDao.getTotalBy("%Y"); 
		return generateSalesMap(list);
	}

	@Override
	public Map<String, Object> soldAmountByCategoryInfo() {
		List<Object[]> list = orderDao.getSaleAmountByCategory();
		return generateCategoryMap(list);
	}
	
	@Override
	public Map<String, Object> getUserBehaviorBookKind(int userID) {
		List<Object[]> list = orderDao.getUserBehaviorBookKind(userID);
		return generateCategoryMap(list);
	}

	@Override
	public Map<String, Object> getUserBehaviorRecentSales(int userID) {
		List<Object[]> list = orderDao.getUserBehaviorRecentSales(userID);
		return generateSalesMap(list);
	}

	@Override
	public Map<String, Object> salesByGivenDate(String begin, String end) {
		List<Object[]> list = orderDao.getTotalByGivenDate(begin, end);
		return generateSalesMap(list);
	}

					/* private method */
	
	private Map<String, Object> generateCategoryMap(List<Object []> list) {
		List<String> categoryName = new ArrayList<String>();
		List<String> amount = new ArrayList<String>();
		list.stream().forEach((record)->{
			String category = (String)record[0];
			BigDecimal  num = (BigDecimal)record[1];
			categoryName.add(category);
			if(num != null)
				amount.add(num.toString());
			else
				amount.add("0");
		});
		Map<String, Object> t = new HashMap<String, Object>();
		t.put("categoryName", categoryName);
		t.put("amount", amount);
		return t;
	}
	
	private Map<String, Object> generateSalesMap(List<Object[]> list) {
		ArrayList<String> date = new ArrayList<String>();
		ArrayList<String> sales = new ArrayList<String>();
		list.stream().forEach((record)->{
			String d = (String)record[0];
			BigDecimal num = (BigDecimal)record[1];
			date.add(d);
			sales.add(num.toString());
		});
		Map<String, Object> t = new HashMap();
		t.put("date", date);
		t.put("sales", sales);
		return t;
	}
	
			/*getters and setters*/

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	
	
}
