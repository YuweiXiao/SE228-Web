package service;

import java.util.Map;

public interface AnalysisService {
	
	/**
	 * Get all sales data group by day
	 * @return Map
	 */
	public Map<String, Object> salesByDayInfo();
	
	/**
	 * Get all sales data group by month
	 * @return Map
	 */
	public Map<String, Object> salesByMonthInfo();
	
	/**
	 * Get all sales data group by Year
	 * @return Map
	 */
	public Map<String, Object> salesByYearInfo();
	
	/**
	 * Get sales data in given date
	 * @param begin
	 * @param end
	 * @return Map
	 */
	public Map<String, Object> salesByGivenDate(String begin, String end);
	
	/**
	 * Get Books' sold amount data group by category
	 * @return Map
	 */
	public Map<String, Object> soldAmountByCategoryInfo();
	
	/**
	 * Get User behavior: what kind of book the user buy, using book category
	 * @param userID
	 * @return Map
	 */
	public Map<String, Object> getUserBehaviorBookKind(int userID);
	
	/**
	 * Get user behavior: The recent 7 days sales data of the User
	 * @param userID
	 * @return Map
	 */
	public Map<String, Object> getUserBehaviorRecentSales(int userID);
	
}
