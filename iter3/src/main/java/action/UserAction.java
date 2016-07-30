package action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import entity.Page;
import entity.User;

public class UserAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6394249815349492176L;
	private Map<String,Object> dataMap = new HashMap<String, Object>();
	private User user;
	private int userID, rows = 10, page = 1;
	@Autowired
	private UserService userService;

	/**
	 * Get user profile load user profile view 
	 * @return SUCCESS
	 */
	public String showUserProfile() {
		ActionContext.getContext().put("path_nav", "个人中心");
		User user = (User)ActionContext.getContext().getSession().get("user");
		ActionContext.getContext().put("user", userService.getUserProfile(user));
//		System.out.println(ActionContext.getContext().getSession().get("user"));
		return SUCCESS;
	}
	
	public String updateProfile() {
		User u = (User) ActionContext.getContext().getSession().get("user");
		user.setUserID(u.getUserID());
		userService.updateUserProfile(user);
		return SUCCESS;
	}
	
	/**
	 * get all users
	 * @return String
	 */
	public String allUsers() {
		Page p = new Page(page, rows);
		dataMap.clear();
		dataMap.put("rows", userService.query(p));
		dataMap.put("total", p.getRowCount());
		dataMap.put("success", true);
		return "json";
	}

	/**
	 * save user
	 * @return String
	 */
	public String saveUser() {
		dataMap.clear();
		dataMap.put("success", userService.save(user));
		return "json";
	}
	
	/**
	 * update user
	 * @return String
	 */
	public String updateUser() {
		user.setUserID(userID);
		dataMap.clear();
		dataMap.put("success", userService.update(user));
		return "json";
	}

	/**
	 * delete user
	 * @return String
	 */
	public String destroyUser() {
		dataMap.clear();
		dataMap.put("success", userService.delete(userID));
		return "json";
	}
	
			/*getters and setters*/
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
}
