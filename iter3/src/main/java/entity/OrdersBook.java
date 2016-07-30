package entity;

public class OrdersBook {
	
	private Book book;
	private int amount;


	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "OrdersBook [book=" + book + ", amount=" + amount + "]";
	}

	public OrdersBook(Book book, int amount) {
		super();
		this.book = book;
		this.amount = amount;
	}

	public OrdersBook() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}