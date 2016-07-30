package service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testAnalysisService {

	@Test
	public void testGetUserBehaviorOfBookKind() {
		ApplicationContext	ctx = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
		AnalysisService analysisService = ctx.getBean("AnalysisService", AnalysisService.class);
		System.out.println(analysisService.getUserBehaviorBookKind(7));
		
	}
}
