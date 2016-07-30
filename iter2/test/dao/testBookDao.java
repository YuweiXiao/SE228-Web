package dao;

import java.util.List;

import org.junit.Test;

public class testBookDao {
	@Test
	public void testGetUserFromDB()
	{
		BookDao dao = new BookDao();
		int num = dao.getTotalBookAmount();
		System.out.println(dao.getBooks(1, 1));
		
		System.out.println(num);
	}
	
	@Test
	public void testGetBooksByCategoryName() {
		BookDao dao = new BookDao();
		List book = dao.getBooksByCategoryName("computer");
		System.out.println(book);
	}
	
}
