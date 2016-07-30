package action;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;

import service.AuthService;
import com.opensymphony.xwork2.ActionSupport;

import entity.User;


public class AuthAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private User user;
	private HashMap<String, Object> dataMap = new HashMap<String, Object>();
	private String tip;

	@Autowired
	private AuthService authService;

	/**
	 * default action
	 */
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	/**
	 * whether the user is logged in.
	 * @return true: already logged in
	 * 		   false: not login.
	 */
	public boolean isLogin() {
		return authService.isLogin();
	}
	
	/**
	 * log out, remove user from session.
	 * @return SUCCESS log out successfully
	 * 		   INPUT   user have not logged in.
	 */
	public String logout() {
		if(authService.logout())
			return SUCCESS;
		else
			return INPUT;
	}
	
	/**
	 * register 
	 * @return
	 */
	public String register() {
		if(authService.register(user))
			return SUCCESS;
		else 
			return "register";
	}

	/**
	 * login
	 * @return
	 */
	public String login() {
		String t = authService.login(user); 
		if(t == SUCCESS)
			return SUCCESS;
		else if(t == INPUT)
			return INPUT;
		setTip(t);
		return INPUT;
	}
	
	
			/*getter and setters*/
	
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
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
