package dao;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testBookDao {
	@Test
	public void testGetUserFromDB()
	{
		ApplicationContext	ctx = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
		BookDao dao = ctx.getBean(BookDao.class);
//		BookDao dao = new BookDao();
		List list = dao.getMostPopularBook();
		System.out.println(list);
	}
	
	@Test
	public void testGetBooksByCategoryName() {
		BookDao dao = new BookDao();
		List book = (List) dao.getBooksByCategoryName("computer");
		System.out.println(book);
	}
	
}
