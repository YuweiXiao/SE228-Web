package dao;

import java.util.Map;

import org.junit.Test;

public class testOrderDao {
	@Test
	public void testGetSaleAmountByCategory()
	{
		OrderDao dao = new OrderDao();
		Map map = dao.getSaleAmountByCategory();
		System.out.println(map);
	}
	
	@Test
	public void testGetTotalPriceBy() {
		OrderDao dao = new OrderDao();
		System.out.println(dao.getTotalBy("%Y-%m-%d"));
	}
}
