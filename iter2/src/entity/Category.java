package entity;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Category {
	private int categoryID;
	private String name;
	private Set<Book> books;
	
	
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	public Category(int categoryID, String name, Set<Book> books) {
		super();
		this.categoryID = categoryID;
		this.name = name;
		this.books = books;
	}



	public Set<Book> getBooks() {
		return books;
	}



	public void setBooks(Set<Book> books) {
		this.books = books;
	}



	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		String s ="Category [categoryID=" + categoryID + ", name=" + name
				+ ", books=" + "]";
		for (Iterator iterator = books.iterator(); iterator.hasNext();) {
			Book book = (Book) iterator.next();
			s = s + book.getTitle()+",";
		}
		return s;	
	}
	
	
}
