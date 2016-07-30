package entity;

import java.util.Set;

public class Category {
	private int categoryID;
	private String name;
	private Set<Book> books = null;
	
	
	public Category() {
		super();
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
		return "Category [categoryID=" + categoryID + ", name=" + name + "]";
	}

	
	
	
}
