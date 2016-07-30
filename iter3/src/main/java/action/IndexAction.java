package action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import service.AnalysisService;
import service.BookService;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class IndexAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7072973863541486492L;
	
	private Map<String,Object> dataMap = new HashMap<String, Object>();
	
	@Autowired
	private BookService bookService;
	
	private int pageno = 1, rows = 6;
	
	/**
	 * get the index page data
	 * @return return SUCCESS
	 * @throws Exception 
	 */
	public String index(){
		System.out.println("in index method");
		ActionContext.getContext().put("books", bookService.queryMostPopular());
		ActionContext.getContext().put("newbooks", bookService.queryRecentlyShelve());
		return SUCCESS;
	}
	
	/**
	 * @return user
	 */
	public String manageUser() {
		ActionContext.getContext().put("title", "manage User");
		return "user";
	}
	
	/**
	 * @return book
	 */
	public String manageBook() {
		ActionContext.getContext().put("title", "manage Book");
		return "book";
	}
	
	/**
	 * get orders info from database for order management page.
	 * @return String
	 */
	public String manageOrder() {
		System.out.println("manage order");
		ActionContext.getContext().put("title", "manage Order");
		return "order";
	}
	
	/*public String test() {
		System.out.println("in test");
		return SUCCESS;
	}*/
	
	
	
			/*getters and setters*/
	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPageno() {
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}
	
}
