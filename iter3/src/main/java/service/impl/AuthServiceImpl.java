package service.impl;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import dao.RoleDao;
import dao.UserDao;
import entity.User;
import service.AuthService;

public class AuthServiceImpl extends AuthService{

	private UserDao userDao;
	private RoleDao roleDao;
	
	@Override
	public boolean isLogin() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		if(session.containsKey("user"))
			return true;
		return false;
	}

	@Override
	@Transactional(readOnly=true)
	public String login(User user) {
		if(user!=null)
			System.out.println(user);
		else
			return "input";
		
		if(isLogin())		//if user has logged in, redirect to index
			return "success";
		
		User userT = userDao.getUserByUsername(user.getUsername());
		if( userT != null && userT.getPassword().equals(user.getPassword())) {
			/* put user object into session */
			user.setUserID(userT.getUserID());
			user.setRole(userT.getRole());
			allow_passport(user);
			return "success";
		} else {
			return "账号或者密码错误";
		}
	}

	@Override
	public boolean logout() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		if(session.containsKey("user")){
			session.remove("user");
			return true;
		}
		else
			return false;
	}

	@Override
	@Transactional(readOnly=false)
	public boolean register(User user) {
		User u = userDao.getUserByUsername(user.getUsername());
		if(u == null)
		{
			user.setRole(roleDao.getUserRole());
			if(userDao.save(user) == false)
				return false;
			allow_passport(userDao.getUserByUsername(user.getUsername()));
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void allow_passport(User user) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("user", user);
	}

	/*setters and getters*/
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	
	
}
