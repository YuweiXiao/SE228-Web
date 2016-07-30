package action;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.RoleDao;
import dao.UsersDao;
import entity.User;


public class AuthAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private HashMap<String, Object> dataMap = new HashMap();
	private String tip;
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public boolean isLogin() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		if(session.containsKey("user"))
			return true;
		return false;
	}
	
	public String logout() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		if(session.containsKey("user")){
			session.remove("user");
			return SUCCESS;
		}
		else
			return INPUT;
	}
	
	public String register() {
		UsersDao userDao = new UsersDao();
		RoleDao roleDao = new RoleDao();
		if(!userDao.isUsernameExists(user.getUsername()))
		{
			user.setRole(roleDao.getUserRole());
			user = userDao.saveUser(user);
			System.out.println(user);
			allow_passport();
			return SUCCESS;
		} else {
			return "register";
		}
	}
	
	public String login() {
		if(user!=null)
			System.out.println(user);
		else
			return INPUT;
		
		if(isLogin())		//if user has logged in, redirect to index
			return SUCCESS;
		
		UsersDao userDao = new UsersDao();
		User userT = userDao.getUserByUsername(user.getUsername());
		if( userT != null && userT.getPassword().equals(user.getPassword())) {
			/* put user object into session */
			user.setUserID(userT.getUserID());
			user.setRole(userT.getRole());
			allow_passport();
			return SUCCESS;
		} else {
			setTip("用户名或者密码错误");
			return INPUT;
		}
	}
	
	private void allow_passport() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		user.setPassword(null);//erase the information of password 
		session.put("user", user);
	}
	
	public HashMap<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(HashMap<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	
	
	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String test() {
		System.out.println("hello there");
		dataMap = new HashMap<String, Object>();
		dataMap.put("user", "hello");
		System.out.println(dataMap);
		return SUCCESS;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
