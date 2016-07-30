package action;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import service.AnalysisService;
import com.opensymphony.xwork2.ActionSupport;

public class AnalysisAction extends ActionSupport{

	private Map<String,Object> dataMap = new HashMap<String, Object>();

	private String beginDate;
	private String endDate;
	private int userID;
	
	@Autowired
	private AnalysisService analysisService;
	
	public String showUserBehavior() {
		//[TODO]
		return SUCCESS;
	}
	
	/**
	 * get order sales amount group by day
	 * @return String
	 * @throws ParseException
	 */
	public String getSalesByDay() throws ParseException {
		dataMap = analysisService.salesByDayInfo();
		return "json";
	}

	/**
	 * get order sales amount group by month
	 * @return String
	 * @throws ParseException
	 */
	public String getSalesByMonth() throws ParseException {
		dataMap = analysisService.salesByMonthInfo();
		return "json";
	}
	
	/**
	 * get order sales amount group by year
	 * @return String
	 * @throws ParseException
	 */
	public String getSalesByYear() throws ParseException {
		dataMap = analysisService.salesByYearInfo();
		return "json";
	}
	
	/**
	 * get sold amount data group by category
	 * @return String
	 */
	public String categoryAnalysis() {
		dataMap = analysisService.soldAmountByCategoryInfo();
		return "json";
	}

	public String getSalesByGivenDate() {
		dataMap = analysisService.salesByGivenDate(beginDate, endDate);
		return "json";
	}
	
	public String getUserBehaviorOfBookCategory() {
		dataMap = analysisService.getUserBehaviorBookKind(userID);
		return "json";
	}
	
	public String getUserRecentSales() {
		dataMap = analysisService.getUserBehaviorRecentSales(userID);
		return "json";
	}
	
			/*getters and setters*/
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public AnalysisService getAnalysisService() {
		return analysisService;
	}

	public void setAnalysisService(AnalysisService analysisService) {
		this.analysisService = analysisService;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	
	
	
	
}
