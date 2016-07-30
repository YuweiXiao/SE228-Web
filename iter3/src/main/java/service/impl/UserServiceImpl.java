package service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import dao.RoleDao;
import dao.UserDao;
import entity.Page;
import entity.Role;
import entity.User;
import service.UserService;
import util.MongoDBManager;

@Transactional(readOnly=true)
public class UserServiceImpl implements UserService{

	private RoleDao roleDao;
	private UserDao userDao;

	private MongoDBManager mongo;
	
	@Override
	@Transactional(readOnly=false)
	public boolean save(User user) {
		Role role = roleDao.getRoleByName(user.getRole_name());
		user.setRole(role);
		return userDao.save(user);
	}

	@Override
	@Transactional(readOnly=false)
	public boolean update(User user) {
		Role role = roleDao.getRoleByName(user.getRole_name());
		user.setRole(role);
		return userDao.update(user);
	}

	@Override
	@Transactional(readOnly=false)
	public boolean delete(int userID) {
		return userDao.destroyUser(userID);
	}

	@Override
	public List query(Page page) {
		page.setRowCount(userDao.getTotalUserAmount());
		String hql = "from User";
		return userDao.query(hql, page.getOffset(), page.getPageSize());
	}
	
	@Override
	public User queryUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	@Override
	public User getUserProfile(User user) {
		try {
			User u = userDao.getUserByID(user.getUserID());
			if(u.getProfileID() == null)											// the user has no profile in mongoDB
				return u;
			DBCollection collection = mongo.getDB().getCollection("userprofile");	// get collection of mongoDB
			BasicDBObject search = new BasicDBObject();								// search Object
			search.append("_id", new ObjectId(u.getProfileID()));
			DBObject profile = collection.findOne(search);
			String[] field  = {"Birthday", "Gender", "E_mail", "Job", "Income", "ImageID"};
			for(int i = 0; i < field.length; ++i) {
				if(profile.containsField(field[i].toLowerCase())) {
					Method m = u.getClass().getMethod("set" + field[i], String.class);
					m.invoke(u, profile.get(field[i].toLowerCase()));
				}
			}
			return u;
		} catch ( Exception e ) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	@Transactional(readOnly=false)
	public boolean updateUserProfile(User user) {
		try {
			
			User u = userDao.getUserByID(user.getUserID());
			u.setName(user.getName());												// update name
			DBCollection collection = mongo.getDB().getCollection("userprofile");	// get collection of mongoDB
			BasicDBObject search = new BasicDBObject();								// search Object
			if(u.getProfileID() == null) {										// if the user do not has profileID, insert it into mongo and get profileID
				collection.insert(search);
				u.setProfileID(search.get("_id").toString());
			} else {																// or get the search object
				search.put("_id", new ObjectId(u.getProfileID()));
			}
			
			BasicDBObject updateObject = new BasicDBObject();
			String[] field  = {"Birthday", "Gender", "E_mail", "Job", "Income", "ImageID"};
			for(int i = 0; i < field.length; ++i) {
				Method m = User.class.getMethod("get" + field[i]);
				Object tmp = m.invoke(user);
				if(tmp != null && !tmp.equals(""))
					updateObject.append(field[i].toLowerCase(), tmp.toString());
			}
			
			BasicDBObject updateQuery = new BasicDBObject();						// create the update object
			updateQuery.append("$set", updateObject);
			collection.update(search, updateQuery);									// update profile in mongoDB
			userDao.update(u);													// update user in mysql
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
			/*setters and getters.*/

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public MongoDBManager getMongo() {
		return mongo;
	}

	public void setMongo(MongoDBManager mongo) {
		this.mongo = mongo;
	}
	
}
