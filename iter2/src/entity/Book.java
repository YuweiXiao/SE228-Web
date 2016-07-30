package entity;

import java.util.HashSet;
import java.util.Set;

public class Book {
	int bookID;
	String isbn;
	String title;
	String press;
	String author;
	int price;
	int inventory;
	int soldAmount;
	int amount;
	private Set category = new HashSet();
	
	public Book() {

	}
	
	
	
	
	public Book(int bookID, String isbn, String title, String press,
			String author, int price, int inventory, int soldAmount,
			int amount, Set category) {
		super();
		this.bookID = bookID;
		this.isbn = isbn;
		this.title = title;
		this.press = press;
		this.author = author;
		this.price = price;
		this.inventory = inventory;
		this.soldAmount = soldAmount;
		this.amount = amount;
		this.category = category;
	}




	@Override
	public String toString() {
		return "Book [bookID=" + bookID + ", isbn=" + isbn + ", title=" + title
				+ ", press=" + press + ", author=" + author + ", price="
				+ price + ", inventory=" + inventory + ", soldAmount="
				+ soldAmount + ", amount=" + amount + ", category=" + category
				+ "]";
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	
	public Set getCategory() {
		return category;
	}


	public void setCategory(Set category) {
		this.category = category;
	}




	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getBookID() {
		return bookID;
	}
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	public int getSoldAmount() {
		return soldAmount;
	}
	public void setSoldAmount(int soldAmount) {
		this.soldAmount = soldAmount;
	}
	
	
	
}
