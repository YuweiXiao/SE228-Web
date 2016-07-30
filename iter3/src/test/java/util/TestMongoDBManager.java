package util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Date;

import org.bson.types.ObjectId;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import service.UserService;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

import dao.UserDao;
import entity.User;

public class TestMongoDBManager {

	@Test
	public void testMongodbManager() {
		try {
			ApplicationContext ctx = new 
					ClassPathXmlApplicationContext("classpath:applicationContext.xml");
			System.out.println(ctx);
			Date dt = new Date();
			DB db = ctx.getBean("mongoDBManager", MongoDBManager.class).getMongoClient().getDB("bookstore");
			MessageDigest md = MessageDigest.getInstance("MD5");
			String newFileName = "3-" + dt.getTime() + "test-image";
			System.out.println(newFileName);
			String d = MyMD5Generator.stringMD5(newFileName);
			System.out.println(d);
			GridFS gfsPhoto = new GridFS(db, "image");
			File imageFile = new File("E:\\001.jpg");

			// get image file from local drive
			GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
			
			// set a new filename for identify purpose
			gfsFile.setFilename(d);

			// save the image file into mongoDB
			gfsFile.save();
			System.out.println(gfsFile.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetObjectByID() {
		ApplicationContext ctx = new 
				ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		System.out.println(ctx);
		DB db = ctx.getBean("mongoDBManager", MongoDBManager.class).getMongoClient().getDB("bookstore");
		GridFS gfsPhoto = new GridFS(db, "image");
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId("57559dc7ce5f332050a47d52"));
		DBObject dbObj = gfsPhoto.findOne(query);
		System.out.println(dbObj);
	}
	
	@Test
	public void testInsertUserProfileIntoMongoDB() {
		try {
			ApplicationContext ctx = new 
					ClassPathXmlApplicationContext("classpath:applicationContext.xml");
			UserService userService = ctx.getBean("UserService", UserService.class);
			DB db = ctx.getBean("mongoDBManager", MongoDBManager.class).getMongoClient().getDB("bookstore");
			DBCollection collection = db.getCollection("userprofile");
			User user = userService.queryUserByUsername("admin");

			BasicDBObject search = new BasicDBObject();								// search Object
			if(user.getProfileID() == null) {										// if the user do not has profileID, insert it into mongo and get profileID
				collection.insert(search);
				user.setProfileID(search.get("_id").toString());
			} else {																// or get the search object
				search.put("_id", new ObjectId(user.getProfileID()));
			}
			
			BasicDBObject updateObject = new BasicDBObject();
			
			String[] field  = {"Birthday", "Gender", "E_mail", "Job", "Income"};
			
			for(int i = 0; i < field.length; ++i) {
				Method m = User.class.getMethod("get" + field[i]);
				Object tmp = m.invoke(user);
				if(tmp != null)
					updateObject.append(field[i].toLowerCase(), tmp.toString());
			}
			
			BasicDBObject updateQuery = new BasicDBObject();						// create the update object
			updateQuery.append("$set", updateObject);
			collection.update(search, updateQuery);									// update profile in mongoDB
			userService.update(user);												// update user in mysql
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
