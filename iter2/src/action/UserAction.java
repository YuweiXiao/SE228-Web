package action;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import com.opensymphony.xwork2.ActionSupport;

import dao.BookDao;
import dao.RoleDao;
import dao.UsersDao;
import entity.Role;
import entity.User;

public class UserAction extends ActionSupport{
	
	private Map<String,Object> dataMap = new HashMap<String, Object>();
	private User user;
	private int userID;
	
	
	public Map<String, Object> getDataMap() {
		return dataMap;
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

	public String allUsers() {
		UsersDao userDao = new UsersDao();
		dataMap.clear();
		dataMap.put("total", userDao.getTotalUserAmount());
		dataMap.put("rows", userDao.getUsers(1, 1));
		dataMap.put("success", true);
		System.out.println(dataMap);
		return SUCCESS;
	}
	
	public String saveUser() {
		UsersDao userDao = new UsersDao();
		RoleDao roleDao = new RoleDao();
		Role role = roleDao.getRoleByName(user.getRole_name());
		user.setRole(role);
		userDao.saveUser(user);
		dataMap.clear();
		dataMap.put("success", true);
		return SUCCESS;
	}
	
	public String updateUser() {
		user.setUserID(userID);
		
		RoleDao roleDao = new RoleDao();
		Role role = roleDao.getRoleByName(user.getRole_name());
		user.setRole(role);
		System.out.println(user);
		UsersDao userDao = new UsersDao();
		userDao.updateUser(user);
		dataMap.clear();
		dataMap.put("success", true);
		return SUCCESS;
	}
	
	public String destroyUser() {
		UsersDao userDao = new UsersDao();
		userDao.destroyUser(userID);
		dataMap.clear();
		dataMap.put("success", true);
		return SUCCESS;
	}
}
