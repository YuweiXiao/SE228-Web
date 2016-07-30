package action;

import org.springframework.beans.factory.annotation.Autowired;

import service.BookService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SearchAction extends ActionSupport{
	
	private String title;
	@Autowired
	private BookService bookService;
	
	/**
	 * search for book which title is like given title 
	 * @return String SUCCESS
	 */
	public String search() {
		ActionContext.getContext().put("books", bookService.search(title));
		ActionContext.getContext().put("path_nav", "搜索:" + title);
		return "list_item";
	}
	
			/*getters and setters*/
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	
	
	
}
