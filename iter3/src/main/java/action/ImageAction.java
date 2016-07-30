package action;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.ServletOutputStream;

import org.apache.struts2.ServletActionContext;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import service.BookService;
import service.UserService;
import util.MongoDBManager;
import util.MyMD5Generator;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import entity.User;

public class ImageAction extends ActionSupport{

	private String filename;
	private File myFile;
	private String myFileContentType;
	private String myFileFileName;
	private Integer bookID;
	
	@Autowired
	private MongoDBManager mongo;
	
	/**
	 * get image from mongoDB by filename
	 * write imge directly to client
	 * @return null
	 */
	public String viewImage() {
		if(filename == null || filename.equals(""))
			return null;
		try {
			DB db = mongo.getMongoClient().getDB("bookstore");
			GridFS gfsPhoto = new GridFS(db, "image");
			GridFSDBFile imageForOutput = gfsPhoto.findOne(filename);
			if(imageForOutput == null) {
				return null;
			}
			InputStream in = imageForOutput.getInputStream();
			ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
			byte[] bytes = new byte[1024];
			while( -1 != in.read(bytes))
				out.write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * upload image
	 * store filename in mysql
	 * @return success or not
	 */
	public String upload() {
		try {
			Date dt = new Date();
			String newFileName = bookID.toString() + "-" + dt.getTime() + myFileFileName;
			String filenameMD5 = MyMD5Generator.stringMD5(newFileName);
			DB db = mongo.getMongoClient().getDB("bookstore");
			GridFS gfsPhoto = new GridFS(db, "image");
			GridFSInputFile gfsFile = gfsPhoto.createFile(myFile);		// get image file from local drive
			gfsFile.setFilename(filenameMD5);							// set a new filename for identify purpose
			gfsFile.save();												// save the image file into mongoDB
			ApplicationContext ctx = new 
					ClassPathXmlApplicationContext("classpath:applicationContext.xml");
			BookService bookService = ctx.getBean(BookService.class);
			bookService.updateImage(bookID, filenameMD5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * get image from mongodb using _id
	 * write imge directly to client
	 * @return
	 */
	public String viewImageByID() {
		System.out.println(filename);
		if(filename == null || filename.equals(""))
			return null;
		try {
			DB db = mongo.getMongoClient().getDB("bookstore");
			GridFS gfsPhoto = new GridFS(db, "image");
			BasicDBObject obj = new BasicDBObject().append("_id", new ObjectId(filename));
			GridFSDBFile imageForOutput = gfsPhoto.findOne(obj);
			System.out.println(imageForOutput);
			if(imageForOutput == null) {
				return null;
			}
			InputStream in = imageForOutput.getInputStream();
			ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
			byte[] bytes = new byte[1024];
			while( -1 != in.read(bytes))
				out.write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * upload image
	 * store _id in mysql
	 * @return success or not
	 */
	public String uploadUserImage() {
		try {
			Date dt = new Date();
			User u = (User) ActionContext.getContext().getSession().get("user");
			ApplicationContext ctx = new 
					ClassPathXmlApplicationContext("classpath:applicationContext.xml");
			UserService userService = ctx.getBean(UserService.class);
			u = userService.queryUserByUsername(u.getUsername());
			String newFileName = u.getUsername() + "-" + dt.getTime() + myFileFileName;
			String filenameMD5 = MyMD5Generator.stringMD5(newFileName);

			DB db = mongo.getMongoClient().getDB("bookstore");
			GridFS gfsPhoto = new GridFS(db, "image");
			GridFSInputFile gfsFile = gfsPhoto.createFile(myFile);		// get image file from local drive
			gfsFile.setFilename(filenameMD5);							// set a new filename for identify purpose
			gfsFile.save();								
			u.setImageID(gfsFile.getId().toString()); 
			userService.updateUserProfile(u);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}
	
		/*getters and setters*/
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public File getMyFile() {
		return myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getMyFileContentType() {
		return myFileContentType;
	}

	public MongoDBManager getMongo() {
		return mongo;
	}

	public void setMongo(MongoDBManager mongo) {
		this.mongo = mongo;
	}

	public void setMyFileContentType(String myFileContentType) {
		this.myFileContentType = myFileContentType;
	}

	public String getMyFileFileName() {
		return myFileFileName;
	}

	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}

	public Integer getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	
	
	
	
}
